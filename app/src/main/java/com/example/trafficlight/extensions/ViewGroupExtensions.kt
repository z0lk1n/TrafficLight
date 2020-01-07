package com.example.trafficlight.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(
    @LayoutRes id: Int,
    inflater: LayoutInflater = LayoutInflater.from(context),
    attachToRoot: Boolean = false
): View = inflater.inflate(id, this, attachToRoot)