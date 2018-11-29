package net.yuzumone.twltrus.tdr.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    fun add(title: String, fragment: Fragment) {
        fragments.add(fragment)
        titles.add(title)
        notifyDataSetChanged()
    }

    fun clear() {
        fragments.clear()
        titles.clear()
        notifyDataSetChanged()
    }
}