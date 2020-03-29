package com.example.animals_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.animals_app.R
import com.example.animals_app.model.Animal
import com.example.animals_app.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalAdapter(arrayListOf())

    private val animalListDataObserver = Observer<List<Animal>> { list: List<Animal> ->
        list?.let {
            recyclerView.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading: Boolean ->
        loading_view.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            listError.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> { isError: Boolean ->
        listError.visibility = if (isError) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)
        viewModel.refresh()

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter

        }

        refreshLayout.setOnRefreshListener {
            recyclerView.visibility = View.GONE
            listError.visibility = View.GONE
            loading_view.visibility = View.VISIBLE
            viewModel.hardRefresh()
            refreshLayout.isRefreshing = false
        }

    }
}
