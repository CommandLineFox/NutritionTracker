package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IFilter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.databinding.FragmentFilteritemBinding

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    private var onClickListener: OnClickListener? = null

    inner class FilterViewHolder(val binding: FragmentFilteritemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<IFilter>() {
        override fun areItemsTheSame(oldItem: IFilter, newItem: IFilter): Boolean {
            return oldItem.getTitle() == newItem.getTitle()
        }

        override fun areContentsTheSame(oldItem: IFilter, newItem: IFilter): Boolean {
            return oldItem.getTitle() == newItem.getTitle()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var ifilters: List<IFilter>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = ifilters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            FragmentFilteritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = ifilters[position]
        holder.binding.apply {
            fragmentFilterItemTitle.text = filter.getTitle()
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, filter)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, filter: IFilter)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}