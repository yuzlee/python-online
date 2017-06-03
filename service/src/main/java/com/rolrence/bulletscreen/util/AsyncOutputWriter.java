package com.rolrence.bulletscreen.util;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Rolrence on 2017/6/2.
 *
 */
public class AsyncOutputWriter extends Thread {
    private OutputStream out;
    private PipedInputStream pin;

    private Scanner scanner;
    private BufferedReader br;

    private String get_stdin() {
        byte[] buf = new byte[1024];
        try {
            int len = this.pin.read(buf);
            return new String(buf,0,len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AsyncOutputWriter(OutputStream out, PipedInputStream pin) {
        this.out = out;
        this.pin = pin;
        this.scanner = new Scanner(pin);
        this.br = new BufferedReader(new InputStreamReader(pin));
    }

    public void run() {
        String input;
        while ((input = this.get_stdin()) != null) {
            try {
                out.write(input.getBytes());
                out.write('\n');
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
