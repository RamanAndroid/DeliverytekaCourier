package com.example.deliverytekacourier.fragments.shift

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.deliverytekacourier.data.viewmodel.DeliverytekaCourierViewModel
import com.example.deliverytekacourier.databinding.FragmentStartShiftBinding
import com.example.deliverytekacourier.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class StartShiftFragment : Fragment() {

    private var _binding: FragmentStartShiftBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeliverytekaCourierViewModel by viewModels()
    var currentTime: Int = 0
    var shiftId = 0
    var courierId: Int? = 0
    val time = Calendar.getInstance()

    private var shiftTime: Array<String?> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartShiftBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.COURIER_ID, Context.MODE_PRIVATE)
        courierId = sharedPref?.getInt(Constants.COURIER_ID, 0)

        binding.acceptShift.setOnClickListener {
            showDialogPayMethods()
        }

        viewModel.shift.observe(viewLifecycleOwner) {

            if (it[0].start_work_shift.isNotBlank()) {
                val textWork = StringBuilder(
                    "Вы работаете с ${it[0].start_work_shift} до ${it[0].end_work_shift}"
                )
                binding.selectTimeShiftLayout.isVisible=false
                binding.selectedShiftTime.isVisible=true
                binding.layoutCantStartShift.isVisible = false
                binding.textTimeWork.text = textWork
            }else{
                binding.selectTimeShiftLayout.isVisible=true
                binding.selectedShiftTime.isVisible=false
                binding.layoutCantStartShift.isVisible = true
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
       courierId?.let { viewModel.checkOnline(it) }
        shiftController()
        if (shiftTime.isNullOrEmpty()) {
            binding.selectTimeShiftLayout.isVisible = false
            binding.layoutCantStartShift.isVisible = true
        } else {
            binding.selectTimeShiftLayout.isVisible = true
            binding.layoutCantStartShift.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun shiftController() {
        currentTime = time.get(Calendar.HOUR_OF_DAY)
        if (time.get(Calendar.MINUTE) >= 30) {
            currentTime++
        }
        shiftTime = when (currentTime) {
            in 8..14 -> arrayOf("4 часа", "5 часов", "6 часов", "7 часов", "8 часов")
            15 -> arrayOf("4 часа", "5 часов", "6 часов", "7 часов")
            16 -> arrayOf("4 часа", "5 часов", "6 часов")
            17 -> arrayOf("4 часа", "5 часов")
            18 -> arrayOf("4 часа")
            else -> arrayOf()
        }
    }

    private fun showDialogPayMethods() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Время смены")
        builder.setCancelable(false)
        builder.setSingleChoiceItems(
            shiftTime, 0
        ) { _, which ->
            shiftId = which
        }

        builder.setPositiveButton("НАЧАТЬ СМЕНУ") { dialogs, which ->
            binding.selectTimeShiftLayout.isVisible = false
            courierId?.let { viewModel.startShift(it, shiftId+4) }
            binding.selectedShiftTime.isVisible = true
            dialogs.dismiss()
        }

        builder.setNegativeButton("ОТМЕНИТЬ") { dialogs, which ->
            dialogs.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK)


    }

}