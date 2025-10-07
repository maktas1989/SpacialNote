package com.firsatbilisim.spacialnote.ui.screen.categoryscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.firsatbilisim.spacialnote.R
import com.firsatbilisim.spacialnote.StatusBar
import com.firsatbilisim.spacialnote.data.model.CategoryModel
import com.firsatbilisim.spacialnote.data.viewmodel.CategoryViewModel
import com.firsatbilisim.spacialnote.ui.theme.SpacialNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddCategoryScreen(viewModel: CategoryViewModel = viewModel(), navController: NavController) {

    var categoryName by remember { mutableStateOf("") } // Kullanıcının girdiği metin
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current // Context almak için özellikle Toast mesajlarına lazım oluyor


    StatusBar()
    Scaffold(
        containerColor = colorResource(R.color.background),
        topBar = {
            TopAppBar(
                title = { Text(text = "Kategori Ekle") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.top_bar),
                    titleContentColor = Color.White
                )
            )
        },
        content =  { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    placeholder = { Text(text = "Lütfen başlığı yazınız.") },
                    modifier = Modifier
                        .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.top_bar),
                        unfocusedContainerColor = colorResource(id = R.color.top_bar),
                        focusedPlaceholderColor = colorResource(id = R.color.white),
                        unfocusedPlaceholderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                Button(
                    modifier = Modifier
                        .padding(30.dp)
                        .size(250.dp, 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.fab_button),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        if (categoryName.isEmpty()) {
                            Toast.makeText(context, "Başlık boş bırakılamaz!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.addCategory(CategoryModel(title = categoryName))
                            localFocusManager.clearFocus()

                            navController.popBackStack("category_main_screen", false)
                            Toast.makeText(context, "Başarıyla kaydedildi!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(text = "Kaydet")
                }
            }
        }
    )
}

@Composable
@Preview
fun AddCategoryPreview() {
    SpacialNoteTheme {

    }
}