package com.example.realm_registration.fragment

//import com.example.realm_registration.Adapters.ContactAdapter
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realm_registration.Adapters.ContactAdapter
import com.example.realm_registration.R
import com.example.realm_registration.RealmObjects.Contact
import com.example.realm_registration.databinding.FragmentUsersBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.call_sms_bottom_dialog.view.*

class UsersFragment : Fragment() {
    lateinit var root: View
    lateinit var binding: FragmentUsersBinding
    lateinit var db: Realm

    lateinit var contactList: RealmResults<Contact>
    var contactAdapter: ContactAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_users, container, false)
        binding = FragmentUsersBinding.bind(root)
        db = Realm.getDefaultInstance()

        makeAdapter()

        contactAdapter!!.myonItemClick = object : ContactAdapter.onItemClick {
            override fun onClick(contact: Contact) {
                val btmdialog = BottomSheetDialog(root.context, R.style.BottomSheetDialogTheme)
                val dialog_view = LayoutInflater.from(root.context)
                    .inflate(R.layout.call_sms_bottom_dialog, null, false)
                dialog_view.item_contact_image.setImageURI(Uri.parse(contact.image_path))
                dialog_view.item_contact_ism_familya.text = contact.ism_familya
                dialog_view.item_contact_tel_raqam.text = contact.manzil
                dialog_view.call_btn.setOnClickListener {
                    Dexter.withContext(activity)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(object : PermissionListener {
                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                                val numberText = contact.tel_raqam
                                val intent = Intent(Intent.ACTION_CALL)
                                intent.data = Uri.parse("tel:$numberText")
                                startActivity(intent)
                            }

                            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                                btmdialog.cancel()
                                Snackbar.make(
                                    binding.root,
                                    "Ruxsat berish zarur",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                p0: PermissionRequest?,
                                p1: PermissionToken?
                            ) {
                                btmdialog.cancel()
                                Snackbar.make(
                                    binding.root,
                                    "Ruxsat berish zarur",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        })
                        .check();
                }
                dialog_view.sms_btn.setOnClickListener {
                    Dexter.withContext(activity)
                        .withPermission(Manifest.permission.SEND_SMS)
                        .withListener(object : PermissionListener {
                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                                val sms_uri = Uri.parse("smsto:${contact.tel_raqam}")
                                val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)
                                startActivity(sms_intent)
                            }

                            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                                btmdialog.cancel()
                                Snackbar.make(
                                    binding.root,
                                    "Ruxsat berish zarur",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                p0: PermissionRequest?,
                                p1: PermissionToken?
                            ) {
                                btmdialog.cancel()
                                Snackbar.make(
                                    binding.root,
                                    "Ruxsat berish zarur",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        })
                        .check();
                }
                btmdialog.setContentView(dialog_view)
                btmdialog.show()
            }
        }

        return root
    }

    private fun makeAdapter() {
        contactList = db.where<Contact>().findAll()
        contactAdapter = ContactAdapter(binding.root.context, contactList)
        binding.contactsRv.setAdapter(contactAdapter)
        binding.contactsRv.layoutManager = LinearLayoutManager(root.context)
    }
}