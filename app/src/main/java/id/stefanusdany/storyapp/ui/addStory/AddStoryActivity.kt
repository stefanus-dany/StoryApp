package id.stefanusdany.storyapp.ui.addStory

import java.io.File
import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import id.stefanusdany.core.helper.utils.Helper
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.core.helper.utils.createTempFile
import id.stefanusdany.core.helper.utils.reduceFileImage
import id.stefanusdany.core.helper.utils.uriToFile
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityAddStoryBinding
import id.stefanusdany.storyapp.ui.ViewModelFactory
import id.stefanusdany.storyapp.ui.utils.UIHelper.getTextViewString
import id.stefanusdany.storyapp.ui.utils.UIHelper.gone
import id.stefanusdany.storyapp.ui.utils.UIHelper.showDialog
import id.stefanusdany.storyapp.ui.utils.UIHelper.showSnackBar
import id.stefanusdany.storyapp.ui.utils.UIHelper.visible

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var addStoryViewModel: AddStoryViewModel

    private var userToken: String? = null

    private var getFile: File? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat = DEFAULT_LAT_LONG
    private var long = DEFAULT_LAT_LONG

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.ivAddStory.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            binding.ivAddStory.setImageURI(selectedImg)
        }
    }

    override fun onResume() {
        super.onResume()
        getMyLastLocation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userToken = intent.getStringExtra(Helper.EXTRA_TOKEN)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        with(supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.title_add_story)
        }
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        addStoryViewModel = factory.create(AddStoryViewModel::class.java)
    }

    private fun requestCameraAndGalleryPermission() {
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_CAMERA_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    private fun setupAction() {
        binding.apply {
            btnCamera.setOnClickListener {
                if (!allPermissionsGranted()) requestCameraAndGalleryPermission() else startTakePhoto()
            }

            btnGallery.setOnClickListener {
                if (!allPermissionsGranted()) requestCameraAndGalleryPermission() else startGallery()
            }

            btnUpload.setOnClickListener {
                uploadImage()
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "id.stefanusdany.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }

        launcherIntentGallery.launch(
            Intent.createChooser(
                intent,
                getString(R.string.choose_a_picture)
            )
        )
    }

    private fun uploadImage() {
        binding.apply {
            progressBar.visible()
            when {
                etDescAddStory.getTextViewString().isEmpty() -> {
                    with(etDescAddStory) {
                        error = getString(R.string.insert_desc)
                        etDescAddStory.requestFocus()
                    }
                    progressBar.gone()
                }

                lat == DEFAULT_LAT_LONG && long == DEFAULT_LAT_LONG -> {
                    getMyLastLocation()
                    progressBar.gone()
                }

                else -> {
                    if (getFile != null) {
                        val file = reduceFileImage(getFile as File)
                        userToken?.let { token ->
                            addStoryViewModel.uploadStory(
                                getString(R.string.token_format, token),
                                file,
                                etDescAddStory.getTextViewString(),
                                lat,
                                long
                            ).observe(this@AddStoryActivity) { result ->
                                if (result != null) {
                                    when (result) {
                                        is Result.Loading -> {
                                            progressBar.visible()
                                        }

                                        is Result.Success -> {
                                            progressBar.gone()
                                            showDialog(
                                                context = this@AddStoryActivity,
                                                title = getString(R.string.title_alert_success),
                                                message = getString(R.string.message_alert_add_story_success),
                                                positiveButtonText = getString(R.string.ok),
                                                positiveButton = { _, _ ->
                                                    val intent = Intent()
                                                    setResult(Helper.RESULT_SUCCESS, intent)
                                                    finish()
                                                }
                                            )
                                        }

                                        is Result.Error -> {
                                            progressBar.gone()
                                            showDialog(
                                                context = this@AddStoryActivity,
                                                title = getString(R.string.title_alert_failed),
                                                message = getString(R.string.message_alert_add_story_failed),
                                                positiveButtonText = getString(R.string.ok),
                                                positiveButton = { _, _ ->
                                                    finish()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        progressBar.gone()
                        showSnackBar(root, getString(R.string.insert_image))
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                showSnackBar(binding.root, getString(R.string.error_didnt_get_permission))
                finish()
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }
                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    lat = location.latitude.toFloat()
                    long = location.longitude.toFloat()
                } else {
                    showSnackBar(binding.root, getString(R.string.error_location_not_found))
                }
            }
        } else {
            requestPermissionLauncher.launch(
                REQUIRED_LOCATION_PERMISSIONS
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_CAMERA_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
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

    companion object {
        private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private val REQUIRED_LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val DEFAULT_LAT_LONG = 0.0F
    }
}