package com.example.deliverytekacourier.fragments.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.deliverytekacourier.activities.LoginActivity
import com.example.deliverytekacourier.data.viewmodel.DeliverytekaCourierViewModel
import com.example.deliverytekacourier.databinding.FragmentProfileBinding
import com.example.deliverytekacourier.utility.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: DeliverytekaCourierViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    var courierId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.COURIER_ID, Context.MODE_PRIVATE)
        courierId = sharedPref?.getInt(Constants.COURIER_ID, 0)


        binding.signOutBtn.setOnClickListener {
            sharedPref?.let {
                with(sharedPref.edit()) {
                    putString(Constants.COURIER_PASSWORD, "").apply()
                }
            }
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

        }

        courierId?.let { viewModel.getCourier(it) }
        viewModel.getCourier.observe(viewLifecycleOwner, { response ->
            val courierInfo = response.result[0]


            binding.courierName.text = courierInfo.courier_name
            binding.courierPhone.text = courierInfo.courier_phone
            binding.workedCourierShift.text = courierInfo.all_shifts
            binding.workedCourierHour.text = courierInfo.all_hours
            binding.deliveredCourier.text = courierInfo.all_orders
            binding.salaryCourier.text = courierInfo.all_salary

        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}