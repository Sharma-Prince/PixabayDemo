package com.example.android.pixabaydemo.fragment


import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.android.pixabaydemo.R
import com.example.android.pixabaydemo.databinding.FragmentBiometricBinding
import java.util.concurrent.Executor

class BiometricFragment : Fragment() {

    private var _binding: FragmentBiometricBinding? = null
    private val binding get() = _binding!!

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var isBiometric: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(this,executor,object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_biometricFragment2_to_imageSearchFragment) }
            }
        })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint authentication")
            .setNegativeButtonText("Use App Password insted")
            .build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBiometricBinding.inflate(inflater, container, false)
        val canAuthenticate = context?.let { BiometricManager.from(it.applicationContext).canAuthenticate() }
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            binding.useBiometric.setText(R.string.biometrics)
        }else{
            binding.animationView.visibility=View.GONE
            binding.useBiometric.setText(R.string.skip)
            isBiometric = false
        }
        binding.useBiometric.setOnClickListener {
            if(isBiometric){
                biometricPrompt.authenticate(promptInfo)
            }else{
                Navigation.findNavController(it).navigate(R.id.action_biometricFragment2_to_imageSearchFragment)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}