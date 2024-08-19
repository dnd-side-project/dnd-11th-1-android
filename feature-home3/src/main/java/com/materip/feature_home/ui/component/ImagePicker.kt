@file:OptIn(ExperimentalLayoutApi::class)

package com.materip.feature_home.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.icon.Icons.camera_icon
import com.materip.matetrip.icon.Icons.dismiss_icon
import com.materip.matetrip.theme.MateTripColors.Blue_04
import com.materip.matetrip.theme.MateTripColors.Gray_11
import com.materip.matetrip.theme.MateTripTypographySet


@Composable
fun ImagePicker(
    imageUris: List<String> = emptyList(),
    onImageUrisChange: (List<String>) -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageCount by remember { mutableIntStateOf(imageUris.size) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null && imageCount < 5) {
            imageUri = uri
            onImageUrisChange(imageUris + uri.toString())
            imageCount++
        }
    }

    FlowRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
        maxItemsInEachRow = 5,
    ) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .background(color = Blue_04, shape = RoundedCornerShape(size = 10.dp))
                .padding(start = 18.dp, top = 9.dp, end = 18.dp, bottom = 9.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
        ) {
            IconButton(
                onClick = { imagePickerLauncher.launch("image/*") }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = camera_icon),
                        contentDescription = "Select Image",
                    )
                    Text(
                        text = "$imageCount/5",
                        style = MateTripTypographySet.numberRegular3,
                        color = Gray_11,
                    )
                }
            }
        }
        imageUris.forEach { uri ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(size = 10.dp))
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(uri)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(size = 10.dp))
                        .background(color = Color.Transparent),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = {
                        onImageUrisChange(imageUris - uri)
                        imageCount--
                    },
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.TopEnd)
                        .size(12.dp)
                        .background(color = Color.Unspecified, shape = RoundedCornerShape(size = 8.dp))
                ) {
                    Icon(
                        painter = painterResource(id = dismiss_icon),
                        contentDescription = "Remove Image",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}