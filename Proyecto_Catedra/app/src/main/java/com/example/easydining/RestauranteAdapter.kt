package com.example.easydining

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class RestauranteAdapter(private var restaurantes: List<Restaurante>) :
    RecyclerView.Adapter<RestauranteAdapter.RestauranteViewHolder>() {

    private var restaurantesFiltrados: MutableList<Restaurante> = ArrayList(restaurantes)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurante, parent, false)
        return RestauranteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val restaurante = restaurantesFiltrados[position]
        holder.nombreTextView.text = restaurante.name
        holder.direccionTextView.text = restaurante.direccion
        holder.horarioTextView.text = restaurante.horario
        Glide.with(holder.itemView.context).load(restaurante.imagen).into(holder.imagenImageView)
    }

    override fun getItemCount(): Int = restaurantesFiltrados.size

    fun filter(query: String) {
        restaurantesFiltrados = if (query.isEmpty()) {
            ArrayList(restaurantes)
        } else {
            val resultList = ArrayList<Restaurante>()
            for (restaurante in restaurantes) {
                if (restaurante.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                    resultList.add(restaurante)
                }
            }
            resultList
        }
        notifyDataSetChanged()
    }

    class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreRestaurante)
        val direccionTextView: TextView = itemView.findViewById(R.id.direccionRestaurante)
        val horarioTextView: TextView = itemView.findViewById(R.id.horarioRestaurante)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imagenRestaurante)
    }
}
