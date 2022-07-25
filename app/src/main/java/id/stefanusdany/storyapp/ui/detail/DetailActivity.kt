package id.stefanusdany.storyapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityDetailBinding
import id.stefanusdany.storyapp.ui.utils.UIHelper.loadImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userDetail: ListStoryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        userDetail =
            intent.getParcelableExtra<ListStoryModel>(Helper.EXTRA_STORY) as ListStoryModel
        binding.apply {
            tvName.text = userDetail.name
            tvDesc.text = userDetail.description
            ivStory.loadImage(userDetail.photoUrl)
        }

    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_detail_story)
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