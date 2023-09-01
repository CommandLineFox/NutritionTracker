package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.ListFragment
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.MealFragment
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.ProfileFragment

class PagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager,
    internal var totalTabs: Int
) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ListFragment()
            1 -> return MealFragment()
            else -> return ProfileFragment()
        }
    }
}