package com.example.deliverytekacourier.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deliverytekacourier.R
import com.example.deliverytekacourier.activities.MainActivity
import com.example.deliverytekacourier.databinding.FragmentSplashBinding
import com.example.deliverytekacourier.utility.Constants


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        val logoAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.logo_splash_screen)
        binding.splashScreen.animation = logoAnim

        val sharedPref = requireActivity().getSharedPreferences(Constants.COURIER_ID, Context.MODE_PRIVATE)
        val password = sharedPref?.getString(Constants.COURIER_PASSWORD, "")

        Handler().postDelayed({
            if (password.isNullOrEmpty()) {

                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }else {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }, 2000)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}