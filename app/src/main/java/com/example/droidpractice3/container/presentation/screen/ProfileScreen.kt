package com.example.droidpractice3.container.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.droidpractice3.R
import com.example.droidpractice3.container.presentation.viewmodel.ProfileViewModel
import com.example.droidpractice3.ui.component.EditableBar
import com.example.droidpractice3.ui.component.ProfileCard
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.terrakok.modo.stack.forward
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class ProfileScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<ProfileViewModel> { parametersOf(navigation) }
        val state = viewModel.viewState

        Scaffold(
            topBar = {
                EditableBar(
                    title = stringResource(R.string.profile),
                    onEditPressed = { navigation.forward(EditScreen()) }
                )
            }
        ) { paddingValues ->
            ProfileCard(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                avatarURI = state.avatarURI,
                name = state.name,
                documentURL = state.documentURL,
                onDocumentClicked = viewModel::onDocumentClick
            )
        }
    }
}
