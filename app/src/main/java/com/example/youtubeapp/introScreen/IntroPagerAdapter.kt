

package com.example.youtubeapp.introScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class IntroPagerAdapter(fm: FragmentManager, private val count: Int)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            1 -> SecondIntroFragment.newInstance()
            2 -> ThirdIntroFragment.newInstance()
            else -> FirstIntroFragment.newInstance()
        }
    }

    override fun getCount(): Int = count

}