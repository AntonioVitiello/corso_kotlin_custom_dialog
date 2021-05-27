package ant.vit.corsokotlin.tools

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ant.vit.corsokotlin.R

/**
 * Created by Antonio Vitiello
 * higher-order functions
 */

const val LOG_TAG = "dbg"

inline fun <T> Collection<T>.each(block: (T) -> Unit) {
    for (e in this) block(e)
}

inline fun <T> Collection<T>.eachWithIndex(block: (index: Int, T) -> Unit) {
    var i = 0
    for (e in this) block(i++, e)
}

inline fun <reified T> Any.isOfType(): Boolean = this is T

/**
 * Find implemented inteface in parentFragment, or activity
 */
inline fun <reified T> Fragment.implementingInterface(): T? {
    Log.d(LOG_TAG, "reified T is: ${T::class.java}")
    return when {
        parentFragment is T -> parentFragment as T
        activity is T -> activity as T
        else -> {
            Log.e(LOG_TAG, "Cannot trigger interface methods cause the caller doesn't implement the interface ${T::class.java}")
            null
        }
    }
}

inline fun <reified T> Context.systemService() = ContextCompat.getSystemService(this, T::class.java)

operator fun TextView.plusAssign(s: String) {
//    val concat = "%s\n%s"
//    text = String.format(concat, text, s)
    text = context.getString(R.string.messages_concat, text, s)
}
