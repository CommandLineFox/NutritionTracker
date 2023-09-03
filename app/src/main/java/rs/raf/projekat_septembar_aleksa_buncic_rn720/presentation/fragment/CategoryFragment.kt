package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment

import android.os.Bundle
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
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.Category
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentCategoryBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter.CategoryAdapter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.CategoryViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val viewModel by viewModel<CategoryViewModel>()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupRecycler()
        loadData()
    }

    private fun setupRecycler() {
        binding.fragmentCategoryRecyclerView.layoutManager = LinearLayoutManager(this.context)
        categoryAdapter = CategoryAdapter()
        categoryAdapter.setOnClickListener(object : CategoryAdapter.OnClickListener {
            override fun onClick(position: Int, category: Category) {
                Repository.getInstance().category = category.strCategory
                val listFragment = ListFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentCategory, listFragment)?.commitAllowingStateLoss()
            }
        })

        binding.fragmentCategoryRecyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.fragmentCategoryRecyclerView.adapter = categoryAdapter
    }

    private fun loadData() {
        categoryAdapter.categories = listOf()
        viewModel.loadData()
        Repository.getInstance().categoryData.observe(this.viewLifecycleOwner, Observer {
            categoryAdapter.categories = it.sortedBy { item -> item.strCategory }
        })
    }
}