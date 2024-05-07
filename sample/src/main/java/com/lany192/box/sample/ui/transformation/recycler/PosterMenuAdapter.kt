package com.lany192.box.sample.ui.transformation.recycler

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lany192.box.sample.databinding.ItemPosterMenuBinding
import com.lany192.box.sample.ui.transformation.TransformationDetailActivity

class PosterMenuAdapter : RecyclerView.Adapter<PosterMenuAdapter.PosterViewHolder>() {

  private val items = mutableListOf<Poster>()
  private var previousTime = SystemClock.elapsedRealtime()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
    val binding = ItemPosterMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return PosterViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
    val item = items[position]
    holder.binding.run {
      Glide.with(root.context)
        .load(item.poster)
        .into(itemPosterPost)
      itemPosterTitle.text = item.name
      root.setOnClickListener {
        val now = SystemClock.elapsedRealtime()
        if (now - previousTime >= itemPosterMenuTransformationLayout.duration) {
          TransformationDetailActivity.startActivity(root.context, itemPosterMenuTransformationLayout, item)
          previousTime = now
        }
      }
    }
  }

  fun addPosterList(list: List<Poster>) {
    items.clear()
    items.addAll(list)
    notifyDataSetChanged()
  }

  override fun getItemCount() = items.size

  class PosterViewHolder(val binding: ItemPosterMenuBinding) :
    RecyclerView.ViewHolder(binding.root)
}
