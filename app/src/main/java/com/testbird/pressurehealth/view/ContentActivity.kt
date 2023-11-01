package com.testbird.pressurehealth.view

import androidx.lifecycle.ViewModelProvider
import com.testbird.pressurehealth.base.BaseActivity
import com.testbird.pressurehealth.databinding.ActivityContentBinding
import com.testbird.pressurehealth.model.Contacts
import com.testbird.pressurehealth.viewmodel.ActivityModel

class ContentActivity : BaseActivity<ActivityContentBinding, ActivityModel>() {
    override fun initView() {
        binding.back.setOnClickListener {
            finish()
        }
        when(intent.getStringExtra(Contacts.pageType)){

        }
    }

    override fun initViewModel(): ActivityModel = ViewModelProvider(this)[ActivityModel::class.java]

    override fun initViewBinding(): ActivityContentBinding = ActivityContentBinding.inflate(layoutInflater)

}