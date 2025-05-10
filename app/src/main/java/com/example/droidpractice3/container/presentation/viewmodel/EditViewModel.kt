package com.example.droidpractice3.container.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidpractice3.container.presentation.state.EditState
import com.example.droidpractice3.listwithdetails.data.repository.IProfileRepository
import kotlinx.coroutines.launch

class EditViewModel(
    private val repository: IProfileRepository
) : ViewModel() {
    private val mutableState = MutableEditState()
    val viewState = mutableState as EditState

    init {
        viewModelScope.launch {
            repository.getProfile()?.let {
                mutableState.avatarURI = it.avatarUri.toUri()
                mutableState.name = it.name
                mutableState.documentURL = it.documentUrl
            }
        }
        mutableState.showPermissionDialog = true
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            repository.setProfile(
                viewState.avatarURI.toString(),
                viewState.name,
                viewState.documentURL
            )
        }
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

    fun onPermissionDenied() {
        mutableState.showPermissionDialog = false
    }

    fun onNameChanged(name: String) {
        mutableState.name = name
    }

    fun onDocumentChanged(url: String) {
        mutableState.documentURL = url
    }

    private class MutableEditState : EditState {
        override var avatarURI: Uri by mutableStateOf(Uri.EMPTY)
        override var name by mutableStateOf("")
        override var documentURL by mutableStateOf("")
        override var showPermissionDialog by mutableStateOf(false)
        override var showSelectionDialog by mutableStateOf(false)
    }
}
