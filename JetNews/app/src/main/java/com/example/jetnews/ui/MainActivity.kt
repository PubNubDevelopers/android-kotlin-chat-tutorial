/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetnews.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.WindowCompat
import com.example.jetnews.BuildConfig
import com.example.jetnews.JetnewsApplication
import com.example.jetnews.R
import com.example.jetnews.utils.PubNubObj
import com.example.jetnews.utils.rememberWindowSizeClass
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.enums.PNPushType
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private val pubNub: PubNub = PubNubObj().getInstance() //Using a singleton PubNub object to persist across the activities.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setting to hide the navigation bar, as it interferes with some of the buttons in some
        //AVDs. Drag up from the bottom to bring up the navigation buttons, or use the navigation
        //buttons in the emulator toolbar.
        WindowCompat.setDecorFitsSystemWindows(window, false)
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        val appContainer = (application as JetnewsApplication).container
        setContent {
            val windowSizeClass = rememberWindowSizeClass()
            JetnewsApp(appContainer, windowSizeClass)
        }
        /*
        //Useful for debugging. Uncomment to grab FCM registration token for the device.
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "${task.exception}", Toast.LENGTH_LONG).show()
                return@OnCompleteListener
            }

            // Get FCM registration token
            val token = task.result
            if (token != null) {
                Toast.makeText(this, token, Toast.LENGTH_LONG).show()
                pubNub.addPushNotificationsOnChannels(
                    pushType = PNPushType.FCM,
                    deviceId = token,
                    channels = listOf("default,default-article,ch1,ch2")
                ).async { result, status ->
                   Log.d("TAG","Status: $status, Result: $result")
                }
            }
        })
        */
        createNotificationChannel() //Notification channel created to receive notifications on device.
    }
    override fun onDestroy() {
        destroyPubNub()
        super.onDestroy()
    }

    private fun destroyPubNub(){
        pubNub.destroy()
    }

    //Creates the notification channel necessary to display incoming push notifications
    //Register the channel with the system
    private fun createNotificationChannel() {
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "default"
            val descriptionText = "default"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel( "push-channel",name, importance).apply {
                description = descriptionText
            }
            //Channel settings
            channel.description = descriptionText
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager?.createNotificationChannel(channel)
        } else {
            Log.d("TAG", "Build.VERSION.SDK_INT < 0. Update SDK.")
        }
    }
}
