package id.stefanusdany.storyapp.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityMainBinding
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.storyapp.ui.ViewModelFactory
import id.stefanusdany.storyapp.ui.addStory.AddStoryActivity
import id.stefanusdany.storyapp.ui.login.LoginActivity
import id.stefanusdany.storyapp.ui.maps.MapsActivity
import id.stefanusdany.storyapp.ui.utils.UIHelper.gone
import id.stefanusdany.storyapp.ui.utils.UIHelper.visible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private var userInfo: id.stefanusdany.data.data.remote.response.LoginResultResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportActionBar?.hide()
        setupViewModel()
        isLogin()
        setupRVAdapter()
        setupAction()
    }

    private fun getAllStories() {
        binding.progressBar.visible()
        if (userInfo != null) {
            mainViewModel.getAllStories(getString(R.string.token_format, userInfo?.token))
                .observe(this) { listStory ->
                    if (listStory != null) {
                        with(binding.rvMain) {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = this@MainActivity.adapter.withLoadStateFooter(
                                footer = LoadingStateAdapter {
                                    this@MainActivity.adapter.retry()
                                }
                            )
                            setHasFixedSize(true)
                        }
                        binding.tvEmpty.gone()
                        adapter.submitData(lifecycle, listStory)
                        binding.progressBar.gone()
                    } else {
                        binding.tvEmpty.visible()
                        binding.progressBar.gone()
                    }
                }
        } else {
            Snackbar.make(
                binding.root,
                getString(R.string.error_get_info_user),
                Snackbar.LENGTH_SHORT
            ).show()
            binding.progressBar.gone()
        }

    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        mainViewModel = factory.create(MainViewModel::class.java)
    }

    private fun setupRVAdapter() {
        adapter = MainAdapter()
    }

    private fun isLogin() {
        mainViewModel.getUserInfo().observe(this) {
            userInfo = it
            if (userInfo == null || userInfo?.token == "") {
                val move = Intent(this, LoginActivity::class.java)
                move.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(move)
                overridePendingTransition(0, 0)
            } else {
                setupView()
            }
        }
    }

    private fun setupView() {
        supportActionBar?.show()
        setContentView(binding.root)
        getAllStories()
    }

    private fun setupAction() {
        binding.fabAddStory.setOnClickListener {
            val move = Intent(this, AddStoryActivity::class.java)
            move.putExtra(Helper.EXTRA_TOKEN, userInfo?.token)
            startActivity(move)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.menu_logout -> {
                mainViewModel.logout()
                val move = Intent(this, LoginActivity::class.java)
                move.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(move)
                true
            }
            R.id.menu_open_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            else -> true
        }
    }
}