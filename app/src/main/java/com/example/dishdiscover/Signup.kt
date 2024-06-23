package com.example.dishdiscover

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdiscover.databinding.ActivitySignupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {

            val bemail = binding.email.text.toString()
            val bname = binding.name.text.toString()
            val bphone = binding.phone.text.toString()
            val busername = binding.username.text.toString()
            val bpsw = binding.psw.text.toString()
            val brpsw = binding.rpsw.text.toString()

            val name = bname.trim()
            val email = bemail.trim()
            val phno = bphone.trim()
            val username = busername.trim()
            val psw = bpsw.trim()
            val rpsw = brpsw.trim()

            if(name.isEmpty()){
                binding.name.error = "Name required"
                return@setOnClickListener
            }
            else if(email.isEmpty()){
                binding.email.error = "Email required"
                return@setOnClickListener
            }
            else if(phno.isEmpty()){
                binding.phone.error = "Phone number required"
                return@setOnClickListener
            }
            else if(username.isEmpty()){
                binding.username.error = "Username required"
                return@setOnClickListener
            }
            else if(psw.isEmpty()){
                binding.psw.error = "Password required"
                return@setOnClickListener
            }
            else if(rpsw.isEmpty()){
                binding.rpsw.error = "Password required"
                return@setOnClickListener
            }
            else if(psw!=rpsw){
                binding.rpsw.error = "Passwords must matched"
                return@setOnClickListener
            }
            else {
                database = FirebaseDatabase.getInstance().getReference("User")
                val user = User(bname, bemail, bphone,busername, bpsw, brpsw)

                database.child(busername).setValue(user).addOnSuccessListener {

                    binding.name.text.clear()
                    binding.email.text.clear()
                    binding.phone.text.clear()
                    binding.username.text.clear()
                    binding.psw.text.clear()
                    binding.rpsw.text.clear()

                    Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,Login::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}