package general_staffs

import android.content.Context
import android.widget.Toast


class ToastWrapper(private val context: Context?) {
    /**
     * Show toast with genre or film title
     * */
    companion object{
        private const val ERROR_MESSAGE =  "Error"
    }


    fun showToast(message: String?) {
        when {
            message.isNullOrEmpty() -> {
                showToast(ERROR_MESSAGE)
            }
            else -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}