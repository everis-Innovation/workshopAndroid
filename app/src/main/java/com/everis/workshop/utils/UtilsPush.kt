package com.everis.workshop.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.everis.workshop.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class UtilsPush {
    companion object {
        private val TAG = "UtilsPush"

        fun firebasePushRegister(context: Context) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val token = task.result?.token
                    // Log
                    val msg = context.getString(R.string.msg_token_fmt, token)
                    Log.d(TAG, msg)
                })
        }
    }
}