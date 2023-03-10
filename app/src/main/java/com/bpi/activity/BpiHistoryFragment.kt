package com.bpi.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpi.utils.BpiAdapter
import com.bpi.databinding.FragmentBpiHistoryBinding
import com.bpi.viewmodel.BpiViewModel

class BpiHistoryFragment : Fragment() {

    private lateinit var binding: FragmentBpiHistoryBinding
    private lateinit var viewModel: BpiViewModel
    private lateinit var adapter: BpiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBpiHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(BpiViewModel::class.java)
        adapter = BpiAdapter()

        binding.tableLayout.adapter = adapter
        binding.tableLayout.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getAllBpi().observe(viewLifecycleOwner) { bpiList ->
            val last10Bpi = bpiList.takeLast(10)
            adapter.setData(last10Bpi)
        }
    }
}
