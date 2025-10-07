package com.firsatbilisim.spacialnote.ui.screen.categoryscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.firsatbilisim.spacialnote.R
import com.firsatbilisim.spacialnote.StatusBar
import com.firsatbilisim.spacialnote.data.model.CategoryModel
import com.firsatbilisim.spacialnote.data.viewmodel.CategoryViewModel
import com.firsatbilisim.spacialnote.ui.screen.otherscreen.AdMobBanner
import com.firsatbilisim.spacialnote.ui.screen.otherscreen.GeneralAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ContextCastToActivity")
@Composable
fun CategoryMainScreen(viewModel: CategoryViewModel = viewModel(), navController: NavController) {
    var selectedDeleteCategory by remember { mutableStateOf<CategoryModel?>(null) }
    val isSearching = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val categories by viewModel.categoryList.collectAsState()
    val context = LocalContext.current
    val showDialog by viewModel.showDialog

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    StatusBar()

    GeneralAlertDialog(
        showDialog = showDialog,
        onDismiss = { viewModel.hideDeleteDialog() },
        onConfirm = {
            selectedDeleteCategory?.let { category ->
                viewModel.deleteCategory(category)
                Toast.makeText(context, "Kategori başarıyla silindi!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        containerColor = colorResource(R.color.background),
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching.value) {
                        TextField(
                            value = searchQuery.value,
                            onValueChange = {
                                searchQuery.value = it
                                viewModel.searchCategoryByTitle(it)
                            },
                            placeholder = {
                                Text(text = "Arama...")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedPlaceholderColor = Color.White,
                                unfocusedPlaceholderColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White
                            )
                        )
                    } else {
                        Text("Kategori Listeleri")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.top_bar),
                    titleContentColor = Color.White
                ),
                actions = {
                    if (!isSearching.value) {
                        IconButton(
                            onClick = {
                                isSearching.value = true
                            },
                            content = {
                                Icon(painter = painterResource(R.drawable.search), contentDescription = "Ara", tint = Color.White)
                            }
                        )
                    } else {
                        IconButton(
                            onClick = {
                                isSearching.value = false
                                searchQuery.value = "" // Arama metnini sıfırla
                                viewModel.loadCategories()
                            },
                            content = {
                                Icon(painter = painterResource(R.drawable.close), contentDescription = "Aramayı Kapat", tint = Color.White)
                            }
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                // Reklam banner'ını buraya ekleyin
                AdMobBanner(
                    modifier = Modifier
                        .fillMaxWidth(),
                    adId = "ca-app-pub-3952893430741480/8645597617"
                )
                // Kategorilerin listesi
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(categories) { category ->
                        Card(
                            modifier = Modifier
                                .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                                .fillMaxWidth()
                                .border(1.dp, colorResource(R.color.status_bar), RoundedCornerShape(10.dp)),
                            colors = CardDefaults.cardColors(
                                contentColor = Color.White,
                                containerColor = colorResource(R.color.top_bar),
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        // Burası kategorinin detayına gidecek
                                        navController.navigate("category_detail_screen/${category.id}")
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = " -> Kategori Adı : ${category.title}",
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
                                    )
                                    IconButton(
                                        onClick = {
                                            // Burada silme olacak
                                            viewModel.showDeleteDialog()
                                            selectedDeleteCategory = category
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.delete),
                                            contentDescription = "Kategoriyi sil",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_category_screen") },
                containerColor = colorResource(R.color.fab_button),
                content = {
                    Icon(painter = painterResource(R.drawable.add), contentDescription = "Yeni Kayıt Ekle", tint = Color.White)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}