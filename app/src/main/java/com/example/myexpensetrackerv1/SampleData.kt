package com.example.myexpensetrackerv1

import java.time.LocalDate
import java.util.UUID

val sampleExpenses = listOf(
    Expense(
        UUID.randomUUID().toString(),
        12.5,
        "USD",
        "Food",
        LocalDate.of(2026, 11, 1)
    ),
    Expense(
        UUID.randomUUID().toString(),
        25.0,
        "USD",
        "Transport",
        LocalDate.of(2026, 11, 2)
    )
)