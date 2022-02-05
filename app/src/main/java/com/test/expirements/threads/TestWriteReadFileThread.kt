package com.test.expirements.threads

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class TestWriteReadFileThread(
    private val context: Context,
    private val writeFinished: () -> Unit,
    private val threadFinished: () -> Unit
) : Thread() {
    companion object {
        private const val SAMPLE_SIZE = 512 * 1024
        private const val FILE_SIZE = SAMPLE_SIZE * 1000
        private const val FILE_NAME = "input.txt"
        private const val TAG = "TestWriteReadFileThread"
    }

    private var outputStream: FileOutputStream? = null
    private var inputStream: FileInputStream? = null

    override fun run() {
        Log.d(TAG, "thread started")
        try {
            logIsInterrupted()
            generateBigFile()
            logIsInterrupted()
            writeFinished()
            logIsInterrupted()
            readFile()
            logIsInterrupted()
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message, e)
            // the best java practice is throwing exception again
            // throw e
        } finally {
            outputStream?.close()
            inputStream?.close()
        }
        Log.d(TAG, "thread finished")
        threadFinished()
    }

    private fun logIsInterrupted() {
        if (isInterrupted) {
            Log.e(TAG, "isInterrupted")
        }
    }

    private fun generateBigFile() {
        Log.d(TAG, "generateBigFile")
        val writeStartTime = System.currentTimeMillis()
        outputStream = FileOutputStream(
            getFile().apply {
                createNewFile()
            }
        )
        var i = 0
        while (i < FILE_SIZE /*&& !isInterrupted*/) { // the best solution is check isInterrupted here
            outputStream?.write(getSample()) ?: return
            i += SAMPLE_SIZE
        }
        outputStream?.flush()
        outputStream?.close()
        outputStream = null
        Log.d(
            TAG,
            "generateBigFile finished, " +
                "duration  = ${System.currentTimeMillis() - writeStartTime} ms"
        )
    }

    private fun readFile() {
        Log.d(TAG, "readFile")
        val readStartTime = System.currentTimeMillis()
        inputStream = FileInputStream(getFile())
        var i = 0
        val array = ByteArray(SAMPLE_SIZE)
        while (i < FILE_SIZE /*&& !isInterrupted*/) { // the best solution is check isInterrupted here
            i += inputStream?.read(array) ?: return
        }
        inputStream?.close()
        inputStream = null
        Log.d(
            TAG,
            "readFile finished, " +
                "duration  = ${System.currentTimeMillis() - readStartTime} ms"
        )
    }

    private fun getSample(): ByteArray {
        val array = ByteArray(SAMPLE_SIZE)
//            val i = 0
//            while (i < SAMPLE_SIZE) {
//                array[i] = i.toByte()
//            }
        return array
    }

    private fun getFile(): File {
        return File(context.cacheDir, FILE_NAME)
    }
}
