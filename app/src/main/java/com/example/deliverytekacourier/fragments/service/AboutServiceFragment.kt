package com.example.deliverytekacourier.fragments.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deliverytekacourier.databinding.FragmentAboutServiceBinding
import com.example.deliverytekacourier.utility.Utils


class AboutServiceFragment : Fragment() {

    private var _binding: FragmentAboutServiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutServiceBinding.inflate(inflater, container, false)

        binding.btnOpenOfficialWebsite.setOnClickListener {
            Utils.openLink(requireActivity(), "https://komaroff-site.000webhostapp.com/")
        }

        binding.btnOpenVkGleb.setOnClickListener {
            Utils.openLink(requireActivity(), "https://vk.com/komaroffdes")
        }
        binding.btnOpenVkRoman.setOnClickListener {
            Utils.openLink(requireActivity(), "https://vk.com/romlyschyk")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}