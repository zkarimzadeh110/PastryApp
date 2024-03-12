package com.example.pastry.ui.contents

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pastry.ui.components.CheckAndRequestPermission
import com.example.pastry.ui.components.MediaPicker
import com.example.pastry.ui.components.ProfilePhotoElement
import com.example.pastry.ui.viewModel.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(modifier: Modifier = Modifier,viewModel: AccountViewModel = hiltViewModel()){
    val context= LocalContext.current
    var name by rememberSaveable{ mutableStateOf(viewModel.uiState.name) }
    var phone by rememberSaveable { mutableStateOf( viewModel.uiState.phone) }
    var bio by rememberSaveable { mutableStateOf( viewModel.uiState.bio) }
    var address by rememberSaveable { mutableStateOf( viewModel.uiState.address) }
    var website by rememberSaveable { mutableStateOf( viewModel.uiState.site) }
    var photoPath by rememberSaveable { mutableStateOf("") }
     var requestGranted by rememberSaveable { mutableStateOf(false) }
    var requestPermission by rememberSaveable { mutableStateOf(false) }
    if(viewModel.uiState.name!=""){
        name=viewModel.uiState.name
    }
    if(viewModel.uiState.phone!=""){
        phone=viewModel.uiState.phone
    }
    if(viewModel.uiState.bio!=""){
        bio=viewModel.uiState.bio
    }
    if(viewModel.uiState.address!=""){
        address=viewModel.uiState.address
    }
    if(viewModel.uiState.site!=""){
        website=viewModel.uiState.site
    }
    if(requestPermission){
        CheckAndRequestPermission(context){
            requestPermission=false
            requestGranted=true
        }

    }
    if(requestGranted){
        MediaPicker(context,type = "photo", getPath ={path->
            requestGranted=false
            photoPath=path
            })

    }
    if(photoPath!=""){
        viewModel.updateUserProfilePhoto(photoPath)
        photoPath=""
    }
    Box(modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
            ) {
                    ProfilePhotoElement(
                        modifier = modifier.size(100.dp),
                        photoPath = viewModel.uiState.profilePhoto
                    )

                FloatingActionButton(modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomEnd),
                    onClick = {
                        requestPermission = true
                    }
                ) {
                    Icon(Icons.Filled.Add, "Add profile photo")
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
            OutlinedTextField(
                value =viewModel.uiState.name,
                onValueChange = { viewModel.setName(it) },
                label = {
                    Text("Name")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.uiState.phone,
                onValueChange = { viewModel.setPhone(it) },
                label = {
                    Text("Phone")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(
                        // start = 24.dp, end = 24.dp, bottom = 16.dp
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.uiState.bio,
                onValueChange = { viewModel.setBio(it) },
                label = {
                    Text("Bio")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(
                        //start = 24.dp, end = 24.dp, bottom = 16.dp
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value =  viewModel.uiState.address,
                onValueChange = { viewModel.setAddress(it) },
                label = {
                    Text("Address")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(
                        //start = 24.dp, end = 24.dp, bottom = 16.dp
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value =  viewModel.uiState.site,
                onValueChange = { viewModel.setWebsite(it) },
                label = {
                    Text("Website")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(
                        //start = 24.dp, end = 24.dp, bottom = 16.dp
                    )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
                shape = MaterialTheme.shapes.large,
                onClick = {
                    viewModel.updateUserAccountInfo(
                        name,
                        bio,
                        phone,
                        website,
                        address
                    )
                }) { Text("Confirm") }
        }
        if (viewModel.uiState.networkRequestInfo.loading) {
            CircularProgressIndicator(0.75f,Modifier.padding(24.dp).wrapContentSize().align(Alignment.Center))
        }
    }
}