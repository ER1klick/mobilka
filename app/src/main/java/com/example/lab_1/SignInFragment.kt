package com.example.lab_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab_1.databinding.ActivitySignInBinding

class SignInFragment : Fragment() {

    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivitySignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<User>("registered_user")
            ?.observe(viewLifecycleOwner) { returnedUser ->
                user = returnedUser
                binding.emailEditText.setText(user.email)
            }

        binding.signUpButton.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (::user.isInitialized && email == user.email && password == user.password) {
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Неверные учетные данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}