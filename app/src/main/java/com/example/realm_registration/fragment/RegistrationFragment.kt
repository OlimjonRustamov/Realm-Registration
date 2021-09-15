package com.example.realm_registration.fragment

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.realm_registration.Adapters.SpinnerAdapter
import com.example.realm_registration.BuildConfig
import com.example.realm_registration.R
import com.example.realm_registration.RealmObjects.Contact
import com.example.realm_registration.databinding.FragmentRegistrationBinding
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
import kotlinx.android.synthetic.main.camera_or_gallery_dialog.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistrationFragment : Fragment() {
    lateinit var dialog_view: View
    lateinit var db: Realm
    lateinit var root: View
    lateinit var binding: FragmentRegistrationBinding
    var spinnerList = ArrayList<String>()
    lateinit var dialog: AlertDialog

    lateinit var contacts: RealmResults<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_registration, container, false)
        binding = FragmentRegistrationBinding.bind(root)
        db = Realm.getDefaultInstance()

        dialog_view = LayoutInflater.from(root.context)
            .inflate(R.layout.camera_or_gallery_dialog, null, false)
        photoUri = FileProvider.getUriForFile(root.context, BuildConfig.APPLICATION_ID, imageFile)


        loadData()
        setClickMtd()//opens dialog
        setCameraClick()
        setGalleryClick()
        return root
    }

    private fun loadData() {
        spinnerList.add("O'zbekiston")
        spinnerList.add("Rossiya")
        spinnerList.add("Ukraina")
        spinnerList.add("AQSH")
        spinnerList.add("Buyuk Britaniya")
        spinnerList.add("Qozog'iston")
        spinnerList.add("Tojikiston")

        val spinnerAdapter = SpinnerAdapter(spinnerList)
        binding.registrationDavlatSpinner.adapter = spinnerAdapter

        contacts = db.where<Contact>().findAll()
        Log.d("TTTT", "loadData: $contacts")
    }

    private fun setClickMtd() {
        dialog = AlertDialog.Builder(root.context).create()
        binding.constraintImage.setOnClickListener {
            dialog_view.dialog_bekor_qilish.setOnClickListener {
                dialog.cancel()
                dialog.dismiss()
            }
            dialog.setView(dialog_view)

            dialog.show()
        }

        binding.registrCardBtn.setOnClickListener {
            val ism_familya = binding.registationIsmFamilyaEt.text.toString().trim()
            val tel_raqam = binding.registationTelRaqamEt.text.toString().trim()
            val davlat = spinnerList[binding.registrationDavlatSpinner.selectedItemPosition]

            val manzil = binding.registationManzilEt.text.toString().trim()
            val parol = binding.registationParolEt.text.toString().trim()
            var t = true
            try {
                for (i in 0 until contacts.size) {
                    if (contacts[i]!!.tel_raqam == tel_raqam) {
                        t = false
                    }
                }
            } catch (e: Exception) {
                Log.d("TUIT", "setClickMtd: $e")
            }

            if (ism_familya != "" && tel_raqam != "" && davlat != "" && manzil != "" && parol != "" && image_path != "") {
                if (t) {
                    val contact = Contact()
                    contact.id = getCurrentIndex()
                    contact.ism_familya = ism_familya
                    contact.parol = parol
                    contact.tel_raqam = tel_raqam
                    contact.manzil = manzil
                    contact.image_path = image_path
                    contact.davlat = davlat

                    db.beginTransaction()
                    db.insert(contact)
                    db.commitTransaction()

                    findNavController().popBackStack()
                    Snackbar.make(root, "Muvaffaqiyatli bajarildi", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(root, "Ushbu raqamli foydalanuvchi mavjud", Snackbar.LENGTH_LONG)
                        .show()
                }
            } else {
                Snackbar.make(root, "Barcha maydonlarni to'ldiring!", Snackbar.LENGTH_LONG).show()
            }
        }
    }


    private var image_path = ""

    //upload image from GALLERY
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            binding.registrationImageview.setImageURI(uri)
            val ins = activity?.contentResolver?.openInputStream(uri)
            val file = File(activity?.filesDir, "${getCurrentIndex()}.jpg")
            val fileOutputStream = FileOutputStream(file)
            ins?.copyTo(fileOutputStream)
            ins?.close()
            fileOutputStream.close()
            image_path = file.absolutePath
        }

    private fun setGalleryClick() {
        dialog_view.choose_gallery_btn.setOnClickListener {
            Dexter.withContext(activity)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        getImageContent.launch("image/*")
                        dialog.cancel()
                        dialog.dismiss()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
                    }
                })
                .check();
        }
    }


    val imageFile = createImageFile()
    lateinit var photoUri: Uri
    lateinit var currentPhotoPath: String

    //upload image from CAMERA
    private val getCameraImage = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            binding.registrationImageview.setImageURI(photoUri)
            val ins = activity?.contentResolver?.openInputStream(photoUri)
            val file = File(activity?.filesDir, "${getCurrentIndex()}.jpg")
            val fileOutputStream = FileOutputStream(file)
            ins?.copyTo(fileOutputStream)
            ins?.close()
            fileOutputStream.close()
            image_path = file.absolutePath
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun setCameraClick() {
        dialog_view.choose_camera_btn.setOnClickListener {
            Dexter.withContext(activity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        getCameraImage.launch(photoUri)
                        dialog.cancel()
                        dialog.dismiss()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        Snackbar.make(root, "Ruxsat berish zarur", Snackbar.LENGTH_LONG).show()
                    }
                }).check();
        }
    }

    //rasmni takrorlanmas nom bilan saqlash
    //quyidagi funksiya bazada turgan oxirgi elementning image_path'ini olib +1 qilib qaytaradi
    //lekin bazaga oxirgi qo'shilgan oxirida turmas ekan shuni to'g'rilash kerak
    private fun getCurrentIndex(): Int {
        var index = 0
        if (contacts.size != 0) {
            try {
                index = contacts[0]!!.id
                for (i in 0 until contacts.size) {
                    if (index < contacts[i]!!.id) {
                        index = contacts[i]!!.id
                    }
                }
                return index + 1
            } catch (e: NullPointerException) {
                return 0
            } catch (e: NumberFormatException) {
                return 0
            }
        }
        return 0

    }
}