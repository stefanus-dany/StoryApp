package id.stefanusdany.favorite.ui

import javax.inject.Inject
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.EntryPointAccessors
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.favorite.ViewModelFactory
import id.stefanusdany.favorite.databinding.ActivityFavoriteBinding
import id.stefanusdany.favorite.di.DaggerFavoriteComponent
import id.stefanusdany.storyapp.di.FavoriteModuleDependencies
import id.stefanusdany.storyapp.ui.detail.DetailActivity
import id.stefanusdany.storyapp.ui.utils.UIHelper.gone
import id.stefanusdany.storyapp.ui.utils.UIHelper.visible

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setupView()
        setupRVAdapter()
        getAllFavoriteStories()
    }

    private fun getAllFavoriteStories() {
        binding.apply {
            progressBar.visible()
            favoriteViewModel.getFavoriteStory().observe(this@FavoriteActivity) { listStory ->
                if (!listStory.isNullOrEmpty()) {
                    with(rvMain) {
                        layoutManager = LinearLayoutManager(this@FavoriteActivity)
                        adapter = this@FavoriteActivity.adapter
                        setHasFixedSize(true)
                    }
                    rvMain.visible()
                    tvEmpty.gone()
                    adapter.setData(listStory)
                    progressBar.gone()
                } else {
                    rvMain.gone()
                    tvEmpty.visible()
                    progressBar.gone()
                }
            }
        }

    }

    private fun setupRVAdapter() {
        adapter = FavoriteAdapter()
        adapter.onItemClick = { selectedData ->
            val move = Intent(this, DetailActivity::class.java).apply {
                putExtra(Helper.EXTRA_STORY, selectedData)
            }
            startActivity(move)
        }
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(id.stefanusdany.core.R.string.favorite)
        setContentView(binding.root)
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