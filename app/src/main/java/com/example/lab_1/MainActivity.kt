package com.example.lab_1
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardFragment())
                .commit()
        }
    }

    fun navigateToSignIn() {
        replaceFragment(SignInFragment())
    }

    fun navigateToSignUp() {
        replaceFragment(SignUpFragment(), addToBackStack = true)
    }

    fun navigateToHome(user: User) {
        val homeFragment = HomeFragment.newInstance(user)
        replaceFragment(homeFragment)
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false, name: String? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(name)
        }
        transaction.commit()
    }
}