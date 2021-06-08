package com.example.deliverytekacourier.fragments.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.deliverytekacourier.R
import com.example.deliverytekacourier.activities.MainActivity
import com.example.deliverytekacourier.data.models.RequestAccess
import com.example.deliverytekacourier.data.viewmodel.DeliverytekaCourierViewModel
import com.example.deliverytekacourier.databinding.FragmentLoginBinding
import com.example.deliverytekacourier.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: DeliverytekaCourierViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val slots = UnderscoreDigitSlotsParser().parseSlots("+375 (__)___-__-__")
        val formatWatcher: FormatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(slots)
        )
        formatWatcher.installOn(binding.phonesInput)

        binding.loginBtn.setOnClickListener {

            if (verifyDataFromUser(
                    binding.phonesInput.text.toString(),
                    binding.passwordsInput.text.toString()
                )
            ) {
                val password =  binding.passwordsInput.text.toString()

                binding.progressBar.isVisible = true
                viewModel.login(
                    RequestAccess(
                        binding.phonesInput.text.toString(),
                          binding.passwordsInput.text.toString()
                    )

                )
                viewModel.login.observe(viewLifecycleOwner, { response ->
                    if (response[0].courier_id.isNotEmpty()) {
                        binding.progressBar.isVisible = false

                        val sharedPref = requireActivity().getSharedPreferences(
                            Constants.COURIER_ID,
                            Context.MODE_PRIVATE
                        )

                        sharedPref?.let {
                            with(sharedPref.edit()) {
                                putString(Constants.COURIER_PASSWORD, password ).apply()
                            }
                        }

                        sharedPref?.let {
                            with(sharedPref.edit()) {
                                putInt(Constants.COURIER_ID, response[0].courier_id.toInt()).apply()
                            }
                        }


                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                    } else {

                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            "Вы неправильно ввели номер телефона или пароль!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }

        }

        return binding.root
    }


    private fun verifyDataFromUser(phone: String, password: String): Boolean {
        return if (phone.length != 18) {
            Toast.makeText(
                requireContext(),
                getString(R.string.enter_number_phone_correctly),
                Toast.LENGTH_LONG
            )
                .show()
            false
        } else if (phone.isNotEmpty() && password.isNotEmpty()) {
            true
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.not_all_fields_filled),
                Toast.LENGTH_LONG
            )
                .show()
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}