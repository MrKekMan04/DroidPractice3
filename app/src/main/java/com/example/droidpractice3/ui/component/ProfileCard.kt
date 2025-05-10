package com.example.droidpractice3.ui.component


import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.droidpractice3.R
import com.example.droidpractice3.ui.theme.Typography

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    avatarURI: Uri = Uri.EMPTY,
    name: String = "",
    documentURL: String = "",
    onDocumentClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = avatarURI,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(128.dp),
            error = painterResource(R.drawable.mobile_phone)
        )
        if (name.isNotBlank()) {
            Text(
                text = name,
                modifier = Modifier.padding(16.dp),
                style = Typography.headlineLarge
            )
        }
        if (documentURL.isNotBlank()) {
            Button(onClick = { onDocumentClicked() }) {
                Text(text = stringResource(R.string.document))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ProfileCard(name = stringResource(R.string.name))
}
