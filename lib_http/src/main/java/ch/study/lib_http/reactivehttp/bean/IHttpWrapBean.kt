package ch.study.lib_http.reactivehttp.bean

interface IHttpWrapBean<Data> {

    /**
     * 服务器返回的数据中，用来标识当前是否请求成功的标识符
     */
    val httpCode: Int

    /**
     * 服务器返回的数据中，用来标识当前请求状态的字符串，一般是用于保存失败原因
     */
    val httpMsg: String

    /**
     * 服务器返回的实际数据
     */
    val httpData: Data

    /**
     * 交由外部来判断当前接口是否请求成功
     */
    val httpIsSuccess: Boolean

    val httpIsFailed: Boolean
        get() = !httpIsSuccess

}