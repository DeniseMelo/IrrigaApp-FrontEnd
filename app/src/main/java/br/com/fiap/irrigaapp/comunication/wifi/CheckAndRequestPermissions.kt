package br.com.fiap.irrigaapp.comunication.wifi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val REQUEST_CODE_LOCATION_PERMISSION = 1

// Verifica e solicita permissões necessárias para o Wi-Fi
fun checkAndRequestPermissions(activity: Activity, onPermissionsGranted: () -> Unit) {
    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

        // Solicita permissão ao usuário
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_LOCATION_PERMISSION
        )
    } else {
        // Permissões já concedidas, pode escanear redes Wi-Fi
        onPermissionsGranted()
    }
}

