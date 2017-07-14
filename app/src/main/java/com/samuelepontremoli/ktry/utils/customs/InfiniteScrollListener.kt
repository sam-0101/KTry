package com.samuelepontremoli.ktry.utils.customs

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by s.pontremoli on 13/07/2017.
 * Infinite scroll listener
 */
class InfiniteScrollListener(val func: () -> Unit, val layoutManager: StaggeredGridLayoutManager) : RecyclerView.OnScrollListener() {

    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            var firstVisibleItems: IntArray? = null
            firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems)

            if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
                pastVisibleItems = firstVisibleItems[0]
            }

            if (loading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    loading = false
                    func()
                }
            }
        }
    }

}