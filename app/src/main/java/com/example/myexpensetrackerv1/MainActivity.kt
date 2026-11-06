package com.example.myexpensetrackerv1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myexpensetrackerv1.ui.theme.MyExpenseTrackerV1Theme
import java.time.LocalDate
import java.util.UUID

private const val TAG = "MyExpenseTracker"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        enableEdgeToEdge()
        setContent {
            MyExpenseTrackerV1Theme {
                var expenses by remember { mutableStateOf(sampleExpenses) }

                DashboardScreen(
                    expenses = expenses,
                    onAddExpense = {
                        expenses = expenses + Expense(
                            id = UUID.randomUUID().toString(),
                            amount = 10.0,
                            currency = "USD",
                            category = "Food",
                            date = LocalDate.now()
                        )
                        Log.d(TAG, "Expense Added")
                    },
                    onDeleteExpense = { expenseToDelete ->
                        expenses = expenses.filterNot { it.id == expenseToDelete.id }
                        Log.d(TAG, "Expense Deleted: ${expenseToDelete.id}")
                    },
                    onClearAllExpenses = {
                        expenses = emptyList()
                        Log.d(TAG, "All Expenses Cleared")
                    },
                    onRestoreExpense = { restoredExpense, index ->
                        val currentList = expenses.toMutableList()
                        val targetIndex = if (index in 0..currentList.size) index else currentList.size
                        currentList.add(targetIndex, restoredExpense)
                        expenses = currentList
                        Log.d(TAG, "Expense Restored: ${restoredExpense.id}")
                    },
                    onRestoreAllExpenses = { restoredList ->
                        expenses = restoredList
                        Log.d(TAG, "All Expenses Restored")
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyExpenseTrackerV1Theme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardScreen()
}
