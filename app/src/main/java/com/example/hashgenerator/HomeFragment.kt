package com.example.hashgenerator

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgenerator.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private val homeViewModel : HomeViewModel by viewModels()
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val hasAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,hasAlgorithms)
        binding.acTextView.setAdapter(arrayAdapter)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)


        binding.btnGenerate.setOnClickListener {
            onGenerateClicked()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear_menu -> binding.editTextTextPersonName.text.clear()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onGenerateClicked(){
        if(binding.editTextTextPersonName.text.isEmpty()){
            showSnackerBar("Field Empty.")
        }else {
            lifecycleScope.launch {
                applyAnimtions()
                navigateToSuccess(getHashData())
            }
        }
    }

    private fun getHashData():String{
        val algorithm = binding.acTextView.text.toString()
        val plainText = binding.editTextTextPersonName.text.toString()
        return homeViewModel.getHash(plainText,algorithm)
    }
    private suspend fun applyAnimtions(){
        binding.btnGenerate.isClickable = false
        binding.tvTitle.animate().alpha(0f).duration = 400L
        binding.btnGenerate.animate().alpha(0f).duration = 400L
        binding.textInputEditText.animate().alpha(0f).translationXBy(1200f).duration = 400L
        binding.editTextTextPersonName.animate().alpha(0f).translationXBy(-1200f).duration = 400L
        delay(300)

        binding.view.animate().apply {
            alpha(1f).duration = 600L
            rotationBy(720f).duration = 600L
            scaleXBy(900f).duration = 800L
            scaleYBy(900f).duration = 800L
        }
        delay(500)

        binding.imgCheck.animate().alpha(1f).duration = 1000L
        delay(1500)
    }

    private fun  navigateToSuccess(hash: String){
        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hash)
        findNavController().navigate(directions)
    }

    private fun showSnackerBar(message : String){
        val snackBar = Snackbar.make(binding.rootLayout,message,Snackbar.LENGTH_SHORT)
        snackBar.setAction("Okay"){}
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(),R.color.blue))
        snackBar.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}