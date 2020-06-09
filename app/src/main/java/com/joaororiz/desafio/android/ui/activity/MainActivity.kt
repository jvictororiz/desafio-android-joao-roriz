package com.joaororiz.desafio.android.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.core.util.Pair
import androidx.paging.PagedList
import com.joaororiz.desafio.android.R
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.datasource.NetworkState
import com.joaororiz.desafio.android.ui.adapter.UserListAdapter
import com.joaororiz.desafio.android.util.ext.startActivityAnim
import com.joaororiz.desafio.android.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()

    private val adapter: UserListAdapter by lazy {
        UserListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        supportActionBar?.hide()
        rv_personagens.adapter = adapter
        subscribe()
        setupListeners()
    }

    private fun subscribe() {


        viewModel.networkState.observe(this, Observer { state ->
            when (state) {
                NetworkState.LOADING -> progress.visibility = View.VISIBLE
                NetworkState.LOADED -> progress.visibility = View.GONE
                is NetworkState.ERROR -> {
                    tv_error.visibility = View.VISIBLE
                    rv_personagens.visibility = View.GONE
                    tv_error.text = getString(R.string.error)
                }
            }

        })

        viewModel.list.observe(this, Observer {
            tv_error.visibility = View.GONE
            rv_personagens.visibility = View.VISIBLE
            adapter.notifyItemRemoved(adapter.itemCount - 1)
            adapter.submitList(it)
        })
    }

    private fun setupListeners() {
        adapter.eventClick = { imageView, titleView, item ->
            startActivityAnim(
                Intent(this, DetailedCharactersActivity::class.java).apply {
                    putExtra(DetailedCharactersActivity.EXTRA_CHARACTERE, item)
                },
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, Pair(imageView, "image"), Pair(titleView, "title")
                )
            )
        }

        tv_error.setOnClickListener {
            viewModel.retry()
        }
    }

}
