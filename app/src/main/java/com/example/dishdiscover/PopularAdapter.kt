package com.example.dishdiscover

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishdiscover.databinding.ActivityLoginBinding
import com.example.dishdiscover.databinding.PopularRvItemBinding

class PopularAdapter (var dataList:ArrayList<Recipe>,var context:Context):RecyclerView.Adapter<PopularAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: PopularRvItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PopularRvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].img).into(holder.binding.popularImg)
        holder.binding.popularText.text=dataList.get(position).title

        holder.itemView.setOnClickListener{
            var intent = Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",dataList[position].img)
            intent.putExtra("title",dataList[position].title)
            intent.putExtra("des",dataList[position].des)
            intent.putExtra("ing",dataList[position].ing)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}