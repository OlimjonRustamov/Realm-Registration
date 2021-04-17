package com.example.realm_registration.RealmObjects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

//
//import io.realm.RealmObject
//import io.realm.annotations.PrimaryKey
//import io.realm.annotations.Required

open class Contact
    : RealmObject() {

    @PrimaryKey
    var id: Int = 0

    @Required
    var tel_raqam: String = "tel_raqam"

    @Required
    var ism_familya: String = "ism_familya"

    @Required
    var davlat: String = "davlat"

    @Required
    var manzil: String = "manzil"

    @Required
    var parol: String = "parol"

    @Required
    var image_path: String = "image_path"
}