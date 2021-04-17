package com.example.realm_registration.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.realm_registration.RealmObjects.Contact
import com.example.realm_registration.databinding.ItemContactBinding
import io.realm.RealmResults

class  ContactAdapter(var context:Context,var contacts: RealmResults<Contact>) :
    RecyclerView.Adapter<ContactAdapter.Vh>() {

    var myonItemClick:onItemClick?=null
    lateinit var binding:ItemContactBinding

    inner class Vh(var binding:ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun myBind(contact: Contact) {
            binding.itemContactIsmFamilya.text = contact.ism_familya
            binding.itemContactTelRaqam.text = contact.tel_raqam
            binding.itemContactImage.setImageURI(Uri.parse(contact.image_path))
            binding.itemContactCard.setOnClickListener {
                if (myonItemClick != null) {
                    myonItemClick!!.onClick(contact)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemContactBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        try {
            holder.myBind(contacts[position]!!)
        } catch (e: NullPointerException) {
            Toast.makeText(binding.root.context, "Kutilmagan xatolik", Toast.LENGTH_SHORT).show()
        }
    }

    interface onItemClick{
        fun onClick(contact: Contact)
    }
}