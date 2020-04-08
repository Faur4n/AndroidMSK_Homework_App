package com.example.youtubeapp.introScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.youtubeapp.R


class FirstIntroFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Toast.makeText(activity,"First fragment",Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_first_intro_page, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FirstIntroFragment()

    }
}
