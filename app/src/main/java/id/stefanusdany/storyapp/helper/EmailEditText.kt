package id.stefanusdany.storyapp.helper

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import id.stefanusdany.storyapp.R

class EmailEditText : AppCompatEditText{
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                error =
                    when {
                        !Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches() -> context.getString(R.string.email_not_valid)
                        p0.toString().isEmpty() -> context.getString(R.string.insert_email)
                        else -> null
                    }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

}