package com.test.testapp.network

object Common {

    private val BASE_URL = "https://dev.bgrem.deelvin.com/"

    val api: Api
        get() = RetrofitClient.getClient(BASE_URL).create(Api::class.java)
}