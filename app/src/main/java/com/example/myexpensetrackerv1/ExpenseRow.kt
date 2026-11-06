package com.example.myexpensetrackerv1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.util.UUID

@Composable
fun ExpenseRow(
    expense: Expense,
    onDeleteExpense: ((Expense) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = expense.category)
            Text(text = expense.date.toString())
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${expense.currency} ${expense.amount}",
                fontWeight = FontWeight.Bold
            )

            if (onDeleteExpense != null) {
                IconButton(
                    onClick = { onDeleteExpense(expense) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseRowPreview() {
    ExpenseRow(
        Expense(
            id = UUID.randomUUID().toString(),
            amount = 20.0,
            currency = "USD",
            category = "Food",
            date = LocalDate.of(2026, 11, 1)
        )
    )
}

@Composable
fun TotalSection(expenses: List<Expense>) {
    val total = expenses.sumOf { it.amount }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(R.string.monthly_total))

            Text(
                "$%.2f".format(total),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun CategorySection(expenses: List<Expense>) {
    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { entry ->
            entry.value.sumOf { expense ->
                expense.amount
            }
        }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            stringResource(R.string.spending_category),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        categoryTotals.forEach { (category, total) ->
            Column {
                Text("$category : $$total")

                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width((total * 5).dp)
                        .background(Color.Blue)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ExpenseList(
    expenses: List<Expense>,
    onDeleteExpense: ((Expense) -> Unit)? = null
) {
    LazyColumn {
        items(expenses, key = { it.id }) { expense ->
            ExpenseRow(
                expense = expense,
                onDeleteExpense = onDeleteExpense
            )

            HorizontalDivider()
        }
    }
}
