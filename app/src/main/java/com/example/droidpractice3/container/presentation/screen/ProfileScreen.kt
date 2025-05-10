package com.example.droidpractice3.container.presentation.screen


import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import com.example.droidpractice3.R
import com.example.droidpractice3.container.presentation.viewmodel.ListViewModel
import com.example.droidpractice3.container.presentation.viewmodel.ProfileViewModel
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class ProfileScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(modifier: Modifier) {
        val profileViewModel = koinViewModel<ProfileViewModel>()
        val state by profileViewModel.viewState.collectAsState()
        val navigation = LocalStackNavigation.current
        val listViewModel = koinViewModel<ListViewModel> { parametersOf(navigation) }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = stringResource(R.string.my_films)) })
            },
            floatingActionButton = {
                Button(onClick = { profileViewModel.onUpdateClick() }) {
                    Text(stringResource(R.string.update))
                }
            }
        ) { innerPadding ->
            LazyColumn(Modifier.padding(innerPadding)) {
                items(state.items) { item ->
                    MovieItem(
                        item = item,
                        Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { listViewModel.onItemClicked(item.id) },
                                onDoubleTap = { profileViewModel.onItemDoubleClicked(item.id) }
                            )
                        }
                    )
                }
            }
        }
    }
}
