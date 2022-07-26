package id.stefanusdany.storyapp.ui.maps

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.stefanusdany.core.helper.utils.Helper.TAG
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityMapsBinding
import id.stefanusdany.storyapp.ui.utils.UIHelper.showSnackBar

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupMapFragment()
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

        setMapStyle()
        setMarker()
    }

    private fun setMarker() {
        mapsViewModel.getUserInfo().observe(this) { userInfo ->
            mapsViewModel.getAllMarkerMaps(
                getString(R.string.token_format, userInfo.token),
                DEFAULT_LOCATION
            )
                .observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                //do nothing
                            }

                            is Result.Success -> {
                                val response = result.data.listStory
                                for (i in response) {
                                    val latLng = LatLng(i.lat, i.lon)
                                    mMap.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .title(i.name)
                                    )
                                }
                                val defaultCameraSetup = LatLng(DEFAULT_LAT, DEFAULT_LONG)
                                mMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        defaultCameraSetup,
                                        5f
                                    )
                                )
                            }

                            is Result.Error -> {
                                showSnackBar(
                                    binding.root,
                                    getString(R.string.message_alert_register_failed)
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun setupView() {
        with(supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.title_google_maps)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> true
        }
    }

    companion object {
        private const val DEFAULT_LOCATION = 1
        private const val DEFAULT_LAT = -6.200000
        private const val DEFAULT_LONG = 106.816666
    }
}