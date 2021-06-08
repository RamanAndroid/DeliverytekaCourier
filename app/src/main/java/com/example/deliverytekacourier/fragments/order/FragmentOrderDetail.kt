package com.example.deliverytekacourier.fragments.order

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliverytekacourier.R
import com.example.deliverytekacourier.data.viewmodel.DeliverytekaCourierViewModel
import com.example.deliverytekacourier.databinding.FragmentOrderDetailBinding
import com.example.deliverytekacourier.fragments.order.adapter.OrderContentAdapter
import com.example.deliverytekacourier.utility.Constants
import com.example.deliverytekacourier.utility.Utils
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class FragmentOrderDetail : Fragment() {
    private val viewModel: DeliverytekaCourierViewModel by viewModels()
    private val args by navArgs<FragmentOrderDetailArgs>()
    private val adapter: OrderContentAdapter by lazy { OrderContentAdapter() }
    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!


    var courierId: Int? = 0
    var isOpen: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.COURIER_ID, Context.MODE_PRIVATE)
        courierId = sharedPref?.getInt(Constants.COURIER_ID, 0)
        courierId?.let { viewModel.getOrders(it) }

        binding.apply {
            rvOrdersList.adapter = adapter
            rvOrdersList.setHasFixedSize(true)
            rvOrdersList.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rvOrdersList.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }

            viewModel.getOrderContent(args.order.order_id)
            viewModel.getOrderContent.observe(viewLifecycleOwner) {
                adapter.setData(it.result)
            }

            order = args.order

            binding.callUserBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${args.order.user_phone}")
                startActivity(intent)
            }

            binding.acceptOrderBtn.setOnClickListener {
                courierId?.let { viewModel.acceptOrder(it, args.order.order_id) }
                Thread.sleep(1000)
                if (binding.acceptOrderBtn.text == "Прибыл к клиенту") {
                    findNavController().navigate(R.id.action_fragmentOrderDetail_to_ordersFragment)
                    Toast.makeText(
                        requireContext(),
                        "Заказ успешно выполнен.Спасибо за ваш труд!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    courierId?.let { viewModel.getOrders(it) }
                }

            }

            binding.directionsUserBtn.setOnClickListener {
                val words = args.order.user_address.trim().split(" ")
                val text = "${words[0]} ${words[1]} ${words[2]}"
                val gmmIntentUri = Uri.parse("geo:0,0?q=$text")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)


            }

            binding.showOrderContentBtn.setOnClickListener {
                if (!isOpen) {
                    isOpen = true
                    binding.rvOrdersList.isVisible = true
                } else {
                    isOpen = false
                    binding.rvOrdersList.isVisible = false
                }
            }

            viewModel.getOrders.observe(viewLifecycleOwner) {
                binding.acceptOrderBtn.text = Utils.getStatusOrder(it.result[0].order_status_id)

            }

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}