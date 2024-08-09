package com.example.mod_http_core.interceptors

import com.example.mod_http_core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val ApiKeyHeader = "x-api-key"
private const val ApiKey = BuildConfig.API_KEY

class APIKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        with(chain) {
            return proceed(
                request()
                    .newBuilder()
                    .addHeader(ApiKeyHeader, ApiKey)
                    .build()
            )
        }
    }
}