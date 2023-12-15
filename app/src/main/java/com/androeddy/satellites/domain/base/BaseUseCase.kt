package com.androeddy.satellites.domain.base

import com.androeddy.satellites.util.UIActionsMessenger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseUseCase<Req : Any, Res : Any> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    internal abstract fun buildUseCaseSingle(req: Req?): Single<Res>
    protected var uiActionsMessenger: UIActionsMessenger? = null
    protected var loadingMessage: String? = null

    fun execute(
        params: Req?,
        onSuccess: ((res: Res) -> Unit),
        onError: ((throwable: Throwable) -> Unit),
        uiActionsMessenger: UIActionsMessenger? = null,
        loadingMessage: String? = null
    ) {
        this.uiActionsMessenger = uiActionsMessenger
        this.loadingMessage = loadingMessage
        val disposable = buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doAfterTerminate(cleanUp())
            .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
    }

    open fun onStart() {
        uiActionsMessenger?.showProgress(loadingMessage ?: "")
    }

    open fun onFinished() {
        uiActionsMessenger?.hideProgress()
    }

    private fun cleanUp(): Action {
        compositeDisposable.clear()
        return Action { onFinished() }
    }
}