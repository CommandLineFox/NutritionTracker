package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentMealBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.MealViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MealFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealFragment : Fragment() {
    private lateinit var binding: FragmentMealBinding
    private val viewModel by viewModel<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        loadData()
    }

    private fun loadData() {
        viewModel.loadData()
        Repository.getInstance().fullMeal.observe(this.viewLifecycleOwner, Observer {
            binding.fragmentMealText.text = it.toString()
        })
    }
}