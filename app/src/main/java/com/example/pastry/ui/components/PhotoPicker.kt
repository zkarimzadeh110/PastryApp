package com.example.pastry.ui.components

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.example.pastry.utils.getPath

@Composable
fun MediaPicker(context: Context, type: String, getPath: (String) -> Unit) {
    if(ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()){
       GetMediaFromPhotoPicker(context,type,getPath)
    }else{
        GetMediaContent(context,type,getPath)
    }
}
@Composable
fun GetMediaFromPhotoPicker(context: Context,type:String, getPath: (String) -> Unit) {
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val path= getPath(context,uri)
            if(path!=null){
                getPath(path)
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    SideEffect {
        if (type == "photo") {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else if (type == "video") {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }
    }
}
@Composable
fun GetMediaContent(context: Context,type:String, getPath: (String) -> Unit) {
        val getContent = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val uri=it.data?.data
            if (uri != null) {
                val path= getPath(context,uri)
                if(path!=null){
                    getPath(path)
                }
            }
        }
        SideEffect {
            if(type=="photo"){
                getContent.launch( Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
            }else if(type=="video"){
                getContent.launch( Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI))
            }
        }


}