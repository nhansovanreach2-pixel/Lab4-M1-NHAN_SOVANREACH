package com.example.myexpensetrackerv1

import java.time.LocalDate
data class Expense(
    val id: String,
    val amount: Double,
    val currency: String,
    val category: String,
    val date: LocalDate,
    val remark: String? = null
)