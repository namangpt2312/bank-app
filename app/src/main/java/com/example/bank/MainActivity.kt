package com.example.bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.bank.modals.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_deposit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnDeposit
import kotlinx.android.synthetic.main.activity_main.btnWithdraw
import kotlinx.android.synthetic.main.activity_withdraw.*

class MainActivity : AppCompatActivity() {

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val database by lazy {
        FirebaseFirestore.getInstance()
    }

    val ref = database.collection("users").document(auth.uid!!)
    lateinit var registeration : ListenerRegistration

    override fun onStart() {
        super.onStart()
        registeration = ref.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("err", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val current = snapshot.toObject(User:: class.java)!!
                    tvBalance.text = "₹${current.balance}"
                } else {
                    Log.d("err", "Current data: null")
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDeposit.setOnClickListener {
            val intent = Intent(this, DepositActivity :: class.java)
            startActivity(intent)
        }
        btnWithdraw.setOnClickListener {
            val intent = Intent(this, WithdrawActivity :: class.java)
            startActivity(intent)
        }
        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity :: class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        registeration.remove()
    }
}