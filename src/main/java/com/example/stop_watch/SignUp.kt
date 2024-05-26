package com.example.stop_watch
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.stop_watch.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SignUp : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseDataBase:FirebaseDatabase
    private lateinit var databaseReference:DatabaseReference
    private lateinit var mAuth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        //Initlize firebase database
        firebaseDataBase= FirebaseDatabase.getInstance()

        //In database reference called firebase datbase that will refer a child name user
        databaseReference=firebaseDataBase.reference.child("users")
        binding.signupButton.setOnClickListener{

            val Username1=binding.name1.text.toString()
            val signupUsername=binding.signupUsername.text.toString()
            val signupPassword=binding.signupPassword.text.toString()


            if(signupUsername.isNotEmpty()  && Username1.isNotEmpty()  && signupPassword.isNotEmpty()){
                signupUser(Username1,signupUsername, signupPassword)
            }
            else{
                Toast.makeText(this@SignUp,"All fields are mandatory",Toast.LENGTH_SHORT).show()

            }
        }

        binding.loginRedirect.setOnClickListener{
            startActivity(Intent(this@SignUp,Login::class.java))
            finish()

        }

    }


    //Take username and password as a parameters
    //With the help of ...orderByChild... we can sort the data by the child that is username
    //EqualTo.... basically filter the data all the store username that matches with the username given by the user
    //DataSnapshotnapshot...is a object that reperent the specfic loc in firebase in real time database,
    //In simple word say that is the data already exist go to else part,otherwise it create new username
    //push().key...to create unique id

    private fun signupUser(name1:String,username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    val id=databaseReference.push().key
                    val userData=UserData(id,name1,username,password)
                    databaseReference.child(id!!).setValue(userData)

                    addUserToDatabase(id,username,name1)

                    Toast.makeText(this@SignUp,"Signup Success",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUp,Login::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this@SignUp,"User Already Exist",Toast.LENGTH_SHORT).show()

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUp,"Database Error:${databaseError.message}",Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun addUserToDatabase(name1: String,username: String,id:String){
        databaseReference=FirebaseDatabase.getInstance().getReference()
        databaseReference.child("users").child(id).setValue(User(id,name1,username))

    }
}
