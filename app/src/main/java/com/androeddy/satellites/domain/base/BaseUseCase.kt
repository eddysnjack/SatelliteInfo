package com.androeddy.satellites.domain.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseUseCase<Req : Any, Res : Any> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    internal abstract fun buildUseCaseSingle(req: Req): Single<Res>
    internal abstract fun onFinished(): Unit
    internal abstract fun onStart(): Unit

    fun execute(
        params: Req,
        onSuccess: ((res: Res) -> Unit),
        onError: ((throwable: Throwable) -> Unit),
    ) {
        val disposable = buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doAfterTerminate(cleanUp())
            .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
    }

    private fun cleanUp(): Action {
        compositeDisposable.clear()
        return Action { onFinished() }
    }
}