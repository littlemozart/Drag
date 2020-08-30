package com.lee.drag

import androidx.recyclerview.widget.RecyclerView

interface OnItemTouchHelper {
    fun onItemMove(viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
    fun onItemDelete(position: Int)
}