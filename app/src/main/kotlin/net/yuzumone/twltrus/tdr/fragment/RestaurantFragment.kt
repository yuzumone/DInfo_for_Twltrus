package net.yuzumone.twltrus.tdr.fragment

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.ListFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import net.yuzumone.twltrus.tdr.R
import net.yuzumone.twltrus.tdr.adapter.RestaurantAdapter
import net.yuzumone.twltrus.tdr.model.Restaurant

class RestaurantFragment : ListFragment() {

    private val restaurants: ArrayList<Restaurant> by lazy {
        arguments.getParcelableArrayList<Restaurant>(ARG_RESTAURANTS)
    }

    companion object {
        val ARG_RESTAURANTS = "restaurants"
        fun newInstance(attractions: ArrayList<Restaurant>): RestaurantFragment {
            return RestaurantFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_RESTAURANTS, attractions)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = RestaurantAdapter(activity)
        adapter.addAll(restaurants)
        adapter.notifyDataSetChanged()
        listAdapter = adapter
        if (restaurants.isEmpty()) {
            val view = LayoutInflater.from(activity).inflate(R.layout.view_close, listView, false)
            listView.addHeaderView(view)
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val restaurant = adapter.getItem(position)
            if (restaurant.url != "") {
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .build()
                intent.launchUrl(activity, Uri.parse(restaurant.url))
            }
        }
    }
}