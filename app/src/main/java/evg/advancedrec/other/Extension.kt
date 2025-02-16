package evg.advancedrec.other

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import evg.advancedrec.R

fun Fragment?.showSnackToast(textMessage: String) {
    this?.requireContext()?.let { context->
        val toast = Toast(context)
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.custom_toast, null)
        val textView = view.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = textMessage
        toast.view = view
        //toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }
}