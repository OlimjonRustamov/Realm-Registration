package com.example.realm_registration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val realmName = "myContacts.db"
        val config = RealmConfiguration.Builder().name(realmName).schemaVersion(1)
            .deleteRealmIfMigrationNeeded().build()
        var db = Realm.getInstance(config)
    }
}