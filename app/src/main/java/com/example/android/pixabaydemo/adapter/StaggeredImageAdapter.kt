package com.example.android.pixabaydemo.adapter



import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.pixabaydemo.R
import com.example.android.pixabaydemo.database.PxImage


class StaggeredImageAdapter(private val context: Context,val listener: ImageClickListener) : RecyclerView.Adapter<StaggeredImageAdapter.ImageViewHolder>(){
    val allImage  = ArrayList<PxImage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val viewHolder = ImageViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.image_container,parent,false))
        viewHolder.imageView.setOnClickListener{
            listener.onItemCLicked(allImage[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val current = allImage[position]
        holder.bind(current,context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<PxImage>){
        allImage.clear()
        allImage.addAll(newList)
        notifyDataSetChanged()
    }
    class ImageViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.findViewById(R.id.staggered_small_image)

        fun bind(item: PxImage,context: Context) {
            Glide.with(context).load(item.previewURL)
                .centerCrop().fitCenter()
                .thumbnail(0.3f)
                .placeholder(R.drawable.default_image).into(imageView)
        }

    }
    override fun getItemCount(): Int {
        return allImage.size
    }
}

interface ImageClickListener{
    fun onItemCLicked(pxImage: PxImage)
}
