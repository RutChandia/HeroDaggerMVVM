package cl.noemi.herodaggerhilt.data.network

import android.util.Log
import cl.noemi.herodaggerhilt.R
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException


suspend fun <T : Any> doNetworkCall(
    call: suspend () -> T
): ApiResponseState<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseState.Success(call())
        } catch (e: HttpException) {
            try {
                val errorBody = e.response()?.errorBody()
                val jsonErrorBody = errorBody?.charStream()?.readText()?.let { JSONObject(it) }
                val defaultResponse =
                    Gson().fromJson(jsonErrorBody.toString(), DefaultResponse::class.java)
                ApiResponseState.Error(R.string.error_api, defaultResponse.status_message)
            } catch (e: Exception) {
                ApiResponseState.Error(R.string.error_api)
            }
        } catch (e: Exception) {
            Log.d("heroCall", "${e.cause?.message}")
            ApiResponseState.Error(R.string.error_connection)
        }
    }
}