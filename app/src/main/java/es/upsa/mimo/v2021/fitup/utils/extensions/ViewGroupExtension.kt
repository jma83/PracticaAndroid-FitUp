package es.upsa.mimo.v2021.fitup.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
