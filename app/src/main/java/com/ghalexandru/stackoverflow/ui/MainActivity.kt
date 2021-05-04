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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv_main.adapter = adapter
        rv_main.layoutManager = layoutManager

        viewModel.getQuestions().observe(this, {
            it?.let {
                if (it.status == Status.SUCCESS) {
                    adapter.submitList(it.data)
                }
            }
        })
    }
}