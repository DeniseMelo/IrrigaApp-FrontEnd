package br.com.fiap.irrigaapp.ui.screens

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.irrigaapp.R
import br.com.fiap.irrigaapp.data.model.DadosSensor
import br.com.fiap.irrigaapp.ui.components.FundoOpaco
import br.com.fiap.irrigaapp.viewmodel.SensoresViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

@SuppressLint("SetTextI18n")
@Composable
fun Sensores(
    menuExpandido: Boolean,
    onBackgroundClick: () -> Unit,
    viewModel: SensoresViewModel = viewModel()
) {
    val context = LocalContext.current


    val markerAtivo = R.drawable.azul
    val markerInativo = R.drawable.cinza

    val listaSensores = listOf(
        DadosSensor(
            id = "1",
            localizacao = "Localização 1",
            statusRele = "on",
            nivelUmidade = 75.0f,
            latitude = -23.55052,
            longitude = -46.633309,
            temperatura = 22.5f
        ),
        DadosSensor(
            id = "2",
            localizacao = "Localização 2",
            statusRele = "off",
            nivelUmidade = 65.0f,
            latitude = -23.54894,
            longitude = -46.638909,
            temperatura = 23.0f
        ),
        DadosSensor(
            id = "3",
            localizacao = "Localização 3",
            statusRele = "on",
            nivelUmidade = 80.0f,
            latitude = -23.54685,
            longitude = -46.629909,
            temperatura = 24.1f
        )
    )

    // Armazenar a última ViewAnnotation de cada sensor
    val viewAnnotationsMap = remember { mutableStateMapOf<String, View?>() }

    // Armazenar o ViewAnnotationManager
    var viewAnnotationManager by remember { mutableStateOf<ViewAnnotationManager?>(null) }

    // Calcular o ponto central dos sensores para centralizar o mapa
    val mediaLatitude = listaSensores.map { it.latitude }.average()
    val mediaLongitude = listaSensores.map { it.longitude }.average()
    val pontoCentral = Point.fromLngLat(mediaLongitude, mediaLatitude)

    // Definindo o nível de zoom inicial
    val zoomInicial = 12.0

    // Criar um mapa para associar cada PointAnnotation ao seu DadosSensor correspondente
    val annotationToSensorMap = remember { mutableStateMapOf<PointAnnotation, DadosSensor>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .padding(start = 64.dp)
            ) {

                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        MapView(ctx).apply {
                            val map = getMapboxMap()
                            viewAnnotationManager = this.viewAnnotationManager

                            map.setCamera(
                                com.mapbox.maps.CameraOptions.Builder()
                                    .center(pontoCentral)
                                    .zoom(zoomInicial)
                                    .build()
                            )

                            map.loadStyleUri(Style.MAPBOX_STREETS) { style ->

                                // Criar o PointAnnotationManager através do plugin de anotações
                                val annotationPlugin = this.annotations
                                val pointAnnotationManager: PointAnnotationManager = annotationPlugin.createPointAnnotationManager()

                                // Adicionar os marcadores baseados na lista de sensores
                                listaSensores.forEach { sensor ->

                                    val markerResourceId = if (sensor.statusRele == "on") {

                                        markerAtivo  //azul
                                    } else {

                                        markerInativo  //cinza
                                    }

                                    // Adicionar o ícone do marcador ao estilo do mapa
                                    val bitmap = BitmapFactory.decodeResource(context.resources, markerResourceId)
                                    style.addImage("marker_${sensor.id}", bitmap)

                                    val pointAnnotationOptions = PointAnnotationOptions()
                                        .withPoint(Point.fromLngLat(sensor.longitude, sensor.latitude))
                                        .withIconImage("marker_${sensor.id}")
                                        .withIconSize(if (sensor.statusRele == "on") 0.2 else 0.3)

                                    val pointAnnotation = pointAnnotationManager.create(pointAnnotationOptions)


                                    // Mapear a pointAnnotation ao sensor correspondente
                                    annotationToSensorMap[pointAnnotation] = sensor
                                }

                                // Adicionar um único listener de clique ao PointAnnotationManager
                                pointAnnotationManager.addClickListener { clickedAnnotation ->

                                    val sensor = annotationToSensorMap[clickedAnnotation]
                                    if (sensor != null) {

                                        // Remover a ViewAnnotation anterior, se houver
                                        viewAnnotationsMap[sensor.id]?.let { view ->
                                            viewAnnotationManager?.removeViewAnnotation(view)
                                        }

                                        // Adicionar a nova ViewAnnotation para o sensor clicado
                                        viewAnnotationsMap[sensor.id] = viewAnnotationManager?.addViewAnnotation(
                                            resId = R.layout.item_callout_view,
                                            options = viewAnnotationOptions {
                                                geometry(clickedAnnotation.geometry)
                                                annotationAnchor {
                                                    anchor(ViewAnnotationAnchor.BOTTOM)
                                                }
                                            }
                                        )?.apply {

                                            // Configurar os dados do sensor na caixa de informação
                                            val sensorInfoTextView = findViewById<TextView>(R.id.text_sensor_info)
                                            sensorInfoTextView.text = "Sensor ID: ${sensor.id}, Umidade: ${sensor.nivelUmidade}, Temp: ${sensor.temperatura}"

                                            // botão de fechar
                                            val closeButton = findViewById<Button>(R.id.button_close)
                                            closeButton.setOnClickListener {
                                                viewAnnotationManager?.removeViewAnnotation(this)
                                                viewAnnotationsMap[sensor.id] = null // Limpar a referência após fechar
                                            }
                                        }
                                    }

                                    true // Retornar true para o clique
                                }
                            }
                        }
                    }
                )
            }

            // Parte inferior (25%) com informações de sensores ativos/inativos
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(64.dp)
            ) {
                // Exibir irrigadores ativos e inativos
                val ativos = listaSensores.count { it.statusRele == "on" }
                val inativos = listaSensores.count { it.statusRele == "off" }

                // Layout melhorado para exibir os sensores
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Exibir os sensores ativos com ícone azul
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.azul),
                            contentDescription = "Sensores Ativos",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF6FAFDD),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ativos: $ativos",
                            color = Color(0xFF6FAFDD),
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    // Exibir os sensores inativos com ícone cinza
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cinza),
                            contentDescription = "Sensores Inativos",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Inativos: $inativos",
                            color = Color.Gray,
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Fundo opaco para quando o menu está expandido
            if (menuExpandido) {
                FundoOpaco(onClick = onBackgroundClick)
            }
        }
    }
}
