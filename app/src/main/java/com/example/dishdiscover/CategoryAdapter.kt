package com.example.dishdiscover

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishdiscover.databinding.CategoryRvBinding

class CategoryAdapter (var dataList:ArrayList<Recipe>, var context:Context):RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: CategoryRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = CategoryRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].img).into(holder.binding.img)
        holder.binding.title.text=dataList.get(position).title
        holder.binding.next.setOnClickListener{
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