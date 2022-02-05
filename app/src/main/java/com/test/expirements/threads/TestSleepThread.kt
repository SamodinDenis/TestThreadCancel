package com.test.expirements.threads

import android.util.Log

class TestSleepThread : Thread() {
    companion object {
        private const val TAG = "TestSleepThread"
    }

    override fun run() {
        Log.d(TAG, "thread started")
        try {
            sleep(5000)
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message, e)
        }
        Log.d(TAG, "thread finished")
    }
}
