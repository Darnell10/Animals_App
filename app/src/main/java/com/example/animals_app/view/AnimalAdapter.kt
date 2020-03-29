package com.example.animals_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animals_app.R
import com.example.animals_app.model.Animal
import com.example.animals_app.util.getProgressDrawable
import com.example.animals_app.util.loadImage
import kotlinx.android.synthetic.main.item_layout.view.*

class AnimalAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_layout, parent, false)
        return AnimalViewHolder(view)
    }


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList[position].name
        holder.view.animalImage.loadImage(
            animalList[position].imageUrl,
            getProgressDrawable(holder.view.context))

    }

    override fun getItemCount(): Int = animalList.size

    class AnimalViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}