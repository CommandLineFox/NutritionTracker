package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        loadData()
        setupButton()
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    private fun loadData() {
        viewModel.loadData()
        Repository.getInstance().fullMealData.observe(this.viewLifecycleOwner, Observer {
            if (it is FullMeal) {
                DownloadImageFromInternet(binding.fragmentMealImage).execute(it.strMealThumb)
                binding.fragmentMealTitle.text = it.strMeal
                binding.fragmentMealArea.text = "Area: " + it.strArea
                binding.fragmentMealCategory.text = "Category: " + it.strCategory
                if (it.strTags.isNullOrEmpty()) {
                    binding.fragmentMealTags.visibility = View.GONE
                } else {
                    binding.fragmentMealTags.text = "Tags: " + it.strTags.split(",").joinToString(", ")
                    binding.fragmentMealTags.visibility = View.VISIBLE
                }
                binding.fragmentMealIngredient1.text = it.strIngredient1
                binding.fragmentMealMeasurement1.text = it.strMeasure1
                if (it.strIngredient1.isNullOrEmpty()) {
                    binding.fragmentMealConstraint1.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint1.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient2.text = it.strIngredient2
                binding.fragmentMealMeasurement2.text = it.strMeasure2
                if (it.strIngredient2.isNullOrEmpty()) {
                    binding.fragmentMealConstraint2.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint2.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient3.text = it.strIngredient3
                binding.fragmentMealMeasurement3.text = it.strMeasure3
                if (it.strIngredient3.isNullOrEmpty()) {
                    binding.fragmentMealConstraint3.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint3.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient4.text = it.strIngredient4
                binding.fragmentMealMeasurement4.text = it.strMeasure4
                if (it.strIngredient4.isNullOrEmpty()) {
                    binding.fragmentMealConstraint4.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint4.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient5.text = it.strIngredient5
                binding.fragmentMealMeasurement5.text = it.strMeasure5
                if (it.strIngredient5.isNullOrEmpty()) {
                    binding.fragmentMealConstraint5.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint5.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient6.text = it.strIngredient6
                binding.fragmentMealMeasurement6.text = it.strMeasure6
                if (it.strIngredient6.isNullOrEmpty()) {
                    binding.fragmentMealConstraint6.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint6.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient7.text = it.strIngredient7
                binding.fragmentMealMeasurement7.text = it.strMeasure7
                if (it.strIngredient7.isNullOrEmpty()) {
                    binding.fragmentMealConstraint7.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint7.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient8.text = it.strIngredient8
                binding.fragmentMealMeasurement8.text = it.strMeasure8
                if (it.strIngredient8.isNullOrEmpty()) {
                    binding.fragmentMealConstraint8.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint8.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient9.text = it.strIngredient9
                binding.fragmentMealMeasurement9.text = it.strMeasure9
                if (it.strIngredient9.isNullOrEmpty()) {
                    binding.fragmentMealConstraint9.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint9.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient10.text = it.strIngredient10
                binding.fragmentMealMeasurement10.text = it.strMeasure10
                if (it.strIngredient10.isNullOrEmpty()) {
                    binding.fragmentMealConstraint10.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint10.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient11.text = it.strIngredient11
                binding.fragmentMealMeasurement11.text = it.strMeasure11
                if (it.strIngredient11.isNullOrEmpty()) {
                    binding.fragmentMealConstraint11.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint11.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient12.text = it.strIngredient12
                binding.fragmentMealMeasurement12.text = it.strMeasure12
                if (it.strIngredient12.isNullOrEmpty()) {
                    binding.fragmentMealConstraint12.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint12.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient13.text = it.strIngredient13
                binding.fragmentMealMeasurement13.text = it.strMeasure13
                if (it.strIngredient13.isNullOrEmpty()) {
                    binding.fragmentMealConstraint13.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint13.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient14.text = it.strIngredient14
                binding.fragmentMealMeasurement14.text = it.strMeasure14
                if (it.strIngredient14.isNullOrEmpty()) {
                    binding.fragmentMealConstraint14.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint14.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient15.text = it.strIngredient15
                binding.fragmentMealMeasurement15.text = it.strMeasure15
                if (it.strIngredient15.isNullOrEmpty()) {
                    binding.fragmentMealConstraint15.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint15.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient16.text = it.strIngredient16
                binding.fragmentMealMeasurement16.text = it.strMeasure16
                if (it.strIngredient16.isNullOrEmpty()) {
                    binding.fragmentMealConstraint16.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint16.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient17.text = it.strIngredient17
                binding.fragmentMealMeasurement17.text = it.strMeasure17
                if (it.strIngredient17.isNullOrEmpty()) {
                    binding.fragmentMealConstraint17.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint17.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient18.text = it.strIngredient18
                binding.fragmentMealMeasurement18.text = it.strMeasure18
                if (it.strIngredient18.isNullOrEmpty()) {
                    binding.fragmentMealConstraint18.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint18.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient19.text = it.strIngredient19
                binding.fragmentMealMeasurement19.text = it.strMeasure19
                if (it.strIngredient19.isNullOrEmpty()) {
                    binding.fragmentMealConstraint19.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint19.visibility = View.VISIBLE
                }

                binding.fragmentMealIngredient20.text = it.strIngredient20
                binding.fragmentMealMeasurement20.text = it.strMeasure20
                if (it.strIngredient20.isNullOrEmpty()) {
                    binding.fragmentMealConstraint20.visibility = View.GONE
                } else {
                    binding.fragmentMealConstraint20.visibility = View.VISIBLE
                }

                if (!it.strSource.isNullOrEmpty()) {
                    binding.fragmentMealSource.text = "Source: " + it.strSource
                    binding.fragmentMealSource.visibility = View.VISIBLE

                    binding.fragmentMealSource.setOnClickListener { item ->
                        run {
                            val openUrl = Intent(Intent.ACTION_VIEW)
                            openUrl.data = Uri.parse(it.strSource)

                            activity?.startActivity(openUrl)
                        }
                    }
                } else {
                    binding.fragmentMealSource.visibility = View.GONE
                }
                var video: String? = null
                if (!it.strYoutube.isNullOrEmpty()) {
                    video =
                        "<iframe width=\"100%\" height=\"100%\" src=\"" + it.strYoutube.replace(
                            "watch?v=",
                            "embed/"
                        ) + "\" title=\"" + it.strMeal + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                } else {
                    video =
                        "<iframe width=\"100%\" height=\"100%\" src=\"" + "https://www.youtube.com/watch?v=Fp-CUEB0H_c".replace(
                            "watch?v=",
                            "embed/"
                        ) + "\" title=\"" + it.strMeal + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                }

                binding.fragmentMealWebView.loadData(video, "text/html", "utf-8")
                binding.fragmentMealWebView.settings.javaScriptEnabled = true
                binding.fragmentMealWebView.webChromeClient = WebChromeClient()
                binding.fragmentMealInstructions.text = it.strInstructions
            }
        })
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

    private fun setupButton() {
        binding.fragmentMealButtonMenu.setOnClickListener {
            Repository.getInstance().addingToMenu = true
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentMeal, AddToMenuFragment())?.commitAllowingStateLoss()
        }
    }
}