package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentFavoritesBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter.FavoritesAdapter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.FavoritesViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModel<FavoritesViewModel>()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.favoritesFragmentUsername.text = Repository.getInstance().username
        setupRecycler()
        setupSpinner()
        loadData()
    }

    private fun setupRecycler() {
        binding.favoritesFragmentRecycleView.layoutManager = LinearLayoutManager(this.context)
        favoritesAdapter = FavoritesAdapter()

        binding.favoritesFragmentRecycleView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.favoritesFragmentRecycleView.adapter = favoritesAdapter
    }

    private fun setupSpinner() {
        binding.favoritesFragmentSpinner.setSelection(0, false)
        binding.favoritesFragmentSpinner.adapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.menuFavoriteTypes, android.R.layout.simple_spinner_item)

        binding.favoritesFragmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (Repository.getInstance().favoriteSelected != p2) {
                    Repository.getInstance().favoriteSelected = p2

                    when (Repository.getInstance().favoriteSelected) {
                        0 -> {
                            if (Repository.getInstance().favoriteAreas.isEmpty()) {
                                viewModel.loadData()
                            } else {
                                Repository.getInstance().favoriteData.value = Repository.getInstance().favoriteAreas.toMutableList()
                            }
                        }

                        1 -> {
                            if (Repository.getInstance().favoriteCategories.isEmpty()) {
                                viewModel.loadData()
                            } else {
                                Repository.getInstance().favoriteData.value = Repository.getInstance().favoriteCategories.toMutableList()
                            }
                        }

                        2 -> {
                            if (Repository.getInstance().favoriteMeals.isEmpty()) {
                                viewModel.loadData()
                            } else {
                                Repository.getInstance().favoriteData.value = Repository.getInstance().favoriteMeals.toMutableList()
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun loadData() {
        viewModel.loadData()

        Repository.getInstance().observerData.observe(this.viewLifecycleOwner, Observer {
            viewModel.loadData()
        })

        favoritesAdapter.favorites = mutableListOf()
        Repository.getInstance().favoriteData.observe(this.viewLifecycleOwner, Observer {
            favoritesAdapter.favorites = it.distinctBy {
                it.getName()
            }
        })
    }
}