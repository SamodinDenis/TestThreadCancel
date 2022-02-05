package com.test.expirements

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.test.expirements.databinding.ActivityMainBinding
import com.test.expirements.threads.TestSleepThread
import com.test.expirements.threads.TestWriteReadFileThread
import com.test.expirements.threads.network.TestClientThread
import com.test.expirements.threads.network.TestServerThread

class TestThreadsActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private val handler = Handler(Looper.getMainLooper())
    private var thread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initListeners()
    }

    private fun initListeners() {
        binding?.btnTestFile?.setOnClickListener {
            testFileThread()
        }
        binding?.btnTestSleep?.setOnClickListener {
            testSleepThread()
        }
        binding?.btnTestSocket?.setOnClickListener {
            testSocketThread()
        }
    }

    private fun testFileThread() {
        thread?.interrupt()
        thread = TestWriteReadFileThread(
            this,
            writeFinished = {
                // do nothing
            },
            threadFinished = {
                // do nothing
            }
        ).apply {
            start()
        }
        thread?.interrupt()
        handler.postDelayed(
            {
                thread?.interrupt()
            },
            100
        )
        handler.postDelayed(
            {
                thread?.interrupt()
            },
            500
        )
        thread = null
    }

    private fun testSocketThread() {
        thread?.interrupt()
        val serverThread = TestServerThread(onServerStarted = {
            handler.post {
                startClientThread()
            }
        }).apply {
            start()
        }
        thread = null
    }

    private fun startClientThread() {
        val clientThread = TestClientThread().apply {
            start()
        }
//        handler.postDelayed({
            clientThread.interrupt()
//        }, 1000)
    }

    private fun testSleepThread() {
        thread?.interrupt()
        thread = TestSleepThread().apply {
            start()
        }
//        thread?.interrupt()
//        thread = null
        handler.postDelayed(
            {
                thread?.interrupt()
                thread = null
            },
            500
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
