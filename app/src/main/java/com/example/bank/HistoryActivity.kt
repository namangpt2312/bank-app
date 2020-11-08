package com.example.bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bank.modals.Transactions
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_history.*
import java.lang.Exception

class HistoryActivity : AppCompatActivity() {

    lateinit var mAdapter : FirestorePagingAdapter<Transactions, RecyclerView.ViewHolder>

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val database by lazy {
        FirebaseFirestore.getInstance().collection("transactions").document(auth.uid!!).collection("list")
            .orderBy("time", Query.Direction.DESCENDING)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setupAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = mAdapter
        }
    }

    private fun setupAdapter() {
        val config = PagedList.Config.Builder()
            .setPrefetchDistance(2)
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        val options = FirestorePagingOptions.Builder<Transactions>()
            .setLifecycleOwner(this)
            .setQuery(database, config, Transactions::class.java)
            .build()

        mAdapter = object : FirestorePagingAdapter<Transactions, RecyclerView.ViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: Transactions) {
                if(holder is ViewHolder) {
                    holder.bind(model)
                }
            }
        }

    }
}