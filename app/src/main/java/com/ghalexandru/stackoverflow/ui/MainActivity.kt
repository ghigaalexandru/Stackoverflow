/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ghalexandru.stackoverflow.adapters.Adapter
import com.ghalexandru.stackoverflow.databinding.ActivityMainBinding
import com.ghalexandru.stackoverflow.network.Status
import com.ghalexandru.stackoverflow.viewmodels.ViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()

    @Inject
    lateinit var adapter: Adapter

    @Inject
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv_main.adapter = adapter
        rv_main.layoutManager = layoutManager

        viewModel.getQuestions().observe(this, {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        adapter.submitList(it.data)
                        displayProgressBar(false)
                    }
                    Status.ERROR -> {
                        it.message?.let { message ->
                            displayError(message)
                        }
                        displayProgressBar(false)
                    }
                    Status.LOADING -> displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressBar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}