package com.example.trafficlight.base

import android.view.ViewGroup

interface DelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): BaseHolder
}