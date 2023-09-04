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
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealObject
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentListBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter.MealListAdapter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.ListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModel<ListViewModel>()
    private lateinit var mealListAdapter: MealListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupRecycler()
        loadData()
        setupButton()
        setupToggleButtons()
    }

    private fun setupRecycler() {
        binding.fragmentListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        mealListAdapter = MealListAdapter()
        mealListAdapter.setOnClickListener(object : MealListAdapter.OnClickListener {
            override fun onClick(position: Int, iMeal: IMeal) {
                if (Repository.getInstance().isMealFromApi) {
                    Repository.getInstance().id = iMeal.getId()
                } else if (iMeal is MealObject) {
                    Repository.getInstance().currentMeal = iMeal.meal
                }

                val mealFragment = MealFragment()

                if (Repository.getInstance().addingToMenu) {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentMeal, AddToMenuFragment())?.commit()
                    Repository.getInstance().addingToMenu = false
                } else {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentMeal, mealFragment)?.commit()
                }

                activity?.findViewById<TabLayout>(R.id.activityMainTabLayout)?.getTabAt(1)?.select()
            }
        })

        binding.fragmentListRecyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.fragmentListRecyclerView.adapter = mealListAdapter
    }

    private fun loadData() {
        mealListAdapter.meals = listOf()
        viewModel.loadData()
        Repository.getInstance().mealData.observe(this.viewLifecycleOwner, Observer {
            mealListAdapter.meals = it.sortedBy { item -> item.getTitle() }
        })
    }

    private fun setupButton() {
        binding.filterListSearchButton.setOnClickListener {
            if (!Repository.getInstance().search.isNullOrEmpty()) {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentList, ListFragment())?.commit()
            } else {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentList, FilterFragment())?.commit()
            }
        }

        binding.filterListSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.filterListSearchText.text.isNullOrEmpty()) {
                    binding.filterListSearchButton.setImageResource(R.drawable.filter)
                    Repository.getInstance().search = null
                } else {
                    binding.filterListSearchButton.setImageResource(R.drawable.search)
                    Repository.getInstance().search = binding.filterListSearchText.text.toString()
                    Repository.getInstance().category = null
                    Repository.getInstance().area = null
                    Repository.getInstance().ingredient = null
                    Repository.getInstance().tag = null
                }
            }
        })

        viewModel.planButtonClicked()
    }

    private fun setupToggleButtons() {
        binding.fragmentListFilterApi.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    binding.fragmentListFilterApi.isEnabled = false
                    binding.fragmentListFilterLocal.isChecked = false

                    binding.fragmentListFilterApi.setBackgroundColor(Color.WHITE)
                    binding.fragmentListFilterLocal.setBackgroundColor(Color.parseColor("#BBBBBB"))

                    if (Repository.getInstance().isMealFromApi) {
                        return@run
                    }

                    Repository.getInstance().isMealFromApi = true

                    mealListAdapter.meals = listOf()
                    viewModel.loadData()
                } else {
                    binding.fragmentListFilterApi.isEnabled = true
                }
            }
        }

        binding.fragmentListFilterLocal.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    binding.fragmentListFilterApi.isChecked = false
                    binding.fragmentListFilterLocal.isEnabled = false

                    binding.fragmentListFilterLocal.setBackgroundColor(Color.WHITE)
                    binding.fragmentListFilterApi.setBackgroundColor(Color.parseColor("#BBBBBB"))

                    if (!Repository.getInstance().isMealFromApi) {
                        return@run
                    }
                    Repository.getInstance().isMealFromApi = false

                    mealListAdapter.meals = listOf()
                    viewModel.loadData()
                } else {
                    binding.fragmentListFilterLocal.isEnabled = true
                }
            }
        }

        if (Repository.getInstance().isMealFromApi) {
            binding.fragmentListFilterApi.isChecked = true
        } else {
            binding.fragmentListFilterLocal.isChecked = true
        }
    }
}