package cl.noemi.herodaggerhilt.data.network

sealed class ApiResponseState<T> {
    data class Success<T>(val data: T) : ApiResponseState<T>()
    class Loading<T> : ApiResponseState<T>()
    data class Error<T>(val messageId: Int, val statusMessage: String = "") : ApiResponseState<T>()
}