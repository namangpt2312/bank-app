package com.example.bank.modals

data class User(
    val name: String,
    val email: String,
    val age: String,
    val uid: String,
    var balance : Int
) {
    constructor() : this("", "", "", "", 0)
}