package com.example.animals_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.animals_app.R
import com.example.animals_app.databinding.ItemLayoutBinding
import com.example.animals_app.model.Animal
import com.example.animals_app.util.AnimalClickListener
import com.example.animals_app.util.getProgressDrawable
import com.example.animals_app.util.loadImage
import kotlinx.android.synthetic.main.item_layout.view.*

class AnimalAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>(), AnimalClickListener {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: ItemLayoutBinding =
            DataBindingUtil.inflate<ItemLayoutBinding>(
                inflater,
                R.layout.item_layout, parent, false
            )
        return AnimalViewHolder(view)
    }


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this

    }

    override fun getItemCount(): Int = animalList.size

    override fun onClick(view: View) {
        for (animal: Animal in animalList) {
            if (view.tag == animal.name) {
                val action =
                    ListFragmentDirections.actionListFragmentToDetailFragment(animal)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    class AnimalViewHolder(var view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root)


}