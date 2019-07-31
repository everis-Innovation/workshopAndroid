package com.everis.workshop.data.network.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.everis.workshop.ui.main.MainActivity
import com.everis.workshop.R
import com.everis.workshop.data.network.model.*

class PushFirebaseMessagingService : FirebaseMessagingService() {

    private val GENDER = "gender"
    private val NAME = "name"
    private val EMAIL = "email"
    private val CITY = "city"

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val ID_NOTIFICATION = 0
        private const val REQUEST_CODE = 0
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Check if message contains a notification payload.
        remoteMessage?.let {remoteMessage ->
            if (remoteMessage.data.isNullOrEmpty().not()) {
                handleData(data = remoteMessage.data)
            } else {
                remoteMessage.notification?.let {
                    Log.d(TAG, "Message Notification Body: ${it.body}")
                    sendNotification(it.body)
                }
            }
        }
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.mipmap.ic_launcher_round)
            setContentTitle(getString(R.string.app_name))
            setContentText(messageBody)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setContentIntent(pendingIntent)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(ID_NOTIFICATION, notificationBuilder.build())
    }

    private fun handleData(data: Map<String, String>) {
        val gender: String = data.getOrDefault(GENDER, "")
        val name: Name = Name(data.getOrDefault(NAME, ""), "", "")
        val email = data.getOrElse(EMAIL, {""})
        val location: Location = Location("",
            data.getOrElse(CITY, {""}),"","",
            Coordinates("0","0"), TimeZone("", "Spain"))

        val user = User(gender = gender, name = name, email = email, location = location)

        val resultIntent = Intent(applicationContext, MainActivity::class.java)
        /*resultIntent.putExtra(TAG, user)
        val notificationUtils = NotificationUtils(applicationContext)
        notificationUtils.displayNotification(notificationVO, resultIntent)
        notificationUtils.playNotificationSound()*/

    }
}

