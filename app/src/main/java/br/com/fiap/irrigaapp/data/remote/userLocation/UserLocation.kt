package br.com.fiap.irrigaapp.data.remote.userLocation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

@SuppressLint("MissingPermission") // Certifique-se de tratar permissões antes de usar
fun getUserLocation(context: Context, onLocationReceived: (latitude: Double, longitude: Double) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                Log.d("Location", "Localização recebida: Latitude ${location.latitude}, Longitude ${location.longitude}")
                onLocationReceived(location.latitude, location.longitude)
            } else {
                Log.e("Location", "Não foi possível obter a localização")
            }
        }
        .addOnFailureListener { exception ->
            Log.e("Location", "Erro ao obter a localização", exception)
        }
}

