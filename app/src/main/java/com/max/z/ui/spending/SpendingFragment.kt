package com.max.z.ui.spending

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.max.z.R
import com.max.z.databinding.FragmentSpendingBinding
import com.max.z.ui.spending.SpendingViewModel

class SpendingFragment : Fragment() {

    private var _binding: FragmentSpendingBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SpendingFragment()
    }

    private val viewModel: SpendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val SpendingViewModel =
            ViewModelProvider(this).get(SpendingViewModel::class.java)

        _binding = FragmentSpendingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        
        return root
//        return inflater.inflate(R.layout.fragment_spending, container, false)
    }

}