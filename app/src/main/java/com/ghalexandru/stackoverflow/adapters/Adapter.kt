/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ghalexandru.stackoverflow.databinding.ItemQuestionBinding
import com.ghalexandru.stackoverflow.models.Question
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Adapter @Inject constructor(private val requestManager: RequestManager) :
    ListAdapter<Question, Adapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            binding.tvQuestionTitle.text = question.title
            question.owner?.let {
                requestManager.load(it.image).into(binding.ivOwnerAvatar)
                binding.tvOwnerName.text = it.name
            }
        }
    }

    fun addList(newList: List<Question>) {
        val list = mutableListOf<Question>()
        list.addAll(currentList)
        list.addAll(newList)
        submitList(list.distinct())
    }

    fun cleanList() {
        submitList(listOf())
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Question>() {
            override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}