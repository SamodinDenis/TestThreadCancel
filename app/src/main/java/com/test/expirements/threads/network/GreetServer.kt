package com.test.expirements.threads.network

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class GreetServer {
    companion object {
        private const val TAG = "GreetServer"
        const val PORT = 7777
        const val IP = "127.0.0.1"
    }

    private lateinit var serverSocket: ServerSocket
    private lateinit var clientSocket: Socket
    private lateinit var output: PrintWriter
    private lateinit var input: BufferedReader
    fun start(port: Int) {
        serverSocket = ServerSocket(port)
        clientSocket = serverSocket.accept()
        output = PrintWriter(clientSocket.getOutputStream(), true)
        input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
    }

    fun read() {
        val greeting: String = input.readLine()
        Log.d(TAG, "read message = $greeting")
        Thread.sleep((GreetClient.TIMEOUT_MS * 2).toLong())
        if ("hello server" == greeting) {
            output.println("hello client")
        } else {
            output.println("unrecognised greeting")
        }
    }

    fun stop() {
        input.close()
        output.close()
        clientSocket.close()
        serverSocket.close()
    }
}
