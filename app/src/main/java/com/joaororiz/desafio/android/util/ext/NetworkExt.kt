package com.joaororiz.desafio.android.util.ext


import com.google.gson.Gson
import com.joaororiz.desafio.android.data.entities.DataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.net.ConnectException


suspend fun <T> Call<DataWrapper<T>>.backgroundCall(dispatcher: CoroutineDispatcher): DataWrapper<T?> {
    return withContext(context = dispatcher) {
        try {
            val response = this@backgroundCall.execute()
            if (response.isSuccessful) {
                DataWrapper.success(response.body()?.data, response.code())
            } else {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), ErrorDefault::class.java)
                DataWrapper.error(error.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is ConnectException || e is java.net.UnknownHostException) {
                DataWrapper.error<T?>("Seu dispositivo está sem internet.")
            } else {
                DataWrapper.error(e.message)
            }
        }
    }
}

data class ErrorDefault(val message: String)

