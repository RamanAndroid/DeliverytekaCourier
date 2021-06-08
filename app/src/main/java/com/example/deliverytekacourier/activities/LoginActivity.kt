package com.example.deliverytekacourier.activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.deliverytekacourier.databinding.ActivityLoginBinding
import com.example.deliverytekacourier.utility.NetworkChangeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val networkChangeListener = NetworkChangeListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener, intentFilter)

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeListener)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(binding.navHostLoginFragment.id)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}