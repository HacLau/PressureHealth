package com.testbird.pressurehealth.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.testbird.pressurehealth.helper.log

class MainAdapter(
    private val data: List<View>,
    ) : PagerAdapter() {
    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(data[position])
        return data[position]
    }
}

