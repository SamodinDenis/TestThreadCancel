package com.test.expirements.threads.network;

import static com.test.expirements.threads.network.GreetClient.TIMEOUT_MS;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestStatic {
    static void test(GreetClient greetClient) {
        try {
            logIsInterrupted();
            greetClient.clientSocket = new Socket("127.0.0.1", 7777);
            logIsInterrupted();
            greetClient.clientSocket.setSoTimeout(TIMEOUT_MS);
            logIsInterrupted();
            greetClient.output = new PrintWriter(greetClient.clientSocket.getOutputStream(), true);
            logIsInterrupted();
            InputStream inputStream = greetClient.clientSocket.getInputStream();
            greetClient.input = new BufferedReader(new InputStreamReader(inputStream));
            logIsInterrupted();
        } catch (IOException e) {
            Log.d("GreetClient", e.getMessage(), e);
        }
    }

    private static void logIsInterrupted() {
        if (Thread.currentThread().isInterrupted()) {
            Log.e("GreetClient", "isInterrupted");
        }
    }
}
