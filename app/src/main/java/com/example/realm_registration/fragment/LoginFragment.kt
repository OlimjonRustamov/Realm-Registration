package com.example.realm_registration.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.realm_registration.R
import com.example.realm_registration.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var root:View
//    lateinit var db:MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_login, container, false)
        binding= FragmentLoginBinding.bind(root)
//        db= MyDbHelper(root.context)

        setCLicksMtd()
        return root
    }

    private fun setCLicksMtd() {
        binding.loginSingupbtn.setOnClickListener{
            findNavController().navigate(R.id.registrationFragment)
        }
        binding.loginCardBtn.setOnClickListener {
            val tel_raqam = binding.loginTelRaqamEt.text.toString().trim()
            val password = binding.loginPasswordEt.text.toString().trim()
//            if (tel_raqam != "" && password != "") {
//                val parol = db.getContactByTelNumber(tel_raqam)
//                if (parol == password) {
//                    Snackbar.make(root, "Muvaffaqiyatli amalga oshirildi", Snackbar.LENGTH_LONG).show()
//                    findNavController().navigate(R.id.usersFragment)
//                } else {
//                    Snackbar.make(root,"Noto'g'ri ma'lumotlar kiritdingiz!", Snackbar.LENGTH_LONG).show()
//                }
//            } else {
//            }
                Snackbar.make(root,"Bajarilmoqda", Snackbar.LENGTH_LONG).show()
        }
    }
}