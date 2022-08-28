package com.example.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashgenerator.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SuccessFragment : Fragment() {

    private val args: SuccessFragmentArgs by navArgs()

    private var _binding : FragmentSuccessBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(inflater,container,false)

        binding.tvHash.text=args.hash
        
        binding.btnCoppy.setOnClickListener {
            onCoppyClick()
        }

        return binding.root
    }

    private fun onCoppyClick() {
        lifecycleScope.launch {
            copyToClipboard(args.hash)
            applyAnimation()
        }

    }

    private fun copyToClipboard(hash: String) {
        val clipboardManager  = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encypted text", hash)
        clipboardManager.setPrimaryClip(clipData)
    }
    private suspend fun applyAnimation(){
        binding.include.messageBackground.animate().translationY(80f).duration = 200L
        binding.include.tvMessage.animate().translationY(80f).duration = 200L

        delay(2000L)

        binding.include.messageBackground.animate().translationY(-80f).duration = 500L
        binding.include.tvMessage.animate().translationY(-80f).duration = 500L
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}