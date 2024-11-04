package br.com.fiap.irrigaapp

import android.app.Application
import br.com.fiap.irrigaapp.data.remote.api.TokenProvider

class MyApplication : Application() {
    // Variável global para o TokenProvider
    lateinit var tokenProvider: TokenProvider

    override fun onCreate() {
        super.onCreate()
        // Inicialize o TokenProvider com applicationContext
        tokenProvider = TokenProvider(applicationContext)
    }
}
