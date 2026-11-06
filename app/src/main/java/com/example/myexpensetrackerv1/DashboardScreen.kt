package com.example.myexpensetrackerv1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    expenses: List<Expense> = sampleExpenses,
    onAddExpense: () -> Unit = {},
    onDeleteExpense: (Expense) -> Unit = {},
    onClearAllExpenses: () -> Unit = {},
    onRestoreExpense: (Expense, Int) -> Unit = { _, _ -> },
    onRestoreAllExpenses: (List<Expense>) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val expenseDeletedMessage = stringResource(R.string.expense_deleted)
    val allExpensesDeletedMessage = stringResource(R.string.all_expenses_deleted)
    val undoLabel = stringResource(R.string.undo)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    if (expenses.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                val previousExpenses = expenses.toList()
                                onClearAllExpenses()
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = allExpensesDeletedMessage,
                                        actionLabel = undoLabel,
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        onRestoreAllExpenses(previousExpenses)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.clear_all)
                            )
                        }
                    }
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
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (expenses.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.no_expenses),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    ExpenseList(
                        expenses = expenses,
                        onDeleteExpense = { expenseToDelete ->
                            val index = expenses.indexOf(expenseToDelete)
                            onDeleteExpense(expenseToDelete)
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = expenseDeletedMessage,
                                    actionLabel = undoLabel,
                                    duration = SnackbarDuration.Short
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onRestoreExpense(expenseToDelete, index)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
