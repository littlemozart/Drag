package com.lee.drag

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.item_logo.view.*
import kotlin.math.max
import kotlin.math.min

class LogoAdapter(
    private val logos: MutableList<Logo>,
    private val helper: ItemTouchHelper
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnItemTouchHelper {

    var isEditable = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent.inflate(R.layout.item_title))
            TYPE_CONTENT -> ContentViewHolder(parent.inflate(R.layout.item_logo))
            else -> DividerViewHolder(parent.inflate(R.layout.item_divider))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            val index = if (position > COLUMNS) position - 2 else position - 1
            val logo = logos.getOrNull(index)
            if (logo != null) {
                holder.setLogo(logo)
                holder.setEditable(isEditable)
                holder.itemView.setOnLongClickListener {
                    if (isEditable) {
                        helper.startDrag(holder)
                    }
                    true
                }
                holder.itemView.setOnClickListener {
                    Toast.makeText(it.context, "$position", Toast.LENGTH_SHORT).show()
                }
                holder.itemView.remove_btn.setOnClickListener {
                    if (isEditable) {
                        onItemDelete(position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (logos.isNullOrEmpty()) 1 else logos.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_TITLE
            position == COLUMNS + 1 -> TYPE_DIVIDER
            logos.size < COLUMNS && logos.size + 1 == position -> TYPE_DIVIDER
            else -> TYPE_CONTENT
        }
    }

    override fun onItemMove(
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (viewHolder is ContentViewHolder && target is ContentViewHolder) {
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition
            val start = min(from, to)
            val end = max(from, to)
            if (logos.isNotEmpty()) {
                if (start <= COLUMNS && end <= COLUMNS) { // both are before divider
                    val temp = logos[from - 1]
                    logos.removeAt(from - 1)
                    logos.add(to - 1, temp)
                } else if (start <= COLUMNS) {
                    if (from < to) { // from up to down
                        val temp = logos[from - 1]
                        logos.removeAt(from - 1)
                        logos.add(to - 2, temp)
                    } else { // from down to up
                        val temp = logos[from - 2]
                        logos.removeAt(from - 2)
                        logos.add(to - 1, temp)
                    }
                } else { // both are after divider
                    val temp = logos[from - 2]
                    logos.removeAt(from - 2)
                    logos.add(to - 2, temp)
                }
            }
            notifyItemMoved(from, to)
            notifyItemRangeChanged(start, end - start + 1)
            return true
        }
        return false
    }

    override fun onItemDelete(position: Int) {
        if (logos.isNotEmpty()) {
            if (position < COLUMNS) {
                logos.removeAt(position - 1)
            } else {
                logos.removeAt(position - 2)
            }
            notifyItemRemoved(position)
            if (logos.size == 0) {
                notifyDataSetChanged()
                return
            }
            if (position < itemCount) {
                notifyItemRangeChanged(position, itemCount - position)
            }
        }
    }

    private class TitleViewHolder(v: View) : RecyclerView.ViewHolder(v)

    private class ContentViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val placeholder = ColorDrawable(Color.parseColor("#F1F1F1"))

        private val logoIcon: ImageView = v.findViewById(R.id.logo_icon)
        private val logoText: TextView = v.findViewById(R.id.logo_text)
        private val editableGroup: Group = v.findViewById(R.id.group)

        fun setLogo(logo: Logo) {
            logoText.text = logo.text
            logoIcon.load(logo.url) { placeholder(placeholder) }
        }

        fun setEditable(editable: Boolean) {
            editableGroup.isVisible = editable
        }
    }

    private class DividerViewHolder(v: View) : RecyclerView.ViewHolder(v)

    companion object {
        const val COLUMNS = 4

        const val TYPE_TITLE = 1
        const val TYPE_CONTENT = 2
        const val TYPE_DIVIDER = 3
    }

    private fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
        return LayoutInflater.from(context).inflate(layoutId, this, false)
    }
}