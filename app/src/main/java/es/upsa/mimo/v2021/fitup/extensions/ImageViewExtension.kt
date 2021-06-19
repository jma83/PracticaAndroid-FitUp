package es.upsa.mimo.v2021.fitup.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.fromUrl(url: String) {
    Glide.with(context)
            .load(url)
            .into(this)
}