package com.example.userscrudapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.userscrudapp.databinding.DialogUpdateUserBinding

class UpdateUserDialog(context: Context, private val user: User) : Dialog(context) {

    private lateinit var binding: DialogUpdateUserBinding

    private var onSaveClickListener: OnSaveClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogUpdateUserBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        init()

        onClickListeners()

    }

    private fun init() {
        binding.firstNameEditText.setText(user.firstName)
        binding.lastNameEditText.setText(user.lastName)
        binding.ageEditText.setText(user.age.toString())
        binding.emailEditText.setText(user.email)
    }

    private fun onClickListeners() {
        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }

        binding.saveButton.setOnClickListener {
            onSaveClickListener.let {
                val firstName = binding.firstNameEditText.text.toString()
                val lastName = binding.lastNameEditText.text.toString()
                val age = binding.ageEditText.text.toString().toShort()
                val email = binding.emailEditText.text.toString()
                onSaveClickListener?.onClick(User(firstName, lastName, age, email))
                this.dismiss()
            }
        }

    }

    interface OnSaveClickListener {
        fun onClick(updatedUser: User)
    }

    fun setOnSaveClickListener(onSaveClickListener: OnSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener
    }
}