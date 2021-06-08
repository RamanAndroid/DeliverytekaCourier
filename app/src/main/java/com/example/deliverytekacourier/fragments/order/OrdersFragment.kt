package com.example.deliverytekacourier.fragments.order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliverytekacourier.R
import com.example.deliverytekacourier.data.models.OrdersItem
import com.example.deliverytekacourier.data.viewmodel.DeliverytekaCourierViewModel
import com.example.deliverytekacourier.databinding.FragmentOrdersBinding
import com.example.deliverytekacourier.fragments.order.adapter.OrderAdapter
import com.example.deliverytekacourier.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class OrdersFragment : Fragment(), OrderAdapter.OnOpenClickListener {

    private val viewModel: DeliverytekaCourierViewModel by viewModels()
    private val adapter: OrderAdapter by lazy { OrderAdapter(this) }

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    var courierId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.COURIER_ID, Context.MODE_PRIVATE)
        courierId = sharedPref?.getInt(Constants.COURIER_ID, 0)

        binding.apply {
            rvOrdersList.adapter = adapter
            rvOrdersList.setHasFixedSize(true)
            rvOrdersList.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rvOrdersList.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }

        }


        binding.returnHome.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_startShiftFragment)

        }
        binding.retryBtn.setOnClickListener {
            courierId?.let { viewModel.getOrders(it) }
        }



        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onResume() {
        super.onResume()
        courierId?.let { viewModel.checkOnline(it) }
        viewModel.shift.observe(viewLifecycleOwner, { shift ->
            if (shift[0].start_work_shift.isEmpty()) {
                binding.noShift.isVisible = true
                binding.noProductsInOrderLayout.isVisible = false
                binding.rvOrdersList.isVisible = false
            } else {
                binding.noShift.isVisible = false
                viewModel.getOrders.observe(viewLifecycleOwner) {
                    if (it.result.isNotEmpty()) {
                        binding.noProductsInOrderLayout.isVisible = false
                        adapter.setData(it.result)
                        binding.rvOrdersList.isVisible = true
                    } else {
                        binding.rvOrdersList.isVisible = false
                        binding.noProductsInOrderLayout.isVisible = true
                    }
                }
            }
        })


        courierId?.let { viewModel.getOrders(it) }
        viewModel.getOrders.observe(viewLifecycleOwner, {

            if (it.is_active) {
                val action = OrdersFragmentDirections.actionOrdersFragmentToFragmentOrderDetail(
                    it.result[0],
                    it.result[0].order_datetime,
                    it.result[0].order_id
                )
                findNavController().navigate(action)
            }


        })


    }

    override fun onOpenItemClick(order: OrdersItem) {
        val action = OrdersFragmentDirections.actionOrdersFragmentToFragmentOrderDetail(
            order,
            order.order_datetime,
            order.order_id
        )
        findNavController().navigate(action)
    }


}