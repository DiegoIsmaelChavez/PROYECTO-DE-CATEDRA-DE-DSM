package com.example.easydining
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class ReservaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva)

        val spinnerRestaurantes = findViewById<Spinner>(R.id.spinnerRestaurantes)
        val spinnerMesa = findViewById<Spinner>(R.id.spinnerMesa)
        val editTextFechaHora = findViewById<EditText>(R.id.editTextFechaHora)
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val btnConfirmarReserva = findViewById<Button>(R.id.btnConfirmarReserva)

        // Cargar restaurantes desde la API (Simulación en este ejemplo)
        val restaurantes = listOf("Cadejo Brewing Company Huizucar", "Restaurante Don Li", "Faisca do Brasil", "Los Balcones", "Hacienda Real El Salvador")
        val adapterRestaurantes = ArrayAdapter(this, android.R.layout.simple_spinner_item, restaurantes)
        adapterRestaurantes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRestaurantes.adapter = adapterRestaurantes

        // Cargar opciones para número de personas
        val personas = (1..6).toList().map { "$it personas" }
        val adapterMesa = ArrayAdapter(this, android.R.layout.simple_spinner_item, personas)
        adapterMesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMesa.adapter = adapterMesa

        // Configurar el selector de fecha y hora
        val calendario = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        editTextFechaHora.setOnClickListener {
            DatePickerDialog(this, { _, year, month, day ->
                TimePickerDialog(this, { _, hour, minute ->
                    calendario.set(year, month, day, hour, minute)
                    editTextFechaHora.setText(dateFormat.format(calendario.time))
                }, calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), true).show()
            }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Manejar la acción de confirmar reserva
        btnConfirmarReserva.setOnClickListener {
            val restauranteSeleccionado = spinnerRestaurantes.selectedItem.toString()
            val personasSeleccionadas = spinnerMesa.selectedItem.toString()
            val fechaHora = editTextFechaHora.text.toString()
            val nombreReservante = editTextNombre.text.toString()

            if (restauranteSeleccionado.isNotEmpty() && personasSeleccionadas.isNotEmpty() && fechaHora.isNotEmpty() && nombreReservante.isNotEmpty()) {
                Toast.makeText(this, "Reserva confirmada para $nombreReservante en $restauranteSeleccionado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }
}
