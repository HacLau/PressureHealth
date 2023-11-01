package com.testbird.pressurehealth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.databinding.LayoutGuideItemBinding

class GuideAdapter(private val context: Context) : PagerAdapter() {

    override fun getCount(): Int {
        return guideDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = LayoutGuideItemBinding.inflate(LayoutInflater.from(context))
        binding.stepImage.setImageResource(guideDataList[position].image)
        binding.stepTitle.text = context.getString(guideDataList[position].title)
        binding.stepContent.text = context.getString(guideDataList[position].content)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

val guideDataList: List<GuideEntity> = listOf(
    GuideEntity(R.string.text_step_title_1, R.string.text_step_content_1, R.mipmap.bg_guide_step1),
    GuideEntity(R.string.text_step_title_2, R.string.text_step_content_1, R.mipmap.bg_guide_step2),
    GuideEntity(R.string.text_step_title_3, R.string.text_step_content_1, R.mipmap.bg_guide_step3)
)

data class GuideEntity(
    val title: Int,
    val content: Int,
    val image: Int
)
