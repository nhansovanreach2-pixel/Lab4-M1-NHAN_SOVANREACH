package com.example.myexpensetrackerv1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    expenses: List<Expense> = sampleExpenses,
    onAddExpense: () -> Unit = {},
    onDeleteExpense: (Expense) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpense
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_expense)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TotalSection(expenses)

            CategorySection(expenses)

            Text(
                text = stringResource(R.string.recent_expenses),
                modifier = Modifier.padding(8.dp)
            )

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (expenses.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_expenses),
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    ExpenseList(
                        expenses = expenses,
                        onDeleteExpense = onDeleteExpense
                    )
                }
            }
        }
    }
}
