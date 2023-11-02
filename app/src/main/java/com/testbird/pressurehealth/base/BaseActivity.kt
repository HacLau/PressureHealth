package com.testbird.pressurehealth.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.testbird.pressurehealth.BuildConfig
import com.testbird.pressurehealth.model.Contacts
import com.testbird.pressurehealth.model.ContentType
import com.testbird.pressurehealth.model.InfoEntity
import com.testbird.pressurehealth.model.RecordEntity
import com.testbird.pressurehealth.view.ContentActivity
import com.testbird.pressurehealth.view.MainActivity

abstract class BaseActivity<V : ViewBinding, M : ViewModel> : AppCompatActivity() {
    lateinit var binding: V
    lateinit var viewModel: M
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = initViewBinding()
        viewModel = initViewModel()
        setContentView(binding.root)
        initView()
    }

    abstract fun initView()

    abstract fun initViewModel(): M

    abstract fun initViewBinding(): V

    fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun startContentActivity(
        type: ContentType,
        title: String = "",
        infoEntity: InfoEntity? = null,
        recordEntity: RecordEntity? = null,
        url: String? = null
    ) {
        startActivity(Intent(this, ContentActivity::class.java).apply {
            putExtra(Contacts.pageType, type.type)
            putExtra(Contacts.infoEntity, infoEntity)
            putExtra(Contacts.recordEntity, recordEntity)
            putExtra(Contacts.url, url)
            putExtra(Contacts.title, title)
        })
    }


    fun sharedMySelf() {
        kotlin.runCatching {
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            })
        }
    }
}
