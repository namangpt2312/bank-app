package com.example.bank

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bank.modals.Transactions
import kotlinx.android.synthetic.main.list_item.view.*

class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item : Transactions) {
        with(itemView) {
            if(item.type == "Deposit") {
                tvAmount.text = "Deposit ₹${item.amount}"
                tvAmount.setTextColor(Color.parseColor("#388E3C"))
                tvType.text = item.description
                timeTv.text = item.time?.formatAsListItem(context)
            }
            else {
                tvAmount.text = "Withdraw ₹${item.amount}"
                tvAmount.setTextColor(Color.parseColor("#D32F2F"))
                tvType.text = item.description
                timeTv.text = item.time?.formatAsListItem(context)
            }
        }
    }
}