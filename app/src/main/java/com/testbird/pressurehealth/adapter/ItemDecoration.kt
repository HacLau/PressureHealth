package com.testbird.pressurehealth.adapter

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.testbird.pressurehealth.appContext
import com.testbird.pressurehealth.helper.dp2px

class ItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        space.dp2px(appContext).let {
            outRect.left = it
            outRect.right = it
            outRect.top = it
            outRect.bottom = it
        }
    }
}

class ItemChartDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        space.dp2px(appContext).let {
            outRect.left = it
            outRect.right = it
        }
    }
}