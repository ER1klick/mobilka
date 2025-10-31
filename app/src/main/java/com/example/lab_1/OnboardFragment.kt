package com.example.lab_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class OnboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_onboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this)
            .asGif()
            .load(R.drawable.onboard_image)
            .into(imageView)

        val nextButton: Button = view.findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignIn()
        }
    }
}