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
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.ShortMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentListitemBinding

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {
    private var onClickListener: OnClickListener? = null

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