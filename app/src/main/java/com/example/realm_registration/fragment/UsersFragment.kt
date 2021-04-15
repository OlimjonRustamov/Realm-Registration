package com.example.realm_registration.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realm_registration.R
import com.example.realm_registration.databinding.FragmentUsersBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class UsersFragment : Fragment() {
    lateinit var root: View
    lateinit var binding: FragmentUsersBinding
//    lateinit var db:MyDbHelper

//    var contactList=ArrayList<Contact>()
//    var contactAdapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_users, container, false)
        binding= FragmentUsersBinding.bind(root)
//        db = MyDbHelper(root.context)

//        makeAdapter()

//        contactAdapter!!.myonItemClick=object:ContactAdapter.onItemClick{
//            override fun onClick(contact: Contact) {
//                val btmdialog = BottomSheetDialog(root.context, R.style.BottomSheetDialogTheme)
//                val dialog_view = LayoutInflater.from(root.context).inflate(R.layout.call_sms_bottom_dialog, null, false)
//                dialog_view.item_contact_image.setImageURI(Uri.parse(contact.image_path))
//                dialog_view.item_contact_ism_familya.text = contact.ism_familya
//                dialog_view.item_contact_tel_raqam.text = contact.manzil
//                dialog_view.call_btn.setOnClickListener {
//                    Dexter.withContext(activity)
//                            .withPermission(Manifest.permission.CALL_PHONE)
//                            .withListener(object : PermissionListener {
//                                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                                    val numberText = contact.tel_raqam
//                                    val intent = Intent(Intent.ACTION_CALL)
//                                    intent.data = Uri.parse("tel:$numberText")
//                                    startActivity(intent)
//                                }
//
//                                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//                                    Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
//                                }
//
//                                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
//                                    Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
//                                }
//                            })
//                            .check();
//                }
//                dialog_view.sms_btn.setOnClickListener {
//                    Dexter.withContext(activity)
//                            .withPermission(Manifest.permission.SEND_SMS)
//                            .withListener(object : PermissionListener {
//                                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                                    val sms_uri = Uri.parse("smsto:${contact.tel_raqam}")
//                                    val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)
//                                    startActivity(sms_intent)
//                                }
//
//                                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//                                    Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
//                                    Toast.makeText(root.context, "Ruxsat berish zarur", Toast.LENGTH_SHORT).show()
//                                }
//
//                                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
//                                    Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
//                                    Toast.makeText(root.context, "Ruxsat berish zarur", Toast.LENGTH_SHORT).show()
//                                }
//                            })
//                            .check();
//                }
//                btmdialog.setContentView(dialog_view)
//                btmdialog.show()
//            }
//        }

        return root
    }

//    private fun makeAdapter() {
//        contactList=db.getAllContacts()
//        contactAdapter = ContactAdapter(contactList)
//        binding.contactsRv.adapter = contactAdapter
//        binding.contactsRv.layoutManager= LinearLayoutManager(root.context)
//    }
}