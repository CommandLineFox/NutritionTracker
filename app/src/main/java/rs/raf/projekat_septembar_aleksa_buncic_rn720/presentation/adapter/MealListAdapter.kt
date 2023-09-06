package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.ShortMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentListitemBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.AddToMenuFragment
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.ListFragment

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {
    private var onClickListener: OnClickListener? = null
    lateinit var listFragment: ListFragment

    inner class MealViewHolder(val binding: FragmentListitemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<IMeal>() {
        override fun areItemsTheSame(oldItem: IMeal, newItem: IMeal): Boolean {
            return oldItem.getId() == newItem.getId()
        }

        override fun areContentsTheSame(oldItem: IMeal, newItem: IMeal): Boolean {
            return oldItem.getTitle() == newItem.getTitle() && oldItem.getImage() == newItem.getImage()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var meals: List<IMeal>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = meals.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            FragmentListitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.binding.apply {
            listItemText.text = meal.getTitle()
            DownloadImageFromInternet(listItemImage).execute(meal.getImage())

            listItemType.text = meal.getType()
            listItemDate.text = meal.getDate()
            if (meal is ShortMeal || meal is FullMeal) {
                listItemType.visibility = View.GONE
                listItemDate.visibility = View.GONE
            } else {
                listItemType.visibility = View.VISIBLE
                listItemDate.visibility = View.VISIBLE
            }

            if (Repository.getInstance().isMealFromApi != 1) {
                listItemButtonEdit.visibility = View.GONE
                listItemButtonDelete.visibility = View.GONE
            } else {
                listItemButtonEdit.visibility = View.VISIBLE
                listItemButtonDelete.visibility = View.VISIBLE

                listItemButtonEdit.setOnClickListener {
                    Repository.getInstance().currentMeal = meal
                    Repository.getInstance().editingInMenu = true
                    listFragment.activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentMeal, AddToMenuFragment())?.commitAllowingStateLoss()
                    listFragment.activity?.findViewById<TabLayout>(R.id.activityMainTabLayout)?.getTabAt(1)?.select()
                }

                listItemButtonDelete.setOnClickListener {
                    Repository.getInstance().currentMeal = meal
                    listFragment.viewModel.deleteFromDatabase()

                    Repository.getInstance().mealList.remove(Repository.getInstance().currentMeal)
                    Repository.getInstance().mealData.value = Repository.getInstance().mealList
                    Repository.getInstance().currentMeal = null
                }
            }

            listItemButtonPlan.setOnClickListener {
                Repository.getInstance().currentMeal = meal
                Repository.getInstance().addingToPlan = true
                listFragment.activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentMeal, AddToMenuFragment())?.commitAllowingStateLoss()
                listFragment.activity?.findViewById<TabLayout>(R.id.activityMainTabLayout)?.getTabAt(1)?.select()
                Repository.getInstance().fullMealData.value = Repository.getInstance().currentMeal
            }
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, meal)
            }
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

    interface OnClickListener {
        fun onClick(position: Int, iMeal: IMeal)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}