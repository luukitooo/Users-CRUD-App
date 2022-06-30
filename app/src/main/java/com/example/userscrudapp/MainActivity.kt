package com.example.userscrudapp

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import com.example.userscrudapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val usersList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListeners()

    }

    private fun onClickListeners() {
        binding.addButton.setOnClickListener {
            if (!areLinesEmpty(binding.editTextContainer) && isEmailValid(binding.emailEditText.text.toString())) {
                if (addUser(getCurrentUser())) {
                    setMessage(resources.getString(R.string.add_success), isPositive = true)
                    updateUsersCounter()
                    clearLines(binding.editTextContainer)
                } else {
                    setMessage(resources.getString(R.string.add_error), isPositive = false)
                }
            }
        }

        binding.deleteButton.setOnClickListener {
            if (!areLinesEmpty(binding.editTextContainer) && isEmailValid(binding.emailEditText.text.toString())) {
                if (deleteUser(getCurrentUser())) {
                    setMessage(resources.getString(R.string.delete_success), isPositive = true)
                    updateUsersCounter()
                    clearLines(binding.editTextContainer)
                } else {
                    setMessage(resources.getString(R.string.delete_error), isPositive = false)
                }
            }
        }

        binding.updateButton.setOnClickListener {
            if (!areLinesEmpty(binding.editTextContainer) && isEmailValid(binding.emailEditText.text.toString())) {
                updateUser(this, getCurrentUser())
            }
        }
    }

    private fun setMessage(message: String, isPositive: Boolean) {
        if (isPositive) {
            binding.messageTextView.apply {
                text = message
                setTextColor(Color.GREEN)
            }
        } else {
            binding.messageTextView.apply {
                text = message
                setTextColor(Color.RED)
            }
        }
    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun areLinesEmpty(viewGroup: ViewGroup): Boolean {
        viewGroup.children.forEach { if (it is EditText && it.text.isEmpty()) return true }
        return false
    }

    private fun clearLines(viewGroup: ViewGroup) {
        viewGroup.children.forEach { if (it is EditText) it.setText("") }
    }

    private fun getCurrentUser(): User {
        return User(
            binding.firstNameEditText.text.toString(),
            binding.lastNameEditText.text.toString(),
            binding.ageEditText.text.toString().toShort(),
            binding.emailEditText.text.toString()
        )
    }

    private fun addUser(user: User): Boolean {
        if (!usersList.contains(user)) {
            usersList.add(user)
            return true
        }
        return false
    }

    private fun deleteUser(user: User): Boolean {
        if (usersList.contains(user)) {
            usersList.remove(user)
            return true
        }
        return false
    }

    private fun updateUser(context: Context, oldUser: User) {
        val updateUserDialog = UpdateUserDialog(context, oldUser)

        updateUserDialog.setOnSaveClickListener(object : UpdateUserDialog.OnSaveClickListener{
            override fun onClick(updatedUser: User) {
                usersList[usersList.indexOf(oldUser)] = updatedUser
                clearLines(binding.editTextContainer)
                setMessage(resources.getString(R.string.update_success), isPositive = true)
            }
        })
        updateUserDialog.show()
    }

    private fun updateUsersCounter() {
        binding.usersCount.text = usersList.size.toString()
    }
}