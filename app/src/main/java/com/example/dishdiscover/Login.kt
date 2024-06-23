package com.example.dishdiscover

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdiscover.databinding.ActivityLoginBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginbtn.setOnClickListener{
            val username = binding.username.text.toString()
            val pw = binding.pw.text.toString()

            if(username.isNotEmpty()){
                readData(username,pw)
            }
            else{
                Toast.makeText(this,"Please enter the username", Toast.LENGTH_SHORT).show()
            }
        }

        val guest = findViewById<TextView>(R.id.guest)
        guest.setOnClickListener{
            startActivity(Intent(this, Home::class.java))
        }

    }

    private fun readData(username: String,pw:String){
        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(username).get().addOnSuccessListener{
            if(it.exists()){
                val password = it.child("password").value
                if(password==pw){
                    Toast.makeText(this,"Successful Login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,UserHome::class.java)
                    val name = it.child("name").value.toString()
                    val bundle = Bundle()
                    bundle.putString("name",name)
                    bundle.putString("username",username)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Incorrect Password", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"User Doesn't Exist", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun load_signup(view: View){
        val intent = Intent(this,Signup::class.java)
        startActivity(intent)
    }

}