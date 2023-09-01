package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.ShortMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentListitemBinding


class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {
    private var onClickListener: OnClickListener? = null

    inner class MealViewHolder(val binding: FragmentListitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ShortMeal>() {
        override fun areItemsTheSame(oldItem: ShortMeal, newItem: ShortMeal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: ShortMeal, newItem: ShortMeal): Boolean {
            return oldItem.strMeal == newItem.strMeal
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var meals: List<ShortMeal>
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
            listItemText.text = meal.strMeal
            DownloadImageFromInternet(listItemImage).execute(meal.strMealThumb)
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
        fun onClick(position: Int, shortMeal: ShortMeal)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}