/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ghalexandru.stackoverflow.adapters.Adapter
import com.ghalexandru.stackoverflow.adapters.LoaderStateAdapter
import com.ghalexandru.stackoverflow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.HttpException
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

        viewModel.fetchQuestions().observe(this, { adapter.submitData(lifecycle, it) })
    }

    private fun setupRecyclerView() {
        val loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        recyclerview.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = layoutManager
        adapter.addLoadStateListener {
            val error = when {
                it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                it.append is LoadState.Error -> it.append as LoadState.Error
                it.refresh is LoadState.Error -> it.refresh as LoadState.Error
                else -> null
            }
            error?.let { e ->
                displayError(e.error)
            }
        }
    }

    private fun displayError(throwable: Throwable) {
        if (throwable is HttpException)
            Toast.makeText(this,
                "Http exception with code ${throwable.code()}",
                Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }
}