package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentHistoryBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.HistoryViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    val viewModel by viewModel<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this

        viewModel.loadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        loadData()
        initChart()
    }

    private fun loadData() {
        viewModel.loadData()

        Repository.getInstance().historyData.observe(this.viewLifecycleOwner, Observer {
            initChart()
        })
    }

    private fun initChart() {
        val chart = binding.historyFragmentChart

        chart.description.isEnabled = false
        chart.setTouchEnabled(true)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val yAxis = chart.axisLeft
        yAxis.setDrawGridLines(true)

        chart.axisRight.isEnabled = false

        val label = "Meals added"
        val dataSet = LineDataSet(Repository.getInstance().historyList, label)
        dataSet.setDrawFilled(true)
        dataSet.lineWidth = 4f
        dataSet.fillColor = Color.RED

        val lineData = LineData(dataSet)
        chart.data = lineData
    }
}