package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentAddToMenuBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.AddToMenuViewModel
import java.util.Calendar


/**
 * A simple [Fragment] subclass.
 * Use the [AddToMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddToMenuFragment : Fragment() {
    private lateinit var binding: FragmentAddToMenuBinding
    private val viewModel by viewModel<AddToMenuViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddToMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupSpinner()
        setupButtons()
    }

    private fun setupView() {
        binding.fragmentAddToMenuTitle.text = Repository.getInstance().currentMeal?.getTitle()
        DownloadImageFromInternet(binding.fragmentAddToMenuImage).execute(Repository.getInstance().currentMeal?.getImage())
    }

    private fun setupSpinner() {
        binding.fragmentAddToMenuType.setSelection(0, false)
        binding.fragmentAddToMenuType.adapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.menuSpinnerMealTypes, android.R.layout.simple_spinner_item)
    }

    private fun setupButtons() {
        binding.fragmentAddToMenuImage.setOnClickListener {
            Toast.makeText(this.requireContext(), "Klik", Toast.LENGTH_SHORT).show()
        }

        binding.fragmentAddToMenuButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(binding.fragmentAddToMenuDate.year, binding.fragmentAddToMenuDate.month + 1, binding.fragmentAddToMenuDate.dayOfMonth)
            val date = calendar.timeInMillis / 1000
            val type = binding.fragmentAddToMenuType.selectedItem.toString()

            val currentMeal = Repository.getInstance().fullMealData.value
            if (currentMeal is FullMeal) {
                Repository.getInstance().saveableMeal = MealModel(0, currentMeal.idMeal, date, type, Gson().toJson(currentMeal))
            }

            viewModel.addToMenu()
            Repository.getInstance().addingToMenu = false
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentAddToMenu, MealFragment())?.commitAllowingStateLoss()
        }

        binding.fragmentAddToMenuTitle.setOnClickListener {
            Repository.getInstance().addingToMenu = false
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentAddToMenu, MealFragment())?.commitAllowingStateLoss()
        }
    }

    private inner class DownloadImageFromInternet(var imageView: ImageView) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}