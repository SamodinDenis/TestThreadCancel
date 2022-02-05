package com.test.expirements.threads.network

import android.util.Log

class TestClientThread : Thread() {
    companion object {
        private const val TAG = "TestClientThread"
    }

    override fun run() {
        Log.d(TAG, "thread started")
        try {
            val client = GreetClient()
            client.startConnection(GreetServer.IP, GreetServer.PORT)
            val response = client.sendMessage("hello server")
            Log.d(TAG, "response = $response")
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message, e)
        }
        Log.d(TAG, "thread finished")
    }
}
