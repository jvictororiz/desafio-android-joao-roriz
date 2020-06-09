package com.joaororiz.desafio.android.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.joaororiz.desafio.android.R
import com.joaororiz.desafio.android.data.entities.Comic
import com.joaororiz.desafio.android.util.ext.getFantasticUrl
import com.joaororiz.desafio.android.util.ext.hideWithAnim
import com.joaororiz.desafio.android.util.ext.setImageFromUrl
import com.joaororiz.desafio.android.viewModel.ComicCharactersViewModel
import kotlinx.android.synthetic.main.activity_comic_characters.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ComicCharactersActivity : AppCompatActivity(R.layout.activity_comic_characters) {
    private val viewModel: ComicCharactersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        observables()
        intent.extras?.getParcelable<Comic?>(EXTRA_TOP_COMIC)?.let { comic ->
            img_background.setImageFromUrl(comic.thumbnail.getFantasticUrl(), R.drawable.ic_round_account_circle)
            tv_name.text = comic.title
            tv_description.text = comic.description
            viewModel.selectComic(comic)
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return if (menuItem.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(menuItem)
        }
    }

    private fun observables() {
        viewModel.error.observe(this, Observer {
            list_prices.visibility = View.GONE
            tv_error.text = it
        })

        viewModel.listObservable.observe(this, Observer {
            list_prices.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
        })
    }

    override fun finish() {
        super.finish()
        hideWithAnim()
    }

    companion object {
        const val EXTRA_TOP_COMIC = "EXTRA_TOP_COMIC"
    }

}
