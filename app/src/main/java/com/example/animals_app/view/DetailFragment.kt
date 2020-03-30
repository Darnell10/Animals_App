package com.example.animals_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.animals_app.R
import com.example.animals_app.databinding.FragmentDetailBinding
import com.example.animals_app.model.Animal
import com.example.animals_app.util.getProgressDrawable
import com.example.animals_app.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    var animal: Animal? = null

    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }
//        context?.let {
//            dataBinding.animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
//        }

        dataBinding.animal = animal

//        animalName.text = animal?.name
//        animalLocation.text = animal?.location
//        animalLifeSpan.text = animal?.lifeSpan
//        animalDiet.text = animal?.diet

    }


}
