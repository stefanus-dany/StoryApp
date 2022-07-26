package id.stefanusdany.storyapp.ui.homepage

import javax.inject.Inject
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.domain.model.auth.LoginResultModel
import id.stefanusdany.storyapp.MyApplication
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityMainBinding
import id.stefanusdany.storyapp.ui.ViewModelFactory
import id.stefanusdany.storyapp.ui.addStory.AddStoryActivity
import id.stefanusdany.storyapp.ui.detail.DetailActivity
import id.stefanusdany.storyapp.ui.login.LoginActivity
import id.stefanusdany.storyapp.ui.maps.MapsActivity
import id.stefanusdany.storyapp.ui.utils.UIHelper.gone
import id.stefanusdany.storyapp.ui.utils.UIHelper.showSnackBar
import id.stefanusdany.storyapp.ui.utils.UIHelper.visible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels {
        factory
    }
    private lateinit var adapter: MainAdapter
    private var userInfo: LoginResultModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        isLogin()
    }

    private fun getAllStories() {
        if (userInfo != null) {
            mainViewModel.getAllStories(getString(R.string.token_format, userInfo?.token))
                .observe(this) { listStory ->
                    binding.apply {
                        when (listStory) {
                            is Result.Loading -> progressBar.visible()
                            is Result.Error -> {
                                showSnackBar(root, listStory.error)
                                progressBar.gone()
                            }
                            is Result.Success -> {
                                with(rvMain) {
                                    layoutManager = LinearLayoutManager(this@MainActivity)
                                    adapter = this@MainActivity.adapter
                                    setHasFixedSize(true)
                                }
                                tvEmpty.gone()
                                adapter.setData(listStory.data)
                                progressBar.gone()
                            }
                        }
                    }
                }
        } else {
            showSnackBar(binding.root, getString(R.string.error_get_info_user))
            binding.progressBar.gone()
        }

    }

    private fun setupRVAdapter() {
        adapter = MainAdapter()
        adapter.onItemClick = { selectedData ->
            val move = Intent(this, DetailActivity::class.java).apply {
                putExtra(Helper.EXTRA_STORY, selectedData)
            }
            startActivity(move)
        }
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
                setupRVAdapter()
                setupAction()
                getAllStories()
            }
        }
    }

    private fun setupView() {
        supportActionBar?.show()
        setContentView(binding.root)
    }

    private fun setupAction() {
        binding.fabAddStory.setOnClickListener {
            val move = Intent(this, AddStoryActivity::class.java)
            move.putExtra(Helper.EXTRA_TOKEN, userInfo?.token)
            startForResult.launch(move)
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Helper.RESULT_SUCCESS) {
            getAllStories()
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