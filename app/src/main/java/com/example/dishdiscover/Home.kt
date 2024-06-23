package com.example.dishdiscover

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdiscover.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Home : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var database : DatabaseReference
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList:ArrayList<Recipe>

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecycleView()

        binding.search.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        binding.catSalad.setOnClickListener {
            val intent = Intent(this,Category::class.java)
            intent.putExtra("Title","Salad")
            intent.putExtra("Cat","Salad")
            startActivity(intent)
        }
        binding.catMain.setOnClickListener {
            val intent = Intent(this,Category::class.java)
            intent.putExtra("Title","Main Dish")
            intent.putExtra("Cat","Dish")
            startActivity(intent)
        }
        binding.catDessert.setOnClickListener {
            val intent = Intent(this,Category::class.java)
            intent.putExtra("Title","Dessert")
            intent.putExtra("Cat","Dessert")
            startActivity(intent)
        }
        binding.catDrink.setOnClickListener {
            val intent = Intent(this,Category::class.java)
            intent.putExtra("Title","Drink")
            intent.putExtra("Cat","Drink")
            startActivity(intent)
        }

        val drawerLayout : DrawerLayout = findViewById(R.id.main)
        val navView : NavigationView = findViewById(R.id.navi_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.navi_home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    // No need for Intent, user is already on Home screen
                    true
                }
                R.id.navi_aboutus -> {
                    startActivity(Intent(this@Home, AboutUs::class.java))
                    true
                }

                R.id.navi_exit -> {
                    val intent = Intent(this@Home,Login::class.java)
                    startActivity(intent)
                }

                else -> {
                    false
                }
            }
            true
        }
        false

    }


    private fun setUpRecycleView() {
        dataList = ArrayList()

        binding.rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvAdapter = PopularAdapter(dataList, this@Home)
        binding.rvPopular.adapter = rvAdapter

        database = FirebaseDatabase.getInstance().getReference("Recipe")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val catValue = userSnapshot.child("cat").getValue(String::class.java)
                        if (catValue != null && catValue.contains("Popular")){
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