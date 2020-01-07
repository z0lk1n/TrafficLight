package com.example.trafficlight.model

import androidx.annotation.DrawableRes
import com.example.trafficlight.base.ItemType

data class Light(
    @DrawableRes val image: Int,
    var isOn: Boolean = false
) : ItemType {

    companion object {
        const val LIGHT_TYPE = 1
        const val LIGHT_ID = "Light"
    }

    override fun getItemId(): String = LIGHT_ID

    override fun getItemType(): Int = LIGHT_TYPE

    override fun compareItem(old: ItemType): Boolean = if (old is Light) isOn == old.isOn else false
}