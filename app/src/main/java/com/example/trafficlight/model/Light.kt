package com.example.trafficlight.model

import androidx.annotation.DrawableRes
import com.example.trafficlight.R
import com.example.trafficlight.base.ItemType

data class Light(
    val id: Int,
    val typeColor: Color,
    var isOn: Boolean = false
) : ItemType {

    companion object {
        const val LIGHT_TYPE = 1
    }

    enum class Color(
        @DrawableRes val res: Int,
        val ticksBeforeBlinking: Int,
        val ticksBeforeSwitching: Long
    ) {
        RED(R.drawable.img_red_light, 6, 10L),
        YELLOW(R.drawable.img_yellow_light, 6, 10L),
        GREEN(R.drawable.img_green_light, 6, 10L)
    }

    override fun getItemId(): String = "$typeColor-$id"

    override fun getItemType(): Int = LIGHT_TYPE

    override fun compareItem(old: ItemType): Boolean =
        if (old is Light) id == old.id && typeColor == old.typeColor && isOn == old.isOn else false
}