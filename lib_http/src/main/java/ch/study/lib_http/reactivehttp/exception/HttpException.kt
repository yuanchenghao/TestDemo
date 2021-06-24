package ch.study.lib_http.reactivehttp.exception

import ch.study.lib_http.reactivehttp.bean.IHttpWrapBean

open class BaseHttpException(
    val errorCode: Int,
    val errorMessage: String,
    val realException: Throwable?
) : Exception(errorMessage) {

    companion object {

        /**
         * 此变量用于表示在网络请求过程过程中抛出了异常
         */
        const val CODE_ERROR_LOCAL_UNKNOWN = -1024520

    }

    /**
     * 是否是由于服务器返回的 code != successCode 导致的异常
     */
    val isServerCodeBadException: Boolean
        get() = this is ServerCodeBadException

    /**
     * 是否是由于网络请求过程中抛出的异常（例如：服务器返回的 Json 解析失败）
     */
    val isLocalBadException: Boolean
        get() = this is LocalBadException

}

/**
 * API 请求成功了，但 code != successCode
 * @param errorCode
 * @param errorMessage
 */
class ServerCodeBadException(errorCode: Int, errorMessage: String) :
    BaseHttpException(errorCode, errorMessage, null) {

    constructor(bean: IHttpWrapBean<*>) : this(bean.httpCode, bean.httpMsg)

}

/**
 * 请求过程抛出异常
 * @param throwable
 */
class LocalBadException(throwable: Throwable) : BaseHttpException(
    CODE_ERROR_LOCAL_UNKNOWN, throwable.message
        ?: "", throwable
)