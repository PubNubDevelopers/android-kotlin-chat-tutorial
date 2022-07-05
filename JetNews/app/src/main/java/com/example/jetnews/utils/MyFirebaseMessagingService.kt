package com.example.jetnews.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.jetnews.R
import com.example.jetnews.ui.chat.ChatActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pubnub.api.PubNub
import com.pubnub.api.enums.PNPushType

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val pubNub: PubNub = PubNubObj().getInstance()

    //Called when the app is first opened from the device.
    //Registers the device on the PubNub network to receive push notfications.
    override fun onNewToken(token: String) {
        sendRegistrationToPubNub(token)
    }

    //Handle when the device has received a push notification from FCM.
    //Handled whenever App is in Foreground, or receive Data payload.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        var title : String = ""
        var body : String = ""

        // Check if message contains a notification payload.
        // Handled client-side if app in foreground.
        // Handled by FCM if app in background/closed.
        remoteMessage?.data?.let {
            //Log.d(TAG, "Message data payload: " + remoteMessage.data)
            title = remoteMessage.data?.get("title") .toString()
            body = remoteMessage.data?.get("body").toString()
        }

        // Check if message contains a notification payload. Handled client-side.
        remoteMessage?.notification?.let {
            //Log.d(TAG, "Message Notification Body: ${it.body}")
            title = remoteMessage.notification?.title.toString()
            body = remoteMessage.notification?.body.toString()
        }
        sendNotification(title, body)
    }

    //Register the device on PubNub Channels to enable push notifications on those channels.
    private fun sendRegistrationToPubNub(token : String) {
        if (token != null) {
            pubNub.addPushNotificationsOnChannels(
                pushType = PNPushType.FCM,
                deviceId = token,
                channels = listOf("default,default-article,ch1,ch2") //provide a list of channels to enable push on them.
            ).async { result, status ->
                Log.d("TAG","Status: $status, Result: $result")
            }
        }
    }

    //Send the notification to the device manager to display the notification to the user.
    private fun sendNotification(title: String, body: String) {
        //Need to assign a notification id for each notification. Can be randomly generated or set.
        //Useful since it can be updated later on in the app.
        val notificationID = 101

        //Change the intent to navigate to the chat room when clicking on the notification.
        val intent = Intent(this, ChatActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pend = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val channelID = getString(R.string.default_notification_channel_id)

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this@MyFirebaseMessagingService,
                channelID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .setContentIntent(pend)
                .setAutoCancel(true)
                .build()
        } else {
           Log.d("TAG", "Build.VERSION.SDK_INT < 0. Update SDK.")
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager?.notify(notificationID, notification as Notification?)
    }
}