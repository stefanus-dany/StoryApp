package id.stefanusdany.storyapp.ui.homepage

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.storyapp.R
import id.stefanusdany.data.data.remote.response.ListStoryResponse
import id.stefanusdany.storyapp.databinding.ItemStoryBinding
import id.stefanusdany.storyapp.ui.detail.DetailActivity
import id.stefanusdany.storyapp.ui.utils.UIHelper.loadImage

class MainAdapter :
    PagingDataAdapter<ListStoryResponse, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class ViewHolder(
        private val binding: ItemStoryBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ListStoryResponse) {
            with(binding) {
                tvName.text = data.name
                tvDesc.text = data.description
                ivStory.loadImage(data.photoUrl)
            }
            itemView.setOnClickListener {

                val move = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtra(Helper.EXTRA_STORY, data)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(
                            binding.ivStory,
                            itemView.context.getString(R.string.ivProfileTransitionName)
                        ),
                        Pair(
                            binding.tvName,
                            itemView.context.getString(R.string.nameProfileTransitionName)
                        ),
                        Pair(
                            binding.tvDesc,
                            itemView.context.getString(R.string.descProfileTransitionName)
                        ),
                    )
                itemView.context.startActivity(move, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryResponse>() {
            override fun areItemsTheSame(
                oldItem: ListStoryResponse,
                newItem: ListStoryResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryResponse,
                newItem: ListStoryResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}