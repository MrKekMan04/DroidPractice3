package com.example.droidpractice3.container.presentation.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidpractice3.R
import com.example.droidpractice3.container.presentation.notification.receiver.NotificationsReceiver
import com.example.droidpractice3.container.presentation.state.EditState
import com.example.droidpractice3.listwithdetails.data.repository.IProfileRepository
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class EditViewModel(
    private val repository: IProfileRepository,
    private val navigation: StackNavContainer,
) : ViewModel() {
    private val mutableState = MutableEditState()
    val viewState = mutableState as EditState
    private val context: Context by KoinJavaComponent.inject(Context::class.java)
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    init {
        viewModelScope.launch {
            repository.getProfile()?.let {
                mutableState.avatarURI = it.avatarUri.toUri()
                mutableState.name = it.name
                mutableState.documentURL = it.documentUrl
                tryParse(it.notificationTime)?.let { time ->
                    mutableState.notificationTime = time
                    updateTimeString()
                }
            }
        }
        mutableState.showPermissionDialog = true
    }

    fun onBackClicked() = navigation.back()

    fun onSaveClicked() {
        validateTime()
        if (mutableState.timeError != null) return
        viewModelScope.launch {
            repository.setProfile(
                viewState.avatarURI.toString(),
                viewState.name,
                viewState.documentURL,
                viewState.notificationTime
            )
        }
        saveNotification()
        onBackClicked()
    }

    fun onAvatarClicked() {
        mutableState.showSelectionDialog = true
    }

    fun onAvatarCanceled() {
        mutableState.showSelectionDialog = false
    }

    fun onAvatarSelected(uri: Uri?) {
        uri?.let { mutableState.avatarURI = it }
    }

    fun onPermissionDenied() = onBackClicked()

    fun onNameChanged(name: String) {
        mutableState.name = name
    }

    fun onDocumentChanged(url: String) {
        mutableState.documentURL = url
    }

    fun onTimePickerClicked() {
        mutableState.showTimePicker = true
    }

    fun onTimeChanged(time: String) {
        mutableState.timeString = time
        validateTime()
    }

    fun onTimeConfirmed(h: Int, m: Int) {
        mutableState.notificationTime = LocalTime.of(h, m)
        mutableState.timeError = null
        updateTimeString()
        onTimeCanceled()
    }

    fun onTimeCanceled() {
        mutableState.showTimePicker = false
    }

    private fun saveNotification() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val dateTime = LocalDateTime.of(LocalDate.now(), viewState.notificationTime)
        val timeInMillis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val notifyIntent = Intent(context, NotificationsReceiver::class.java)
        notifyIntent.putExtras(
            Bundle().apply {
                putString(
                    NotificationsReceiver.EXTRA_NOTIFICATION,
                    context.getString(R.string.notifications_content, viewState.name)
                )
            }
        )

        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                notifyPendingIntent
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun validateTime() {
        try {
            mutableState.notificationTime = LocalTime.parse(mutableState.timeString, formatter)
            mutableState.timeError = null
        } catch (_: Exception) {
            mutableState.timeError = context.getString(R.string.notifications_error_invalid)
        }
    }

    private fun updateTimeString() {
        mutableState.timeString = formatter.format(viewState.notificationTime)
    }

    private fun tryParse(date: String): LocalTime? {
        return try {
            LocalTime.parse(date)
        } catch (_: Exception) {
            null
        }
    }

    private class MutableEditState : EditState {
        override var avatarURI: Uri by mutableStateOf(Uri.EMPTY)
        override var name by mutableStateOf("")
        override var documentURL by mutableStateOf("")
        override var notificationTime: LocalTime by mutableStateOf(LocalTime.now())
        override var timeString: String by mutableStateOf("")
        override var timeError: String? by mutableStateOf(null)
        override var showPermissionDialog by mutableStateOf(false)
        override var showSelectionDialog by mutableStateOf(false)
        override var showTimePicker: Boolean by mutableStateOf(false)
    }
}
