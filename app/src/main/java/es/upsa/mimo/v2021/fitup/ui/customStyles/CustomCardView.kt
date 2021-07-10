package es.upsa.mimo.v2021.fitup.ui.customStyles

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import es.upsa.mimo.v2021.fitup.R

class CustomCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
) : CardView(
    context,
    attrs,
    defStyleAttr
) {
    init {
        LayoutInflater.from(context).inflate(R.layout.custom_card_view, this, true)
    }
}