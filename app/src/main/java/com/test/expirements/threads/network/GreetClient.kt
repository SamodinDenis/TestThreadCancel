package com.test.expirements.threads.network

import android.util.Log
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class GreetClient {
    companion object {
        private const val TAG = "GreetClient"
        const val TIMEOUT_MS = 10000
    }

    lateinit var clientSocket: Socket
    lateinit var output: PrintWriter
    lateinit var input: BufferedReader

    fun startConnection(ip: String?, port: Int) {
        TestStatic.test(this)
    }

    fun sendMessage(msg: String?): String {
        Log.d(TAG, "send message = $msg")
        output.println(msg)
        return input.readLine()
    }

    fun stopConnection() {
        input.close()
        output.close()
        clientSocket.close()
    }
}
