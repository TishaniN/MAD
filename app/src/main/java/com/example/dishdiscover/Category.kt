package com.example.dishdiscover

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdiscover.databinding.ActivityCategoryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Category : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryBinding
    private lateinit var database : DatabaseReference
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList:ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cat = intent.getStringExtra("Cat")
        val title = intent.getStringExtra("Title")
        binding.title.text=title

        binding.backbtn.setOnClickListener {
            finish()
        }
        setUpRecycleView(cat!!)
    }


    private fun setUpRecycleView(cat:String) {
        dataList = ArrayList()

        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        rvAdapter = CategoryAdapter(dataList, this@Category)
        binding.rvCategory.adapter = rvAdapter

        database = FirebaseDatabase.getInstance().getReference("Recipe")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val catValue = userSnapshot.child("cat").getValue(String::class.java)
                        if (catValue != null && catValue.contains(cat)){
                            val recipe = userSnapshot.getValue(Recipe::class.java)
                            if (recipe != null) {
                                dataList.add(recipe)
                                Log.d("Data", "Recipe added: $recipe")
                            }
                        }
                    }
                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}