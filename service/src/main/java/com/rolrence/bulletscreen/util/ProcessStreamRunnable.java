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
        try {
            this.process = this.builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Process getProcess() {
        return this.process;
    }

    public void run() {
        this.processStdin = this.process.getOutputStream();
        this.processStdout = this.process.getInputStream();

        AsyncInputReader asyncInputReader = new AsyncInputReader(this.processStdout, this.callback);
        AsyncOutputWriter asyncOutputWriter = new AsyncOutputWriter(this.processStdin, this.pin);

        asyncOutputWriter.start();
        asyncInputReader.start();
    }
}