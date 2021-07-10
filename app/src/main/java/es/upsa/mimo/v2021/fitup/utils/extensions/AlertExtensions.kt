package es.upsa.mimo.v2021.fitup.utils.extensions

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun Context.showAlert(title: String, message: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("Accept", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}