package com.devhyeon.kakaoimagesearch.view.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.devhyeon.kakaoimagesearch.databinding.ActivityIntroBinding
import com.devhyeon.kakaoimagesearch.view.base.BaseActivity
import com.devhyeon.kakaoimagesearch.utils.Status
import com.devhyeon.kakaoimagesearch.view.dialogs.ErrorDialog
import com.devhyeon.kakaoimagesearch.viewmodels.IntroViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class IntroActivity : BaseActivity() {
    companion object {
        private val TAG = IntroActivity::class.java.name
    }

    //바인딩
    private lateinit var binding: ActivityIntroBinding

    //뷰모델
    private val introViewModel: IntroViewModel by viewModel()

    override fun initViewBinding() {
        binding = ActivityIntroBinding.inflate(layoutInflater)
    }

    override fun getViewRoot(): View {
        return binding.root
    }

    override fun addObserver() {
        introViewModelObserver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introViewModel.runCheckNetworkState(this@IntroActivity)
    }

    private fun introViewModelObserver() {
        //네트워크 연결상태 결과
        introViewModel.networkState.observe(this@IntroActivity, Observer {
            when(it) {
                is Status.Run -> {}
                is Status.Success -> {
                    //딜레이 진행
                    introViewModel.runDelay()
                }
                is Status.Failure -> {
                    showDialog()
                }
            }
        })
        //딜레이 결과
        introViewModel.delayState.observe(this@IntroActivity, Observer {
            when(it) {
                is Status.Run -> {}
                is Status.Success -> {
                    //메인액티비티 실행
                    startMainActivity()
                }
                is Status.Failure -> {
                    showDialog()
                }
            }
        })
    }

    /** MainActivity 실행 및 현재 Activity 종료 */
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showDialog() {
        val positiveButtonClick = DialogInterface.OnClickListener { dialog, which ->
            dialog!!.dismiss()
            introViewModel.runCheckNetworkState(this@IntroActivity)
        }
        val negativeButtonClick =DialogInterface.OnClickListener { dialog, which -> finish() }
        ErrorDialog.NetworkErrorDialog()
            .create(this@IntroActivity)
            .addPositiveButtonClick(positiveButtonClick)
            .addNegativeButtonClick(negativeButtonClick)
            .show()
    }
}