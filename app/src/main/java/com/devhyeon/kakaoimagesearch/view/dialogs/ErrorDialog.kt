package com.devhyeon.kakaoimagesearch.view.dialogs

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.devhyeon.kakaoimagesearch.R

/** 에러 다이얼로그 */
class ErrorDialog private constructor() {

    class NetworkErrorDialog {
        private lateinit var context : Context
        private var builder : AlertDialog.Builder? = null

        fun create(activity: AppCompatActivity) : NetworkErrorDialog {
            context = activity
            builder = AlertDialog.Builder(context)
            builder!!.setMessage(context.getString(R.string.network_disconnect))
            builder!!.setCancelable(false)
            return this
        }

        fun create(fragment: Fragment) : NetworkErrorDialog {
            context = fragment.context!!
            builder = AlertDialog.Builder(context)
            builder!!.setMessage(context.getString(R.string.network_disconnect))
            builder!!.setCancelable(false)
            return this
        }

        fun addPositiveButtonClick(positiveClickEvent: DialogInterface.OnClickListener?) : NetworkErrorDialog {
            builder!!.setPositiveButton(context.getString(R.string.button_retry),positiveClickEvent)
            return this
        }
        fun addNegativeButtonClick(negativeClickEvent: DialogInterface.OnClickListener?) : NetworkErrorDialog {
            builder!!.setNegativeButton(context.getString(R.string.button_finish),negativeClickEvent)
            return this
        }

        fun show() {
            builder!!.show()
        }
    }
}