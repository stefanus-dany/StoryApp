package id.stefanusdany.storyapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityDetailBinding
import id.stefanusdany.storyapp.ui.utils.UIHelper.loadImage

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userDetail: ListStoryModel
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        userDetail =
            intent.getParcelableExtra<ListStoryModel>(Helper.EXTRA_STORY) as ListStoryModel
        binding.apply {
            with(userDetail) {
                tvName.text = name
                tvDesc.text = description
                ivStory.loadImage(photoUrl)

                // set favorite story
                var statusFavorite = isFavorite
                setStatusFavorite(statusFavorite)
                binding.fabFavorite.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailViewModel.setFavoriteStory(userDetail, statusFavorite)
                    setStatusFavorite(statusFavorite)
                }
            }
        }

    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_detail_story)
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    id.stefanusdany.core.R.drawable.ic_favorite_white
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    id.stefanusdany.core.R.drawable.ic_not_favorite_white
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> true
        }
    }
}