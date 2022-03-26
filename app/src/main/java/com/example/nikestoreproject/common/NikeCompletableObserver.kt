package com.example.nikestoreproject.common

import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class NikeCompletableObserver(val compositeDisposable: CompositeDisposable) :
    CompletableObserver {
    override fun onError(e: Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(e))                                      /*ba inkar b vasile ye EventBus miaym error i ro k handle kardim ro b --> View <-- miresoonim .*/
        Timber.e(e)
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }
}