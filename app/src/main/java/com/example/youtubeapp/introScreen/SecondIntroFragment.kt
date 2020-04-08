package com.example.youtubeapp.introScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.youtubeapp.R


class SecondIntroFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Toast.makeText(activity,"Second fragment", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_second_intro_page, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SecondIntroFragment()

    }
}
