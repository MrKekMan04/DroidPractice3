package com.example.droidpractice3.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidpractice3.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditableBar(
    title: String = "",
    onEditPressed: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onEditPressed() }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun EditableBarPreview() {
    EditableBar(title = stringResource(R.string.profile))
}
