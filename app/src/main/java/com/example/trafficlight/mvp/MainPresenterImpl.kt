package com.example.trafficlight.mvp

import android.util.Log
import androidx.annotation.DrawableRes
import com.example.trafficlight.R
import com.example.trafficlight.base.ItemType
import com.example.trafficlight.extensions.addTo
import com.example.trafficlight.model.Light
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainPresenterImpl(private val view: MainView) : MainPresenter {

    companion object {
        const val TAG = "MainPresenterImpl"
        const val FIRST_INDEX = 0
        const val TICK_PERIOD_IN_MILLIS = 500L
        const val TICKS_BEFORE_BLINKING = 6
        const val TICKS_BEFORE_SWITCHING_LIGHTS = 10L
        const val STATE_ON = "STATE_ON"
        const val STATE_OFF = "STATE_OFF"
        const val RED_COLOR = "Red"
        const val YELLOW_COLOR = "Yellow"
        const val GREEN_COLOR = "Green"
    }

    private var compositeDisposable = CompositeDisposable()
    private var index: Int = FIRST_INDEX
    private var lightsList: List<Light> = listOf(
        Light(R.drawable.img_red_light, true),
        Light(R.drawable.img_yellow_light),
        Light(R.drawable.img_green_light)
    )

    override fun onCreateView() {
        if (compositeDisposable.isDisposed) compositeDisposable = CompositeDisposable()

        view.init()
        view.setLightsData(lightsList)
        manageTrafficLight()
    }

    override fun onDestroyView() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }

    private fun manageTrafficLight() {
        Observable.interval(TICK_PERIOD_IN_MILLIS, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { if (it >= TICKS_BEFORE_BLINKING) changeLightState() }
            .takeUntil { it == TICKS_BEFORE_SWITCHING_LIGHTS }
            .doOnComplete { updateList() }
            .repeat()
            .subscribe(
                { Log.d(TAG, it.toString()) },
                { Log.d(TAG, it.toString()) }
            ).addTo(compositeDisposable)
    }

    private fun changeLightState() {
        lightsList[index].isOn = !lightsList[index].isOn
        view.setLightsData(lightsList)
    }

    private fun updateList() {
        offAllLights()
        lightsList[getIndex()].isOn = true
        view.setLightsData(lightsList)
    }

    private fun getIndex(): Int {
        index = if (--index < FIRST_INDEX) lightsList.lastIndex else index
        return index
    }

    private fun offAllLights() {
        lightsList.forEach { it.isOn = false }
    }

    override fun onItemClicked(item: ItemType) {
        val info = when (item) {
            is Light -> {
                val color = getColorName(item.image)
                val state = if (item.isOn) STATE_ON else STATE_OFF
                "$color light: $state"
            }
            else -> "Unknown item!"
        }

        view.showItemInfo(info)
    }

    private fun getColorName(@DrawableRes image: Int): String =
        when (image) {
            R.drawable.img_red_light -> RED_COLOR
            R.drawable.img_yellow_light -> YELLOW_COLOR
            R.drawable.img_green_light -> GREEN_COLOR
            else -> throw Exception("Unknown image!")
        }
}