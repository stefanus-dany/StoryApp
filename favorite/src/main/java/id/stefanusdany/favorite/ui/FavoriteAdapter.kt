package id.stefanusdany.favorite.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ItemStoryBinding
import id.stefanusdany.storyapp.ui.utils.UIHelper.loadImage

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    private var listData = ArrayList<ListStoryModel>()
    var onItemClick: ((ListStoryModel) -> Unit)? = null

    fun setData(newListData: List<ListStoryModel>?) {
        if (newListData == null) return
        listData.clear()
        val sortedData = newListData.sortedByDescending {
            it.createdAt
        }
        listData.addAll(sortedData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemStoryBinding.bind(itemView)
        fun bind(data: ListStoryModel) {
            with(binding) {
                ivStory.loadImage(data.photoUrl)
                tvName.text = data.name
                tvDesc.text = data.description
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}