package com.test.expirements.threads.network

import android.util.Log
import java.io.IOException

class TestServerThread(private val onServerStarted: () -> Unit) : Thread() {
    companion object {
        private const val TAG = "TestServerThread"
    }

    override fun run() {
        Log.d(TAG, "thread started")
        try {
            val server = GreetServer()
            onServerStarted()
            server.start(GreetServer.PORT)
            server.read()
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message, e)
        } catch (e:IOException) {
            Log.e(TAG, e.message, e)
        }
        catch (e:Exception) {
            Log.e(TAG, e.message, e)
        }
        Log.d(TAG, "thread finished")
    }
}
