package br.com.fiap.irrigaapp.comunication.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast

fun connectToWiFi(context: Context, ssid: String, password: String) {
    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    // Configura o Wi-Fi com SSID e senha
    val wifiConfig = WifiConfiguration().apply {
        SSID = "\"$ssid\""
        preSharedKey = "\"$password\""
    }

    // Adiciona a rede
    val netId = wifiManager.addNetwork(wifiConfig)
    Log.d("WiFiConnection", "Tentando adicionar a rede com netId: $netId")

    if (netId == -1) {
        Log.e("WiFiConnection", "Falha ao adicionar a rede. Verifique o SSID e senha.")
        Toast.makeText(context, "Falha ao adicionar a rede. Verifique o SSID e senha.", Toast.LENGTH_SHORT).show()
        return
    }

    // Desconecta de qualquer rede atual e tenta se conectar à nova rede
    val disconnected = wifiManager.disconnect()
    Log.d("WiFiConnection", "Tentativa de desconexão da rede atual: $disconnected")

    val enabled = wifiManager.enableNetwork(netId, true)
    Log.d("WiFiConnection", "Tentativa de habilitar a rede: $enabled")

    val reconnected = wifiManager.reconnect()
    Log.d("WiFiConnection", "Tentativa de reconexão: $reconnected")

    Toast.makeText(context, "Tentando conectar à rede $ssid...", Toast.LENGTH_SHORT).show()

    // Registrar o BroadcastReceiver para monitorar o status da conexão
    val intentFilter = IntentFilter().apply {
        addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        addAction(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

            Log.d("WiFiConnection", "Recebendo estado da rede: $networkInfo")

            if (networkInfo?.type == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected) {
                Log.d("WiFiConnection", "Conectado à rede $ssid!")
                Toast.makeText(context, "Conectado à rede $ssid!", Toast.LENGTH_SHORT).show()
                context.unregisterReceiver(this)
            } else if (networkInfo != null && !networkInfo.isConnected) {
                Log.d("WiFiConnection", "Falha na conexão à rede $ssid")
                Toast.makeText(context, "Falha na conexão à rede $ssid", Toast.LENGTH_SHORT).show()
                context.unregisterReceiver(this)
            }
        }
    }

    context.registerReceiver(receiver, intentFilter)
}
