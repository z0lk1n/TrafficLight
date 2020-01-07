package com.example.trafficlight.mvp

import android.util.Log
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
    }

    private var compositeDisposable = CompositeDisposable()
    private var index: Int = FIRST_INDEX
    private var lightsList: List<Light> = listOf(
        Light(Light.Color.RED, true),
        Light(Light.Color.YELLOW),
        Light(Light.Color.GREEN)
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
                val color = item.typeColor
                val state = if (item.isOn) STATE_ON else STATE_OFF
                "$color light: $state"
            }
            else -> "Unknown item!"
        }

        view.showItemInfo(info)
    }
}