package com.joaororiz.desafio.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.joaororiz.desafio.android.R
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.util.ext.getLargeUrl
import com.joaororiz.desafio.android.util.ext.setImageFromUrl
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListAdapter : PagedListAdapter<Character, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private var loading: Boolean = false
    var eventClick: ((image: View, title: View, item: Character) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserListItemViewHolder(getViewByLayout(parent, R.layout.list_item_user))
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && loading) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    private fun getViewByLayout(parent: ViewGroup, idLayout: Int) = LayoutInflater.from(parent.context)
        .inflate(idLayout, parent, false)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserListItemViewHolder -> {
                getItem(position)?.let { holder.bind(it) }
            }

        }
    }

    companion object {
        private const val VIEW_TYPE_NETWORK_STATE = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val VIEW_TYPE_ITEM = 2
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Character> = object :
            DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(
                oldItem: Character, newItem: Character
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Character, newItem: Character
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class UserListItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character) {
            itemView.setOnClickListener {
                eventClick?.invoke(itemView.img_background, itemView.name, character)
            }
            itemView.name.text = character.name
            itemView.img_background.setImageFromUrl(character.thumbnail.getLargeUrl(), R.drawable.ic_round_account_circle)
        }
    }
}