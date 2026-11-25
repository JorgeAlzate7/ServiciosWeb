package com.example.serviciosweb

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

class CharacterAdapter(
    private val listaPersonajes: List<CharacterModel>,
    private val onItemClick: (CharacterModel) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImagen: ImageView = itemView.findViewById(R.id.ivItemImagen)
        val tvNombre: TextView = itemView.findViewById(R.id.tvItemNombre)
        val tvEspecie: TextView = itemView.findViewById(R.id.tvItemEspecie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        // inflamos la vista para cada elemento de la lista
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val personaje = listaPersonajes[position]
        holder.tvNombre.text = personaje.nombre
        holder.tvEspecie.text = personaje.especie

        // limpiamos la imagen previa para evitar solapamientos al reciclar
        holder.ivImagen.setImageBitmap(null)

        // cargamos la imagen utilizando imagerequest de volley
        val queue = Volley.newRequestQueue(holder.itemView.context)
        val imageRequest = ImageRequest(
            personaje.imagenUrl,
            { bitmap ->
                holder.ivImagen.setImageBitmap(bitmap)
            },
            0,
            0,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error ->
                // si ocurre un error en la carga de la imagen
            }
        )
        queue.add(imageRequest)

        // asignamos el evento click para abrir el detalle
        holder.itemView.setOnClickListener {
            onItemClick(personaje)
        }
    }

    override fun getItemCount(): Int {
        return listaPersonajes.size
    }
}
