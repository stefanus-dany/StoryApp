package id.stefanusdany.storyapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityDetailBinding
import id.stefanusdany.storyapp.ui.utils.UIHelper.loadImage

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
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
            setupMapFragment()
        }

    }

    private fun setupMapFragment() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
        setMarker()
    }

    private fun setMarker() {
        val latLng = LatLng(userDetail.lat, userDetail.lon)
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(userDetail.name)
        )

        val defaultCameraSetup = LatLng(userDetail.lat, userDetail.lon)
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                defaultCameraSetup,
                7f
            )
        )
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