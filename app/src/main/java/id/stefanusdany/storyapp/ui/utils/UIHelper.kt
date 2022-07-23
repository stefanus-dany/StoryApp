package id.stefanusdany.storyapp.ui.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import id.stefanusdany.storyapp.R

object UIHelper {
    // authentication
    private const val alphaAnimation = 1f
    private const val durationAnimation = 500L

    fun setViewAnimation(view: View) =
        ObjectAnimator.ofFloat(view, View.ALPHA, alphaAnimation)
            .setDuration(durationAnimation)

    fun showDialog(
        context: Context,
        title: String,
        message: String,
        positiveButton: ((dialog: DialogInterface, p2: Int) -> Unit)? = null,
        positiveButtonText: String? = null,
        negativeButton: ((dialog: DialogInterface, p2: Int) -> Unit)? = null,
        negativeButtonText: String? = null
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButtonText) { p0, p1 ->
                positiveButton?.invoke(p0, p1)
            }
            setNegativeButton(negativeButtonText) { p0, p1 ->
                negativeButton?.invoke(p0, p1)
            }
            create()
            show()
        }
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun TextView.getTextViewString() = this.text.toString().trim()

    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions().override(500, 500))
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_downloading_24)
            .into(this)
    }

    fun showSnackBar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, duration).show()
    }

}