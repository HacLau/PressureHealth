package com.testbird.pressurehealth.view

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.adapter.GuideAdapter
import com.testbird.pressurehealth.base.BaseActivity
import com.testbird.pressurehealth.databinding.ActivityGuideBinding
import com.testbird.pressurehealth.helper.ContactHelper
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.helper.or
import com.testbird.pressurehealth.helper.yes
import com.testbird.pressurehealth.viewmodel.ActivityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class GuideActivity : BaseActivity<ActivityGuideBinding, ActivityModel>() {
    override fun initViewModel(): ActivityModel = ViewModelProvider(this)[ActivityModel::class.java]

    override fun initViewBinding(): ActivityGuideBinding = ActivityGuideBinding.inflate(layoutInflater)

    override fun initView() {
        ContactHelper.isLauncherStart.or(funTrue = {
            showSplash()
        }, funFalse = {
            showStart()
        })
    }

    private fun showStart() {
        ContactHelper.isLauncherStart = true
        binding.guideSplashStart.visibility = View.VISIBLE
        binding.guideStep.visibility = View.GONE
        binding.btnStart.setOnClickListener {
            showSplash()
        }
    }

    private fun showSplash() {
        binding.guideStart.visibility = View.GONE
        binding.guideSplash.visibility = View.VISIBLE
        startCountDownProgress()
    }

    private fun showStep() {
        ContactHelper.isLauncherStep = true
        binding.guideSplashStart.visibility = View.GONE
        binding.guideStep.visibility = View.VISIBLE
        binding.guideVp.adapter = GuideAdapter(this)
        binding.guideVp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0, 1 -> {
                        binding.guideSkipNext.text = getString(R.string.text_next)
                    }

                    2 -> {
                        binding.guideSkipNext.text = getString(R.string.text_start_record)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.guideSkipNext.setOnClickListener {
            nextClick()
        }
        binding.guideSkipNextImage.setOnClickListener {
            nextClick()
        }

        binding.guideSkip.setOnClickListener {
            startMainActivity()
        }
    }

    private fun startCountDownProgress() {
        var startCountDown = false
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (binding.guideProgress.progress >= 100) {
                    cancel()
                    startCountDown.yes {
                        startCountDown = false
                        "startMainActivity".log()
                        CoroutineScope(Dispatchers.Main).launch {
                            ContactHelper.isLauncherStep.or(funTrue = {
                                startMainActivity()
                            }, funFalse = {
                                showStep()
                            })
                        }
                    }
                } else {
                    startCountDown = true
                    binding.guideProgress.progress++
                }
            }

        }, 33, 33)
    }

    private fun nextClick() {
        when (binding.guideVp.currentItem) {
            0, 1 -> {
                binding.guideVp.currentItem++
            }

            2 -> {
                startMainActivity()
            }
        }
    }


}