package com.example.droidpractice3.container.presentation.screen


import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.droidpractice3.R
import com.example.droidpractice3.container.presentation.viewmodel.EditViewModel
import com.example.droidpractice3.ui.component.EditBar
import com.example.droidpractice3.ui.component.ImageSourceDialog
import com.example.droidpractice3.ui.component.ProfileEditForm
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.io.File
import java.util.Date

@Parcelize
class EditScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<EditViewModel> { parametersOf(navigation) }
        val state = viewModel.viewState
        val context = LocalContext.current

        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val mediaPicker = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            viewModel.onAvatarSelected(uri)
        }
        val pictureTaker = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success: Boolean ->
            if (success) {
                viewModel.onAvatarSelected(imageUri)
            }
        }
        val permissionRequester =
            rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { map: Map<String, Boolean> ->
                if (map.values.contains(false)) {
                    val dialog = AlertDialog.Builder(context)
                        .setMessage(context.getString(R.string.permission_denied))
                        .setCancelable(false)
                        .setPositiveButton(context.getString(R.string.confirm)) { _, _ ->
                            viewModel.onPermissionDenied()
                        }
                    dialog.show()
                }
            }

        fun onCameraSelected() {
            val baseDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
            val pictureFile = File(baseDir, "picture_${Date().time}.jpg")
            imageUri = FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                pictureFile
            )
            imageUri?.let { pictureTaker.launch(it) }
        }

        Scaffold(
            topBar = {
                EditBar(
                    title = stringResource(R.string.profile_edit),
                    onBackPressed = { viewModel.onBackClicked() },
                    onSavePressed = { viewModel.onSaveClicked() }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {}
            ProfileEditForm(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                avatarURI = state.avatarURI,
                onAvatarClicked = { viewModel.onAvatarClicked() },
                name = state.name,
                onNameChanged = { name -> viewModel.onNameChanged(name) },
                documentURL = state.documentURL,
                onDocumentChanged = { url -> viewModel.onDocumentChanged(url) },
                time = state.notificationTime,
                timeString = state.timeString,
                onTimeChanged = { time -> viewModel.onTimeChanged(time) },
                timeError = state.timeError,
                showTimePicker = state.showTimePicker,
                onTimePickerClicked = { viewModel.onTimePickerClicked() },
                onTimeCanceled = { viewModel.onTimeCanceled() },
                onTimeConfirmed = { h, m -> viewModel.onTimeConfirmed(h, m) }
            )
        }

        if (state.showSelectionDialog) {
            ImageSourceDialog(
                onCanceled = { viewModel.onAvatarCanceled() },
                onGallerySelected = {
                    mediaPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                onCameraSelected = {
                    onCameraSelected()
                    viewModel.onAvatarCanceled()
                }
            )
        }

        if (state.showPermissionDialog) {
            LaunchedEffect(Unit) {
                val permissions = mutableListOf<String>()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                    && ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                    && ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissions.add(Manifest.permission.POST_NOTIFICATIONS)
                }

                permissionRequester.launch(permissions.toTypedArray())
            }
        }
    }
}