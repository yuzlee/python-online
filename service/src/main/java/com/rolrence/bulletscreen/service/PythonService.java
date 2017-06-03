package com.rolrence.bulletscreen.service;

import com.rolrence.bulletscreen.entity.User;
import com.rolrence.bulletscreen.util.ProcessStreamRunnable;
import com.rolrence.bulletscreen.util.StreamDelegate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by Rolrence on 2017/5/31.
 *
 */
@Service
public class PythonService {
    private static File folder = new File(System.getProperty("java.io.tmpdir"));

    private static Map<User, PipedOutputStream> userInput = new ConcurrentHashMap<>();
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static String pythonCommand;

    static {
        InputStream is = new ByteArrayInputStream("/properties/python-config.properties".getBytes());
        Properties properties = new Properties();
        try {
            properties.load(is);
            pythonCommand = properties.getProperty("python.path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StreamDelegate getDelegate(WebSocketSession session) {
        return new StreamDelegate(s -> {
            try {
                System.out.println("Send: " + s);
                if (session.isOpen()) {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(s));
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public static User getSessionKey(WebSocketSession session) {
//        HttpHeaders httpHeaders = session.getHandshakeHeaders();
//        String[] cookies = httpHeaders.get("Cookie").get(0).split(";");
//        for (String cookie: cookies) {
//            if (cookie.contains("JSESSIONID")) {
//                return cookie.replace("JSESSIONID=", "").trim();
//            }
//        }
//        return null;
        return (User)session.getAttributes().get("user");
    }

    public void exec(WebSocketSession session, String content) {
        User key = getSessionKey(session);
        if (key == null) {
            return;
        }
        PipedOutputStream pout = null;
        if (userInput.containsKey(key)) {
            pout = userInput.get(key);
        } else {
            try {
                pout = new PipedOutputStream();
                userInput.put(key, pout);

                PipedInputStream pin = new PipedInputStream();
                pin.connect(pout);

                if (pythonCommand == null) {
                    session.sendMessage(new TextMessage("Server Error."));
                    System.out.println("Can not find the python execution");
                    return;
                }
                ProcessStreamRunnable p = new ProcessStreamRunnable(pythonCommand, pin, getDelegate(session));
                cachedThreadPool.execute(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            pout.write(content.getBytes());
            pout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
