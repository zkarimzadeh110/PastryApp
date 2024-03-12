package com.example.pastry.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfilePhotoElement(modifier:Modifier,photoPath:String){
    GlideImage(
        modifier=modifier
            .clip(MaterialTheme.shapes.extraLarge),
        model = photoPath,
        contentDescription = "profile photo",
        contentScale = ContentScale.Crop
    )
}
