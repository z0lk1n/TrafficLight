package com.example.trafficlight.adapters

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.trafficlight.R
import com.example.trafficlight.base.BaseHolder
import com.example.trafficlight.base.DelegateAdapter
import com.example.trafficlight.base.ItemType
import com.example.trafficlight.extensions.inflate
import com.example.trafficlight.model.Light

class LightDelegateAdapter(private val itemLayout: Int) : DelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): BaseHolder = LightHolder(parent, itemLayout)

    open class LightHolder(
        parent: ViewGroup,
        itemLayout: Int
    ) : BaseHolder(parent.inflate(itemLayout)) {

        private lateinit var ivLight: ImageView

        override fun bind(item: ItemType) = with(itemView) {
            item as Light

            ivLight = findViewById(R.id.iv_item_rv_light)
            ivLight.setImageDrawable(ContextCompat.getDrawable(context, getImage(item)))

            super.bind(item)
        }

        private fun getImage(item: Light): Int =
            if (item.isOn) item.typeColor.res else R.drawable.img_gray_stub
    }
}