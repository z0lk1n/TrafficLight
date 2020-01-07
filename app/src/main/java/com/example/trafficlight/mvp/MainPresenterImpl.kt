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
        const val TICK_PERIOD_IN_MILLIS = 500L
    }

    private var compositeDisposable = CompositeDisposable()
    private var light: Light? = null
    private var lightsList: ArrayList<Light> = arrayListOf(
        Light(0, Light.Color.RED, true),
        Light(1, Light.Color.YELLOW),
        Light(2, Light.Color.GREEN)
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
        light = lightsList.firstOrNull()

        Observable.interval(TICK_PERIOD_IN_MILLIS, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { if (it >= light?.typeColor?.ticksBeforeBlinking ?: 0) changeLightState() }
            .takeUntil { it == light?.typeColor?.ticksBeforeSwitching ?: 0 }
            .doOnComplete { switchLight() }
            .repeat()
            .subscribe(
                { Log.d(TAG, it.toString()) },
                { Log.d(TAG, it.toString()) }
            ).addTo(compositeDisposable)
    }

    private fun changeLightState() {
        val index = light?.id ?: 0
        update(index, !lightsList[index].isOn)
    }

    private fun switchLight() {
        offAllLights()
        getNextLight()
        val index = light?.id ?: 0
        update(index, true)
    }

    private fun update(index: Int, isOn: Boolean) {
        lightsList[index] = lightsList[index].copy(isOn = isOn)
        view.setLightsData(lightsList)
    }

    private fun getNextLight() {
        val i = (light?.id ?: 0) - 1
        val index = if (i < 0) lightsList.lastIndex else i
        light = lightsList[index]
    }

    private fun offAllLights() {
        lightsList.map { it.copy(isOn = false) }
    }

    override fun onItemClicked(item: ItemType) {
        when (item) {
            is Light -> view.showItemInfo("${item.typeColor}")
        }
    }
}