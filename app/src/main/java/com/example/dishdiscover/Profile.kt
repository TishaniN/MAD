package com.example.dishdiscover

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdiscover.databinding.ActivityProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Profile : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbtn.setOnClickListener {
            finish()
        }

        val horizontalRadioGroup: RadioGroup = findViewById(R.id.radioGroup)
        var selectedCategory = ""

        horizontalRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)

            selectedCategory = when (checkedId) {
                R.id.salad -> "Salad"
                R.id.main -> "Main"
                R.id.bev -> "Beverage"
                R.id.des -> "Dessert"
                else -> ""
            }
        }

        binding.insert.setOnClickListener{
            val img = binding.img.text.toString()
            val title = binding.title.text.toString()
            val des = binding.des.text.toString()
            val ing = binding.ing.text.toString()
            val cat = selectedCategory
            val id = binding.title.text.toString()+binding.unProf.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Recipe")
            val recipe = Recipe(id, img, title,des, ing, cat)

            database.child(id.toString()).setValue(recipe).addOnSuccessListener {

                binding.img.text.clear()
                binding.title.text.clear()
                binding.des.text.clear()
                binding.ing.text.clear()

                Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
        val name = intent.getStringExtra("name")
        val username = intent.getStringExtra("username")

        val displayName = findViewById<TextView>(R.id.name_prof)
        val displayUN = findViewById<TextView>(R.id.un_prof)

        displayName.text = name
        displayUN.text = username

        }

    }
