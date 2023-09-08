package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter

import android.annotation.SuppressLint
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
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IFavorite
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentFavoritesitemBinding
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.FavoritesFragment

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {
    lateinit var favoritesFragment: FavoritesFragment

    inner class FavoriteViewHolder(val binding: FragmentFavoritesitemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<IFavorite>() {
        override fun areItemsTheSame(oldItem: IFavorite, newItem: IFavorite): Boolean {
            return oldItem.getName() == newItem.getName()
        }

        override fun areContentsTheSame(oldItem: IFavorite, newItem: IFavorite): Boolean {
            return oldItem.getName() == newItem.getName()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var favorites: List<IFavorite>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = favorites.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            FragmentFavoritesitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.binding.apply {
            favoriteItemText.text = favorite.getName()

            if (favorite.getImage() != null) {
                DownloadImageFromInternet(favoriteItemImage).execute(favorite.getImage())
                favoriteItemInfoLayout.visibility = View.VISIBLE
            } else {
                favoriteItemInfoLayout.visibility = View.GONE
            }

            favoriteItemPercentage.text = ((favorite.getPercentage()!! * 10000).toInt().toDouble() / 100).toString() + "%"
            favoriteItemUses.text = "Prepared " + favorite.getTimesUsed() + " times"
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