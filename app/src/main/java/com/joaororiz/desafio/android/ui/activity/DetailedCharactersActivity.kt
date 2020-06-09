package com.joaororiz.desafio.android.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.joaororiz.desafio.android.R
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.util.ext.getFantasticUrl
import com.joaororiz.desafio.android.util.ext.hideWithAnim
import com.joaororiz.desafio.android.util.ext.setImageFromUrl
import com.joaororiz.desafio.android.util.ext.startActivityAnim
import com.joaororiz.desafio.android.viewModel.DetailedCharactersViewModel
import kotlinx.android.synthetic.main.activity_detailed_characters.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailedCharactersActivity : AppCompatActivity(R.layout.activity_detailed_characters) {

    private val viewModel: DetailedCharactersViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        intent.extras?.getParcelable<Character>(EXTRA_CHARACTERE)?.let { character ->
            img_background.setImageFromUrl(character.thumbnail.getFantasticUrl(), R.drawable.ic_round_account_circle)
            tv_name.text = character.name
            tv_description.text = character.description
            btn_send.setOnClickListener {
                viewModel.findComics(character)
            }
        }
        subscribe()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return if (menuItem.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(menuItem)
        }
    }

    private fun subscribe() {
        viewModel.load.observe(this, Observer {
            load.visibility = if (it) View.VISIBLE else View.GONE

        })
        viewModel.successTopComic.observe(this, Observer { comic ->
            tv_error.text = ""
            startActivityAnim(Intent(this, ComicCharactersActivity::class.java).apply {
                putExtra(ComicCharactersActivity.EXTRA_TOP_COMIC, comic)
            })
        })
        viewModel.error.observe(this, Observer {
            tv_error.text = it
        })
    }

    override fun finish() {
        super.finish()
        hideWithAnim()
    }

    companion object {
        const val EXTRA_CHARACTERE = "EXTRA_CHARACTERE"
    }

}
