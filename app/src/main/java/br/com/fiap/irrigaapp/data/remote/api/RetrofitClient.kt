package br.com.fiap.irrigaapp.data.remote.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://irriga-app-api-dev-bphqgvh6eqc8fret.eastus-01.azurewebsites.net"

    // Criação do Retrofit com ou sem autenticação
    private fun buildRetrofit(context: Context, withAuth: Boolean = true): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (withAuth) {
            clientBuilder.addInterceptor(AuthInterceptor(TokenProvider(context)))
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(ScalarsConverterFactory.create()) // Adicionando o Scalars pois a o token retornado é um texto simples e não json
            .addConverterFactory(GsonConverterFactory.create())   // O Gson permanece para JSON
            .build()
    }

    // Serviço de autenticação que requer o token
    fun getAuthApiService(context: Context): AuthApiService {
        return buildRetrofit(context, withAuth = true).create(AuthApiService::class.java)
    }

    // Serviço de sensores que requer o token
    fun getSensorApiService(context: Context): SensorApiService {
        return buildRetrofit(context, withAuth = true).create(SensorApiService::class.java)
    }

    // Unico serviço que nao requer o token
    fun getUsuarioApiService(context: Context): UsuarioApiService {
        return buildRetrofit(context, withAuth = false).create(UsuarioApiService::class.java)
    }
}
