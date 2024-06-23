package com.example.dishdiscover

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dishdiscover.databinding.ActivityCategoryBinding
import com.example.dishdiscover.databinding.ActivityRecipeBinding


class RecipeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeBinding
    var imgCrop = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemimg)
        binding.title.text=intent.getStringExtra("title")
        binding.stepsData.text=intent.getStringExtra("des")
        binding.ingData.text=intent.getStringExtra("ing")
        binding.scrollstep.visibility=View.GONE
        binding.ing.setTextColor(getColor(R.color.white))
        binding.steps.background=null

        binding.steps.setOnClickListener{
            binding.steps.setBackgroundResource(R.drawable.roundbtn)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ing.setTextColor(getColor(R.color.black))
            binding.ing.background=null
            binding.scrollstep.visibility=View.VISIBLE
            binding.scrolling.visibility=View.GONE
        }

        binding.ing.setOnClickListener{
            binding.ing.setBackgroundResource(R.drawable.roundbtn)
            binding.steps.setTextColor(getColor(R.color.black))
            binding.ing.setTextColor(getColor(R.color.white))
            binding.steps.background=null
            binding.scrolling.visibility=View.VISIBLE
            binding.scrollstep.visibility=View.GONE
        }

        binding.fullscreen.setOnClickListener {
            if(imgCrop){
                binding.itemimg.scaleType=ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemimg)
                binding.fullscreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                binding.shade.visibility = View.GONE
                imgCrop=!imgCrop
            }
            else{
                binding.itemimg.scaleType=ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemimg)
                binding.fullscreen.setColorFilter(null)
                binding.shade.visibility = View.GONE
                imgCrop=!imgCrop
            }
        }
        binding.backbtn.setOnClickListener {
            finish()
        }
    }
}