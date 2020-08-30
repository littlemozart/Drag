package com.lee.drag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var adapter: LogoAdapter? = null
    private val logos: MutableList<Logo> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tool_bar.inflateMenu(R.menu.menu_main)
        tool_bar.setOnMenuItemClickListener {
            val isEditable = it.itemId == R.id.edit
            tool_bar.menu.findItem(R.id.edit).isVisible = !isEditable
            tool_bar.menu.findItem(R.id.done).isVisible = isEditable
            adapter?.isEditable = isEditable
            adapter?.notifyDataSetChanged()
            true
        }

        val helper = ItemTouchHelper(callback).apply { attachToRecyclerView(logo_list) }
        val layoutManager = GridLayoutManager(this, LogoAdapter.COLUMNS).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter?.getItemViewType(position)) {
                        LogoAdapter.TYPE_CONTENT -> 1
                        else -> 4
                    }
                }
            }
        }
        adapter = LogoAdapter(logos, helper)
        logo_list.layoutManager = layoutManager
        logo_list.adapter = adapter

        lifecycleScope.launch {
            val list = getLogoData()
            logos.clear()
            logos.addAll(list)
            adapter?.notifyDataSetChanged()
        }
    }

    private val callback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return adapter?.onItemMove(viewHolder, target) ?: false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

        override fun isItemViewSwipeEnabled(): Boolean = false

        override fun isLongPressDragEnabled(): Boolean = false
    }
}