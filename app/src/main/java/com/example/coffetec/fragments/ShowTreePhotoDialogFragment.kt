package com.example.coffetec.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.coffetec.R
import com.example.coffetec.databinding.DialogFragmentShowTreePhotoBinding

class ShowTreePhotoDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentShowTreePhotoBinding? = null
    private val binding get() = _binding!!

    var uri: Uri? = null

    //ACOMODA LA VISTA
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DialogFragmentShowTreePhotoBinding.inflate(inflater,container,false)
        binding.closeImageDialogFragment.setOnClickListener { dismiss() }
        if (uri!=null){
            Glide.with(binding.imageDialogFragment).load(uri).into(binding.imageDialogFragment)
        }
        return binding.root
    }

    fun loadUri(uri: Uri?){
        this.uri = uri
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShowTreePhotoDialogFragment()
    }
}