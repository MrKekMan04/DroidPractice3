package com.example.droidpractice3.container.presentation.state


import android.net.Uri
import org.threeten.bp.LocalTime

interface EditState {
    val avatarURI: Uri
    val name: String
    val documentURL: String
    val notificationTime: LocalTime
    val timeString: String
    val timeError: String?
    var showPermissionDialog: Boolean
    var showSelectionDialog: Boolean
    val showTimePicker: Boolean
}
