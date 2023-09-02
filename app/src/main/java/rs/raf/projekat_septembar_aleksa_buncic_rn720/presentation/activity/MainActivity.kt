package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.adapter.PagerAdapter

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(0x00000400, 0x00000400)
        supportActionBar!!.hide()

        initView()
    }

    private fun initView() {
        tabLayout = findViewById(R.id.activityMainTabLayout)
        viewPager = findViewById(R.id.activityMainViewPager)

        tabLayout.addTab(tabLayout.newTab().setText("List"))
        tabLayout.addTab(tabLayout.newTab().setText("Meal"))
        tabLayout.addTab(tabLayout.newTab().setText("Profile"))

        val adapter = PagerAdapter(this, supportFragmentManager, 3)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        tabLayout.getTabAt(0)!!.select()
    }
}