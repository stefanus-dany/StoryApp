package id.stefanusdany.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.storyapp.R
import id.stefanusdany.storyapp.databinding.ActivityLoginBinding
import id.stefanusdany.storyapp.ui.homepage.MainActivity
import id.stefanusdany.storyapp.ui.register.RegisterActivity
import id.stefanusdany.storyapp.ui.utils.UIHelper
import id.stefanusdany.storyapp.ui.utils.UIHelper.getTextViewString
import id.stefanusdany.storyapp.ui.utils.UIHelper.gone
import id.stefanusdany.storyapp.ui.utils.UIHelper.setViewAnimation
import id.stefanusdany.storyapp.ui.utils.UIHelper.visible

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var objectAnimator: ObjectAnimator
    private lateinit var objectAnimatorTitleTextView: ObjectAnimator
    private lateinit var objectAnimatorEmailTextView: ObjectAnimator
    private lateinit var objectAnimatorEmailEditTextLayout: ObjectAnimator
    private lateinit var objectAnimatorPasswordTextView: ObjectAnimator
    private lateinit var objectAnimatorPasswordEditTextLayout: ObjectAnimator
    private lateinit var objectAnimatorLoginButton: ObjectAnimator
    private lateinit var objectAnimatorDontHaveAccountTextView: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener {
                progressBar.visible()
                if (passwordEditText.error != null || emailEditText.error != null) progressBar.gone()
                else loadUser(
                    emailEditText.getTextViewString(),
                    passwordEditText.getTextViewString()
                )
            }

            dontHaveAccountTextView.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

    private fun loadUser(email: String, password: String) {
        loginViewModel.login(email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visible()
                    }

                    is Result.Success -> {
                        binding.progressBar.gone()
                        val resultResponse = result.data.result
                        loginViewModel.saveUser(
                            resultResponse.userId,
                            resultResponse.name,
                            resultResponse.token
                        )

                        UIHelper.showDialog(
                            context = this@LoginActivity,
                            title = getString(R.string.title_alert_success),
                            message = getString(R.string.message_alert_login_success),
                            positiveButtonText = getString(R.string.next),
                            positiveButton = { _, _ ->
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                        )
                    }

                    is Result.Error -> {
                        binding.progressBar.gone()
                        UIHelper.showDialog(
                            context = this@LoginActivity,
                            title = getString(R.string.title_alert_failed),
                            message = getString(R.string.message_alert_login_failed),
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

    private fun setupAnimation() {
        binding.apply {
            objectAnimatorTitleTextView = setViewAnimation(titleTextView)
            objectAnimatorEmailTextView = setViewAnimation(emailTextView)
            objectAnimatorEmailEditTextLayout = setViewAnimation(emailEditTextLayout)
            objectAnimatorPasswordTextView = setViewAnimation(passwordTextView)
            objectAnimatorPasswordEditTextLayout = setViewAnimation(passwordEditTextLayout)
            objectAnimatorLoginButton = setViewAnimation(loginButton)
            objectAnimatorDontHaveAccountTextView = setViewAnimation(dontHaveAccountTextView)
        }
    }

    private fun playAnimation() {
        setupAnimation()

        objectAnimator =
            ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
                duration = 6000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }
        objectAnimator.start()

        AnimatorSet().apply {
            playSequentially(
                objectAnimatorTitleTextView,
                objectAnimatorEmailTextView,
                objectAnimatorEmailEditTextLayout,
                objectAnimatorPasswordTextView,
                objectAnimatorPasswordEditTextLayout,
                objectAnimatorLoginButton,
                objectAnimatorDontHaveAccountTextView
            )
            start()
        }

    }

    override fun onDestroy() {
        objectAnimator.end()
        objectAnimatorTitleTextView.end()
        objectAnimatorEmailTextView.end()
        objectAnimatorEmailEditTextLayout.end()
        objectAnimatorPasswordTextView.end()
        objectAnimatorPasswordEditTextLayout.end()
        objectAnimatorLoginButton.end()
        objectAnimatorDontHaveAccountTextView.end()
        super.onDestroy()
    }

}