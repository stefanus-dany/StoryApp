package id.stefanusdany.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.data.Result
import id.stefanusdany.storyapp.databinding.ActivityRegisterBinding
import id.stefanusdany.storyapp.ui.ViewModelFactory
import id.stefanusdany.storyapp.ui.login.LoginActivity
import id.stefanusdany.storyapp.ui.utils.UIHelper
import id.stefanusdany.storyapp.ui.utils.UIHelper.getTextViewString
import id.stefanusdany.storyapp.ui.utils.UIHelper.gone
import id.stefanusdany.storyapp.ui.utils.UIHelper.setViewAnimation
import id.stefanusdany.storyapp.ui.utils.UIHelper.visible

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        AnimatorSet().apply {
            with(binding) {
                playSequentially(
                    setViewAnimation(titleTextView),
                    setViewAnimation(nameTextView),
                    setViewAnimation(nameEditTextLayout),
                    setViewAnimation(emailTextView),
                    setViewAnimation(emailEditTextLayout),
                    setViewAnimation(passwordTextView),
                    setViewAnimation(passwordEditTextLayout),
                    setViewAnimation(signupButton),
                    setViewAnimation(haveAccountTextView),
                )
                start()
            }
        }

    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        registerViewModel = factory.create(RegisterViewModel::class.java)
    }

    private fun setupAction() {
        binding.apply {
            signupButton.setOnClickListener {
                progressBar.visible()

                when {
                    nameEditText.getTextViewString().isEmpty() -> nameEditTextLayout.error =
                        getString(R.string.insert_name)
                    passwordEditText.error != null || emailEditText.error != null || nameEditTextLayout.error != null -> progressBar.gone()
                    else -> {
                        saveUser(
                            nameEditText.getTextViewString(),
                            emailEditText.getTextViewString(),
                            passwordEditText.getTextViewString()
                        )
                    }
                }
            }

            haveAccountTextView.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

    private fun saveUser(name: String, email: String, password: String) {
        registerViewModel.register(name, email, password).observe(this) { result ->
            if (result != null) {
                binding.apply {
                    when (result) {
                        is Result.Loading -> {
                            progressBar.visible()
                        }

                        is Result.Success -> {
                            progressBar.gone()
                            UIHelper.showDialog(
                                context = this@RegisterActivity,
                                title = getString(R.string.title_alert_success),
                                message = getString(R.string.message_alert_register_success),
                                positiveButtonText = getString(R.string.login),
                                positiveButton = { _, _ ->
                                    val intent =
                                        Intent(this@RegisterActivity, LoginActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                }
                            )
                        }

                        is Result.Error -> {
                            progressBar.gone()
                            UIHelper.showDialog(
                                context = this@RegisterActivity,
                                title = getString(R.string.title_alert_failed),
                                message = getString(R.string.message_alert_register_failed),
                                positiveButtonText = getString(R.string.back),
                                positiveButton = { dialog, _ ->
                                    dialog.dismiss()
                                }
                            )
                        }
                    }
                }

            }
        }
    }

}