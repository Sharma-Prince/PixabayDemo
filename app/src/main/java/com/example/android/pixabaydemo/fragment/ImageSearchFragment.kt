package com.example.android.pixabaydemo.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.android.pixabaydemo.R
import com.example.android.pixabaydemo.databinding.FragmentImageSearchBinding
import com.example.android.pixabaydemo.viewModel.Communicator


class ImageSearchFragment : Fragment(R.layout.fragment_image_search) {

    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!

    private val communicator: Communicator by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchText.setOnClickListener {
            if(binding.searchText.text?.isNotEmpty() == true){
                binding.searchText.hideKeyboard()
                communicator.search.value = binding.searchText.text.toString()

                Navigation.findNavController(view).navigate(R.id.action_imageSearchFragment_to_staggeredImageFragment)
            }

        }
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
