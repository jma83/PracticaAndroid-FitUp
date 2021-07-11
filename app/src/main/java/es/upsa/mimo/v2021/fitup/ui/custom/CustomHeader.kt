package es.upsa.mimo.v2021.fitup.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import es.upsa.mimo.v2021.fitup.R

class CustomHeader: LinearLayout{
    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        LayoutInflater.from(context).inflate(R.layout.custom_header, this, false)
    }
}