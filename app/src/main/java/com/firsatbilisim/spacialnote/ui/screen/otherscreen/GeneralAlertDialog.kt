package com.firsatbilisim.spacialnote.ui.screen.otherscreen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun GeneralAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Silme Onayı") },
            text = { Text("Bu öğeyi silmek istediğinizden emin misiniz?") },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Hayır")
                }
            }
        )
    }
}

