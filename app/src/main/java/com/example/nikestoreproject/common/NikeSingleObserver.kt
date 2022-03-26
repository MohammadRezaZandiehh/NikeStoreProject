package com.example.nikestoreproject.common

import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class NikeSingleObserver<T>(val compositeDisposable: CompositeDisposable) :
    SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(e))                                      /*ba inkar b vasile ye EventBus miaym error i ro k handle kardim ro b --> View <-- miresoonim .*/
        Timber.e(e)
    }
}