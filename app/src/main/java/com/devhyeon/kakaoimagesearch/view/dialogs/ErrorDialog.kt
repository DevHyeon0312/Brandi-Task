package com.devhyeon.kakaoimagesearch.view.dialogs

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.devhyeon.kakaoimagesearch.R

/** 에러 다이얼로그 */
class ErrorDialog private constructor() {
    //Builder Pattern 사용
    class NetworkErrorDialog {
        private lateinit var context : Context
        private var builder : AlertDialog.Builder? = null

        //Activity 에서 create 하는 경우
        fun create(activity: AppCompatActivity) : NetworkErrorDialog {
            context = activity
            builder = AlertDialog.Builder(context)
            builder!!.setMessage(context.getString(R.string.network_disconnect))
            builder!!.setCancelable(false)
            return this
        }
        //Fragment 에서 create 하는 경우
        fun create(fragment: Fragment) : NetworkErrorDialog {
            context = fragment.context!!
            builder = AlertDialog.Builder(context)
            builder!!.setMessage(context.getString(R.string.network_disconnect))
            builder!!.setCancelable(false)
            return this
        }
        //긍정의미의 ButtonClickListener 등록
        fun addPositiveButtonClick(positiveClickEvent: DialogInterface.OnClickListener?) : NetworkErrorDialog {
            builder!!.setPositiveButton(context.getString(R.string.button_retry),positiveClickEvent)
            return this
        }
        //부정의미의 ButtonClickListener 등록
        fun addNegativeButtonClick(negativeClickEvent: DialogInterface.OnClickListener?) : NetworkErrorDialog {
            builder!!.setNegativeButton(context.getString(R.string.button_finish),negativeClickEvent)
            return this
        }
        //보여주기
        fun show() {
            builder!!.show()
        }
    }
}