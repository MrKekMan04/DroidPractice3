package com.example.droidpractice3.container.presentation.state


import android.net.Uri

interface EditState {
    val avatarURI: Uri
    val name: String
    val documentURL: String
    var showPermissionDialog: Boolean
    var showSelectionDialog: Boolean
}
