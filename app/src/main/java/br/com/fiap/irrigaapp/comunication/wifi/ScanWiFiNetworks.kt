package br.com.fiap.irrigaapp.comunication.wifi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat

// Função para escanear redes Wi-Fi disponíveis
fun scanWiFiNetworks(context: Context, onScanCompleted: (List<String>) -> Unit) {
    // Verificar se a permissão foi concedida
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {

        try {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

            // Verificar se o scan foi iniciado com sucesso
            if (wifiManager.startScan()) {
                val results: List<ScanResult> = wifiManager.scanResults
                val wifiList = results.map { it.SSID }.filter { it.isNotEmpty() }
                onScanCompleted(wifiList)
            } else {
                // Se o scan não for iniciado
                onScanCompleted(emptyList())
                Toast.makeText(context, "Falha ao iniciar o escaneamento Wi-Fi", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            // Tratar possíveis exceções de segurança
            Log.e("WiFiScan", "Erro de permissão ao escanear Wi-Fi: ${e.message}")
            Toast.makeText(context, "Erro de permissão ao escanear redes Wi-Fi", Toast.LENGTH_SHORT).show()
        }
    } else {
        // Se a permissão não for concedida
        Toast.makeText(context, "Permissão necessária para escanear redes Wi-Fi", Toast.LENGTH_SHORT).show()
    }
}
