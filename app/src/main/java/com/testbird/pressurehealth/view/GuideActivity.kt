package com.testbird.pressurehealth.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.testbird.pressurehealth.GuideAdapter
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.databinding.ActivityGuideBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class GuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
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

    private fun showStep(){
        binding.guideSplashStart.visibility = View.GONE
        binding.guideStep.visibility = View.VISIBLE
        binding.guideVp.adapter = GuideAdapter(this)
        binding.guideVp.addOnPageChangeListener(object :OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0,1-> {
                        binding.guideSkipNext.text = getString(R.string.text_next)
                    }
                    2->{
                        binding.guideSkipNext.text = getString(R.string.text_start_record)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.guideSkipNext.setOnClickListener {
            if(binding.guideVp.currentItem < 2){
                binding.guideVp.currentItem ++
            }
        }
    }

    private fun startCountDownProgress(){
        Timer().schedule(object:TimerTask(){
            override fun run() {
                binding.guideProgress.progress ++
                if (binding.guideProgress.progress >= 100) {
                    cancel()
                    CoroutineScope(Dispatchers.Main).launch {
                        showStep()
                    }
                }

            }

        },33,33)
    }


}