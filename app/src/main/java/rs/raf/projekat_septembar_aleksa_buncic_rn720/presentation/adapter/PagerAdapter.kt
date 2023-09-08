package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.CategoryFragment
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.FavoritesFragment
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.HistoryFragment
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.ListFragment
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.MealFragment

class PagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager,
    internal var totalTabs: Int
) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> if (Repository.getInstance().category != null && Repository.getInstance().area != null && Repository.getInstance().ingredient != null && Repository.getInstance().tag != null && Repository.getInstance().search != null) ListFragment() else CategoryFragment()
            1 -> MealFragment()
            2 -> HistoryFragment()
            else -> FavoritesFragment()
        }
    }
}