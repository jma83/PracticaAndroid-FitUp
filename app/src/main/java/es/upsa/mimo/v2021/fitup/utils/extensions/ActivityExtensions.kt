package es.upsa.mimo.v2021.fitup.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf

inline fun <reified T : Activity> Context.startActivity1(vararg pairs: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundleOf(*pairs))
    startActivity(intent)
}