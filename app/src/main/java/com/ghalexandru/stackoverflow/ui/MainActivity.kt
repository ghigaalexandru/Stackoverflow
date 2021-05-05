/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.ui

import android.os.Bundle
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

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        viewModel.fetchFirstQuestions()
        viewModel.questions.observe(this, {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.items?.let { list -> adapter.addList(list) }
                        displayProgressLoading(false)
                    }
                    Status.ERROR -> {
                        it.message?.let { message ->
                            displayError(message)
                        }
                        displayProgressLoading(false)
                    }
                    Status.LOADING -> displayProgressLoading(true)
                }
            }
        })
        binding.root.setOnRefreshListener {
            adapter.cleanList()
            viewModel.fetchFirstQuestions()
        }
    }

    private fun setupRecyclerView() {
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.fetchNextQuestions()
                }
            }
        })
    }

    private fun displayError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressLoading(isLoading: Boolean) {
        binding.root.isRefreshing = isLoading
    }
}