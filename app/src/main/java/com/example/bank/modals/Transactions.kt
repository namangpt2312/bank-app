package com.example.bank.modals

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Transactions(
    val amount : Int,
    val type : String,
    val description : String,
    @ServerTimestamp
    val time : Date? = null
) {
    constructor() : this(0, "", "")
}