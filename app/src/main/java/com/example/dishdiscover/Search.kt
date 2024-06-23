package com.example.dishdiscover

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdiscover.databinding.ActivityHomeBinding
import com.example.dishdiscover.databinding.ActivitySearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Search : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var database : DatabaseReference
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList:ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.goback.setOnClickListener {
            finish()
        }
        binding.search.requestFocus()
        setUpRecycleView()
        setUpSearchListener()
    }

    private fun setUpSearchListener() {
        val searchField = binding.search
        searchField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Clear data list when search field gains focus
                dataList.clear()
                rvAdapter.notifyDataSetChanged()
            }
        }

        searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Perform search as text changes
                val searchQuery = s.toString().trim()
                fetchDataFromFirebase(searchQuery)
            }
        })
    }

    private fun setUpRecycleView() {
        dataList = ArrayList()

        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        rvAdapter = SearchAdapter(dataList, this@Search)
        binding.rvSearch.adapter = rvAdapter

        database = FirebaseDatabase.getInstance().getReference("Recipe")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val title = userSnapshot.child("title").getValue(String::class.java)
                        if (title != null ){
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

    private fun fetchDataFromFirebase(searchQuery: String) {
        database = FirebaseDatabase.getInstance().getReference("Recipe")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear() // Clear previous data before adding new data
                for (userSnapshot in snapshot.children) {
                    val recipe = userSnapshot.getValue(Recipe::class.java)
                    if (recipe != null) {
                        // Check if the recipe title contains the search query
                        if (recipe.title?.contains(searchQuery, ignoreCase = true) == true) {
                            dataList.add(recipe)
                        }
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching data: ${error.message}")
            }
        })
    }

}