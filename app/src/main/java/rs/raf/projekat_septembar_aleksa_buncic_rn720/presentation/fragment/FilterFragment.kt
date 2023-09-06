package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.Area
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.Category
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IFilter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.Ingredient
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.Tag
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentFilterBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter.FilterAdapter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.FilterViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private val viewModel by viewModel<FilterViewModel>()
    private lateinit var filterAdapter: FilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupRecycler()
        setupButtons()
        setupToggleButtons()

        binding.fragmentFilterCategory.isChecked = true
    }

    private fun setupRecycler() {
        binding.fragmentFilterRecyclerView.layoutManager = LinearLayoutManager(this.context)
        filterAdapter = FilterAdapter()
        filterAdapter.setOnClickListener(object : FilterAdapter.OnClickListener {
            override fun onClick(position: Int, filter: IFilter) {

                if (filter is Category) {
                    Repository.getInstance().category = filter.getTitle()
                    Repository.getInstance().area = null
                    Repository.getInstance().ingredient = null
                    Repository.getInstance().tag = null
                    Repository.getInstance().search = null
                }
                if (filter is Area) {
                    Repository.getInstance().category = null
                    Repository.getInstance().area = filter.getTitle()
                    Repository.getInstance().ingredient = null
                    Repository.getInstance().tag = null
                    Repository.getInstance().search = null
                }
                if (filter is Ingredient) {
                    Repository.getInstance().category = null
                    Repository.getInstance().area = null
                    Repository.getInstance().ingredient = filter.getTitle()
                    Repository.getInstance().tag = null
                    Repository.getInstance().search = null
                }
                if (filter is Tag) {
                    Repository.getInstance().category = null
                    Repository.getInstance().area = null
                    Repository.getInstance().ingredient = null
                    Repository.getInstance().tag = filter.getTitle()
                    Repository.getInstance().search = null
                }

                val listFragment = ListFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentFilter, listFragment)?.commit()
            }
        })

        binding.fragmentFilterRecyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.fragmentFilterRecyclerView.adapter = filterAdapter
    }

    private fun loadCategories() {
        filterAdapter.ifilters = listOf()
        viewModel.loadCategories()
        Repository.getInstance().filterData.observe(this.viewLifecycleOwner, Observer {
            if (Repository.getInstance().sortFilterAscending) {
                filterAdapter.ifilters = it.distinct().sortedBy { item -> item.getTitle() }
            } else {
                filterAdapter.ifilters = it.distinct().sortedByDescending { item -> item.getTitle() }
            }
        })
    }

    private fun loadAreas() {
        filterAdapter.ifilters = listOf()
        viewModel.loadAreas()
        Repository.getInstance().filterData.observe(this.viewLifecycleOwner, Observer {
            if (Repository.getInstance().sortFilterAscending) {
                filterAdapter.ifilters = it.distinct().sortedBy { item -> item.getTitle() }
            } else {
                filterAdapter.ifilters = it.distinct().sortedByDescending { item -> item.getTitle() }
            }
        })
    }

    private fun loadIngredients() {
        filterAdapter.ifilters = listOf()
        viewModel.loadIngredients()
        Repository.getInstance().filterData.observe(this.viewLifecycleOwner, Observer {
            if (Repository.getInstance().sortFilterAscending) {
                filterAdapter.ifilters = it.distinct().sortedBy { item -> item.getTitle() }
            } else {
                filterAdapter.ifilters = it.distinct().sortedByDescending { item -> item.getTitle() }
            }
        })
    }

    private fun loadTags() {
        filterAdapter.ifilters = listOf()
        viewModel.loadTags()
        Repository.getInstance().filterData.observe(this.viewLifecycleOwner, Observer {
            if (Repository.getInstance().sortFilterAscending) {
                filterAdapter.ifilters = it.distinct().sortedBy { item -> item.getTitle() }
            } else {
                filterAdapter.ifilters = it.distinct().sortedByDescending { item -> item.getTitle() }
            }
        })
    }

    private fun setupButtons() {
        binding.fragmentFilterSort.setOnClickListener {
            if (Repository.getInstance().sortFilterAscending) {
                Repository.getInstance().sortFilterAscending = false
                binding.fragmentFilterSort.text = "Z-A"
            } else {
                Repository.getInstance().sortFilterAscending = true
                binding.fragmentFilterSort.text = "A-Z"
            }

            if (Repository.getInstance().category != null) {
                loadCategories()
            } else if (Repository.getInstance().area != null) {
                loadAreas()
            } else if (Repository.getInstance().ingredient != null) {
                loadIngredients()
            } else if (Repository.getInstance().tag != null) {
                loadTags()
            }
        }

        binding.fragmentFilterSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Repository.getInstance().filterData.observe(this@FilterFragment.viewLifecycleOwner, Observer {
                    if (Repository.getInstance().sortFilterAscending) {
                        filterAdapter.ifilters = it.sortedBy { item -> item.getTitle() }.filter { item -> item.getTitle().contains(binding.fragmentFilterSearchText.text, true) }
                    } else {
                        filterAdapter.ifilters = it.distinct().sortedByDescending { item -> item.getTitle() }.filter { item -> item.getTitle().contains(binding.fragmentFilterSearchText.text, true) }
                    }
                })
            }
        })

    }

    private fun setupToggleButtons() {
        binding.fragmentFilterCategory.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    binding.fragmentFilterCategory.isEnabled = false
                    binding.fragmentFilterArea.isChecked = false
                    binding.fragmentFilterIngredient.isChecked = false
                    binding.fragmentFilterTag.isChecked = false

                    loadCategories()
                    binding.fragmentFilterCategory.setBackgroundColor(Color.WHITE)
                    binding.fragmentFilterIngredient.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterArea.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterTag.setBackgroundColor(Color.parseColor("#BBBBBB"))
                } else {
                    binding.fragmentFilterCategory.isEnabled = true
                }
            }
        }

        binding.fragmentFilterArea.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    binding.fragmentFilterCategory.isChecked = false
                    binding.fragmentFilterArea.isEnabled = false
                    binding.fragmentFilterIngredient.isChecked = false
                    binding.fragmentFilterTag.isChecked = false

                    loadAreas()
                    binding.fragmentFilterArea.setBackgroundColor(Color.WHITE)
                    binding.fragmentFilterCategory.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterIngredient.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterTag.setBackgroundColor(Color.parseColor("#BBBBBB"))
                } else {
                    binding.fragmentFilterArea.isEnabled = true
                }
            }
        }

        binding.fragmentFilterIngredient.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    binding.fragmentFilterCategory.isChecked = false
                    binding.fragmentFilterArea.isChecked = false
                    binding.fragmentFilterIngredient.isEnabled = false
                    binding.fragmentFilterTag.isChecked = false

                    loadIngredients()
                    binding.fragmentFilterIngredient.setBackgroundColor(Color.WHITE)
                    binding.fragmentFilterCategory.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterArea.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterTag.setBackgroundColor(Color.parseColor("#BBBBBB"))
                } else {
                    binding.fragmentFilterIngredient.isEnabled = true
                }
            }
        }

        binding.fragmentFilterTag.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    binding.fragmentFilterCategory.isChecked = false
                    binding.fragmentFilterArea.isChecked = false
                    binding.fragmentFilterIngredient.isChecked = false
                    binding.fragmentFilterTag.isEnabled = false

                    loadTags()
                    binding.fragmentFilterTag.setBackgroundColor(Color.WHITE)
                    binding.fragmentFilterCategory.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterIngredient.setBackgroundColor(Color.parseColor("#BBBBBB"))
                    binding.fragmentFilterArea.setBackgroundColor(Color.parseColor("#BBBBBB"))
                } else {
                    binding.fragmentFilterTag.isEnabled = true
                }
            }
        }
    }
}