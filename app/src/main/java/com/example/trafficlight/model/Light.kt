package com.example.trafficlight.model

import androidx.annotation.DrawableRes
import com.example.trafficlight.R
import com.example.trafficlight.base.ItemType

data class Light(
    val typeColor: Color,
    var isOn: Boolean = false
) : ItemType {

    companion object {
        const val LIGHT_TYPE = 1
        const val LIGHT_ID = "Light"
    }

    enum class Color(@DrawableRes val res: Int) {
        RED(R.drawable.img_red_light),
        YELLOW(R.drawable.img_yellow_light),
        GREEN(R.drawable.img_green_light)
    }

    override fun getItemId(): String = LIGHT_ID + typeColor

    override fun getItemType(): Int = LIGHT_TYPE

    override fun compareItem(old: ItemType): Boolean = if (old is Light) isOn == old.isOn else false
}