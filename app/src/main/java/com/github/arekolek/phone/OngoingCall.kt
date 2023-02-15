package com.github.arekolek.phone

import android.graphics.drawable.Icon
import android.telecom.Call
import android.telecom.PhoneAccount
import android.telecom.TelecomManager
import android.telecom.VideoProfile
import android.widget.ImageView
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

object OngoingCall {
    public lateinit var mIcon: Icon

    val state: BehaviorSubject<Int> = BehaviorSubject.create()

    private val callback = object : Call.Callback() {
        override fun onStateChanged(call: Call, newState: Int) {
            Timber.d(call.toString())
            Timber.d(call.details?.accountHandle?.id?.toString())
            var phoneAccount = App.applicationContext().getSystemService(TelecomManager::class.java).getPhoneAccount(call.details?.accountHandle)
            Timber.d(App.applicationContext().getSystemService(TelecomManager::class.java).getLine1Number(call.details?.accountHandle))
            Timber.d(phoneAccount?.icon?.toString())

            mIcon = phoneAccount.icon

            state.onNext(newState)
        }
    }

    var call: Call? = null
        set(value) {
            field?.unregisterCallback(callback)
            value?.let {
                it.registerCallback(callback)
                state.onNext(it.state)
            }
            field = value
        }

    fun answer() {
        call!!.answer(VideoProfile.STATE_AUDIO_ONLY)
    }

    fun hangup() {
        call!!.disconnect()
    }
}
