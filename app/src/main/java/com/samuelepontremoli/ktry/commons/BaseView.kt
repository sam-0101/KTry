package com.samuelepontremoli.ktry.commons

/**
 * Created by s.pontremoli on 10/07/2017.
 */
interface BaseView<T: BasePresenter> {

    fun setPresenter(presenter: T)

}