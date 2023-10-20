package icu.sincos.ZhiWeiXiaoYuan_Extra.util


import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class MyCookieJar : CookieJar{




    private var sessionCookie : Cookie? = null
    private val cookies: MutableList<Cookie> = ArrayList()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        for (cookie in cookies){
            if (cookie.name == "SESSION" ){
                sessionCookie = cookie
                break
            }
        }
        Log.i("CookieJarUtils", "savefromresponse :$sessionCookie")

    }

    override fun loadForRequest(url: HttpUrl): List<Cookie>  {
        val validCookies: MutableList<Cookie> = ArrayList()
        val currentTimeMillis = System.currentTimeMillis()
        if (sessionCookie != null && sessionCookie!!.expiresAt > currentTimeMillis) {
            validCookies.add(sessionCookie!!)
        }
        Log.i("CookieJarUtils", "loadForRequest :$validCookies$url")
        return validCookies
    }

}