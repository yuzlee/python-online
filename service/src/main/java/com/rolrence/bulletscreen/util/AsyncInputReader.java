package com.rolrence.bulletscreen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Rolrence on 2017/6/2.
 *
 */
public class AsyncInputReader extends Thread {
    private static int maxWaitTime = 1000;

    private InputStream in;
    private Scanner scanner;

    private StreamDelegate delegate;

    public AsyncInputReader(InputStream in, StreamDelegate delegate) {
        this.in = in;
        this.scanner = new Scanner(in);

        this.delegate = delegate;
    }

    public void run() {
        String line;
        while ((line = this.scanner.nextLine()) != null) {
            delegate.invoke(line);
        }
    }
}
