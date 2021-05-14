package com.katherineryn.motv3.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.katherineryn.motv3.R
import com.katherineryn.motv3.databinding.FragmentFavoriteBinding
import com.katherineryn.motv3.ui.MainActivity
import com.katherineryn.motv3.ui.favorite.movie.MovieFavFragment
import com.katherineryn.motv3.ui.favorite.tvshow.TvShowFavFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    private var _favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _favoriteFragmentBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            (activity as MainActivity).setActionBarTitle("Favorite")
        }

        setViewPager()
    }

    private fun setViewPager() {
        val listFragment = listOf(MovieFavFragment(), TvShowFavFragment())
        val tabTitle = listOf(resources.getString(R.string.title_movie), resources.getString(R.string.title_tvshow))

        binding?.viewPager?.adapter = ViewPagerAdapter(listFragment, requireActivity().supportFragmentManager, lifecycle)
        TabLayoutMediator(tabs, view_pager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}