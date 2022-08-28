package com.example.hashgenerator

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel: ViewModel() {
    fun getHash(plainText: String, algorithsm: String): String{
        val bytes = MessageDigest.getInstance(algorithsm).digest(plainText.toByteArray())
        return toHex(bytes)
    }
    private fun toHex(byteArray: ByteArray): String{
        Log.d("ViewModel",byteArray.joinToString(""){"%02x".format(it)})
        return byteArray.joinToString(""){"%02x".format(it)}
    }

}