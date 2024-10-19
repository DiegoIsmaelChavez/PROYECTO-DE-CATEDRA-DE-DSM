package com.example.easydining

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var restauranteAdapter: RestauranteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        fabAdd.setOnClickListener {
            // Navegar a la actividad de reserva
            val intent = Intent(this, ReservaActivity::class.java)
            startActivity(intent)
        }

        RetrofitClient.apiService.getRestaurantes().enqueue(object : Callback<List<Restaurante>> {
            override fun onResponse(call: Call<List<Restaurante>>, response: Response<List<Restaurante>>) {
                if (response.isSuccessful) {
                    val restaurantes = response.body() ?: emptyList()
                    restauranteAdapter = RestauranteAdapter(restaurantes)
                    recyclerView.adapter = restauranteAdapter

                    // Configurar la búsqueda
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            restauranteAdapter.filter(newText ?: "")
                            return true
                        }
                    })
                } else {
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Restaurante>>, t: Throwable) {
                Log.e("MainActivity", "Error: ${t.message}")
            }
        })
    }
}
