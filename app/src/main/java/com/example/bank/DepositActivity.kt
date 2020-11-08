package com.example.bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.bank.modals.Transactions
import com.example.bank.modals.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_deposit.*
import java.util.*
import kotlin.properties.Delegates

class DepositActivity : AppCompatActivity() {

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val database by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit)

        btnDeposit.setOnClickListener {
            val amountString = etDeposit.text.toString()
            val ref = database.collection("users").document(auth.uid!!)
            val type = etDepositType.text.toString()

            if(amountString.isEmpty()) {
                Toast.makeText(this, "Amount cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else if(type.isEmpty()) {
                Toast.makeText(this, "Description cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else {
                val amount = etDeposit.text.toString().toInt()
                ref.get()
                    .addOnSuccessListener {
                        val currentUser = it.toObject(User :: class.java)!!
                        val user = User(
                            currentUser.name,
                            currentUser.email,
                            currentUser.age,
                            auth.uid!!,
                            currentUser.balance + amount
                        )
                        ref.set(user)
                            .addOnSuccessListener {
                                val transaction = Transactions(amount, "Deposit", type)
                                database.collection("transactions").document(auth.uid!!).collection("list").add(transaction)
                                Toast.makeText(this, "Amount deposited!!", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                    }
            }
        }
    }
}