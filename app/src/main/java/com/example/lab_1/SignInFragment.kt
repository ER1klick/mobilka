package com.example.lab_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class SignInFragment : Fragment() {

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentFragmentManager.setFragmentResultListener("signUpRequestKey", this) { _, bundle ->
            user = bundle.getSerializable("user") as User
        }
        return inflater.inflate(R.layout.activity_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val signInButton: Button = view.findViewById(R.id.signInButton)
        val signUpButton: Button = view.findViewById(R.id.signUpButton)

        signUpButton.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignUp()
        }

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (::user.isInitialized && email == user.email && password == user.password) {
                (activity as? MainActivity)?.navigateToHome(user)
            } else {
                Toast.makeText(requireContext(), "Неверные учетные данные", Toast.LENGTH_SHORT).show()
            }
        }
    }
}