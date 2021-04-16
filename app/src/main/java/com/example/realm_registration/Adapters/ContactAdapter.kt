//package com.example.realm_registration.Adapters
//
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import android.view.View
//import android.view.ViewGroup
//import com.example.realm_registration.RealmObjects.Contact
//import com.example.realm_registration.databinding.ItemContactBinding
//import io.realm.RealmBasedRecyclerViewAdapter
//import io.realm.RealmResults
//import io.realm.RealmViewHolder
//import java.lang.Exception
//
//class ContactAdapter(context: Context, var contacts: RealmResults<Contact>) :
//    RealmBasedRecyclerViewAdapter<Contact, ContactAdapter.Vh>(context, contacts, true, false) {
//
//    var binding = ItemContactBinding.inflate(inflater, null, false)
//
//    inner class Vh(itemView: View) : RealmViewHolder(itemView) {
//        fun myBind(contact: Contact) {
//            binding.itemContactImage.setImageURI(Uri.parse(contact.image_path))
//            binding.itemContactIsmFamilya.text = contact.ism_familya
//            binding.itemContactTelRaqam.text = contact.tel_raqam
//        }
//    }
//
//    override fun onBindRealmViewHolder(p0: Vh, p1: Int) {
//        try {
//            p0.myBind(contacts[p1]!!)
//        } catch (e: Exception) {
//            Log.d("TTTT", "onBindRealmViewHolder:" + e)
//        }
//    }
//
//    override fun onCreateRealmViewHolder(p0: ViewGroup, p1: Int): Vh {
//        return Vh(binding.root)
//    }
//
//}