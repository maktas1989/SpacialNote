package com.firsatbilisim.spacialnote.ui.screen.notescreen

import DateFormatter
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
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
import com.firsatbilisim.spacialnote.data.model.NoteModel
import com.firsatbilisim.spacialnote.data.viewmodel.NoteViewModel
import com.firsatbilisim.spacialnote.ui.screen.otherscreen.AdMobBanner
import com.firsatbilisim.spacialnote.ui.screen.otherscreen.GeneralAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteMainScreen(categoryId: Int, navController: NavController, viewModel: NoteViewModel = viewModel()) {
    var selectedDeleteNote by remember { mutableStateOf<NoteModel?>(null) }
    val isSearching = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val notesList by viewModel.noteList.collectAsState()
    val context = LocalContext.current // Burayı toast ve alert dialog'ta mesajları için mecburi olarak tanımlıyorum.
    val showDialog by viewModel.showDialog

    // categoryId'yi kullanarak loadNotes fonksiyonunu çağırıyoruz
    LaunchedEffect(Unit) {
        viewModel.loadNotes(categoryId)
    }

    StatusBar()

    GeneralAlertDialog(
        showDialog = showDialog,
        onDismiss = { viewModel.hideDeleteDialog() },
        onConfirm = {
            selectedDeleteNote?.let { note ->
                viewModel.deleteNote(note) // Burada note.id'yi geçiriyoruz
                Toast.makeText(context, "Not başarıyla silindi!", Toast.LENGTH_SHORT).show()
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
                                viewModel.searchNotesByTitle(it, categoryId)
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
                        Text("Not Listeleri")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.top_bar),
                    titleContentColor = Color.White
                ),
                actions = {
                    if (!isSearching.value) {
                        IconButton(
                            onClick = { isSearching.value = true },
                            content = {
                                Icon(painter = painterResource(R.drawable.search), contentDescription = "Ara", tint = Color.White)
                            }
                        )
                    } else {
                        IconButton(
                            onClick = {
                                isSearching.value = false
                                searchQuery.value = "" // Arama metnini sıfırla
                                viewModel.loadNotes(categoryId)
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(notesList) { note ->
                        Card(
                            modifier = Modifier
                                .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    colorResource(R.color.status_bar),
                                    RoundedCornerShape(10.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                contentColor = Color.White,
                                containerColor = colorResource(R.color.top_bar),
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 5.dp),
                            ) {
                                Text(
                                    text = "Ekleme Tarihi : ${
                                        DateFormatter.formatDateToTurkishFormat(
                                            note.createdAt
                                        )
                                    }",
                                    modifier = Modifier
                                        .padding(bottom = 5.dp)
                                )
                                note.updatedAt?.let {
                                    Text(
                                        text = "Düzenleme Tarihi : ${
                                            DateFormatter.formatDateToTurkishFormat(
                                                note.updatedAt!!
                                            )
                                        }"
                                    )
                                }
                                HorizontalDivider(
                                    color = Color.White, // Çizgi rengi
                                    thickness = 1.dp,    // Çizgi kalınlığı
                                    modifier = Modifier
                                        .padding(vertical = 8.dp) // Çizgiye boşluk eklemek için
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        // Burası notun detayına gidecek
                                        navController.navigate("note_detail_screen/${note.id}")
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
                                        text = " -> Not Adı : ${note.title}"
                                    )
                                    IconButton(
                                        onClick = {
                                            // Burada silme olacak
                                            viewModel.showDeleteDialog()
                                            selectedDeleteNote = note


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
                onClick = { navController.navigate("add_note_screen/${categoryId}") },
                containerColor = colorResource(R.color.fab_button),
                content = {
                    Icon(painter = painterResource(R.drawable.add), contentDescription = "Yeni Kayıt Ekle", tint = Color.White)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}

