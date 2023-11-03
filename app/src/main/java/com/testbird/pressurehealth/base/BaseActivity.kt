package com.testbird.pressurehealth.base

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.testbird.pressurehealth.BuildConfig
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.model.Contacts
import com.testbird.pressurehealth.model.ContentType
import com.testbird.pressurehealth.model.InfoEntity
import com.testbird.pressurehealth.model.RecordEntity
import com.testbird.pressurehealth.view.ContentActivity
import com.testbird.pressurehealth.view.MainActivity

abstract class BaseActivity<V : ViewBinding, M : ViewModel> : AppCompatActivity() {
    lateinit var binding: V
    lateinit var viewModel: M
    var isResume = false
    private var onResult: () -> Unit = {}
    private var startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            onResult.invoke()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isResume = false
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
        if (isResume.not())
            return
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun startContentActivity(
        type: ContentType,
        title: String = "",
        infoEntity: InfoEntity? = null,
        recordEntity: RecordEntity? = null,
        url: String? = null,
        onResult: () -> Unit = {}
    ) {
        this.onResult = onResult
        startActivityForResult.launch(Intent(this, ContentActivity::class.java).apply {
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
                putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            })
        }
    }

    override fun onStart() {
        super.onStart()
        isResume = false
    }

    override fun onResume() {
        super.onResume()
        isResume = true
    }

    override fun onPause() {
        super.onPause()
        isResume = false
    }

    override fun onStop() {
        super.onStop()
        isResume = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isResume = false
    }

}
