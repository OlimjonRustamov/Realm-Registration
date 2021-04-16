package com.example.realm_registration.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.realm_registration.R
import com.example.realm_registration.RealmObjects.Contact
import com.example.realm_registration.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.lang.NullPointerException

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var root:View
    lateinit var db: Realm
    lateinit var contacts:RealmResults<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_login, container, false)
        binding= FragmentLoginBinding.bind(root)

        db= Realm.getDefaultInstance()

        contacts = db.where<Contact>().findAll()

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
            if (tel_raqam != "" && password != "") {
                var parol= ""
                try {
                    parol =db.where<Contact>().equalTo("tel_raqam",tel_raqam).findFirst()!!.parol
                } catch (e: NullPointerException) {
//                    Toast.makeText(binding.root.context, "Bunday raqamli foydalanuvchi mavjud emas", Toast.LENGTH_SHORT).show()
                }
                if (parol != "" && parol == password) {
                    Snackbar.make(root, "Muvaffaqiyatli amalga oshirildi", Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(R.id.usersFragment)
                } else {
                    Snackbar.make(root, "Noto'g'ri ma'lumotlar kiritdingiz!", Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(root,"Barcha maydonlarni to'ldiring", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}