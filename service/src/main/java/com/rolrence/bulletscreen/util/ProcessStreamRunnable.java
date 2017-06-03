package com.rolrence.bulletscreen.util;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Rolrence on 2017/6/2.
 *
 */
public class ProcessStreamRunnable implements Runnable {
    private PipedInputStream pin;

    private OutputStream processStdin;
    private InputStream processStdout;

    private Process process;
    private ProcessBuilder builder;

    private StreamDelegate callback;

    public ProcessStreamRunnable(String command, PipedInputStream pin, StreamDelegate callback) {
        this.pin = pin;
        this.callback = callback;

        this.builder = new ProcessBuilder(command);
        this.builder.redirectErrorStream(true);
    }

    public void run() {
        try {
            this.process = this.builder.start();

            this.processStdin = this.process.getOutputStream();
            this.processStdout = this.process.getInputStream();

            AsyncInputReader asyncInputReader = new AsyncInputReader(this.processStdout, this.callback);
            AsyncOutputWriter asyncOutputWriter = new AsyncOutputWriter(this.processStdin, this.pin);

            asyncOutputWriter.start();
            asyncInputReader.start();
            // this.process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}