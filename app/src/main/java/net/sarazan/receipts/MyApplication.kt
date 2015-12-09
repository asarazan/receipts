package net.sarazan.receipts

import android.app.Application
import com.firebase.client.Firebase
import kotlin.properties.Delegates

/**
 * Created by Aaron Sarazan on 12/8/15
 * Copyright(c) 2015 Level, Inc.
 */
class MyApplication : Application() {

    companion object {
        var firebase: Firebase by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        Firebase.setAndroidContext(this)
        firebase = Firebase("https://brilliant-inferno-5413.firebaseio.com/")
    }
}