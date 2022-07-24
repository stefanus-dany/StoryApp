package id.stefanusdany.storyapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.stefanusdany.storyapp.R
import id.stefanusdany.data.data.remote.response.ListStoryResponse
import id.stefanusdany.storyapp.databinding.ActivityDetailBinding
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.storyapp.ui.utils.UIHelper.loadImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userDetail: ListStoryResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        userDetail =
            intent.getParcelableExtra<ListStoryResponse>(Helper.EXTRA_STORY) as ListStoryResponse
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