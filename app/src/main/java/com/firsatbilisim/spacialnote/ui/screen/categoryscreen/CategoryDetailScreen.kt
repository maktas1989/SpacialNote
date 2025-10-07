package com.firsatbilisim.spacialnote.ui.screen.categoryscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.firsatbilisim.spacialnote.R
import com.firsatbilisim.spacialnote.StatusBar
import com.firsatbilisim.spacialnote.data.model.CategoryModel
import com.firsatbilisim.spacialnote.data.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryDetailScreen(categoryId: Int, navController: NavController, viewModel: CategoryViewModel = viewModel()) {

    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current // Context almak için

    // categoryDetail'deki veri değiştikçe UI'yi güncellemek için collectAsState kullanıyoruz.
    val category by viewModel.selectedCategory.collectAsState() // Burayı ben CategoryViewModel den çekiyorum.
    // Kategori detayını yükle
    LaunchedEffect(categoryId) {
        viewModel.loadCategoryDetail(categoryId)
    }

    var categoryName by remember { mutableStateOf("") }
    LaunchedEffect(category) {
        category?.let {
            categoryName = it.title
        }
    }

    StatusBar()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Kategori Güncelle") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.status_bar),
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = colorResource(R.color.top_bar))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 15.dp, end = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Burası kategoriye ait bilgiler içermektedir.",
                                color = colorResource(R.color.white),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        }
                        HorizontalDivider(
                            color = Color.White, // Çizgi rengi
                            thickness = 1.dp,    // Çizgi kalınlığı
                            modifier = Modifier.padding(vertical = 8.dp) // Çizgiye boşluk eklemek için
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, start = 15.dp, end = 5.dp)
                                .clickable {
                                    navController.navigate("note_main_screen/${categoryId}")
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Kategoriye Ait Notlar", color = Color.White)
                        }
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                    value = categoryName,
                    onValueChange = { categoryName = it},
                    placeholder = { Text(text = "Lütfen başlığı yazınız.") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.top_bar),
                        unfocusedContainerColor = colorResource(id = R.color.top_bar),
                        focusedPlaceholderColor = colorResource(id = R.color.white),
                        unfocusedPlaceholderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
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
                            Toast.makeText(context, "Başlık boş bırakılamaz!", Toast.LENGTH_LONG).show()
                        } else {
                            category?.let {
                                val updatedCategory = it.copy(title = categoryName) // `title` alanını güncelliyoruz
                                viewModel.updateCategory(updatedCategory)
                            }
                            localFocusManager.clearFocus()

                            navController.popBackStack("category_main_screen", false)
                            Toast.makeText(context, "Başarıyla Gücellendi!", Toast.LENGTH_LONG).show()
                        }
                    }
                ) {
                    Text(text = "Güncelle")
                }
            }
        }
    )
}

