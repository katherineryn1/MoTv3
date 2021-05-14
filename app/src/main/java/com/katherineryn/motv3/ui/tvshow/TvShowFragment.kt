package com.katherineryn.motv3.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.katherineryn.motv3.R
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.databinding.FragmentTvshowBinding
import com.katherineryn.motv3.ui.MainActivity
import com.katherineryn.motv3.ui.detail.DetailActivity
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.katherineryn.motv3.utils.SortUtils
import com.katherineryn.motv3.utils.SortUtils.NEWEST
import com.katherineryn.motv3.viewmodel.ViewModelFactory
import com.katherineryn.motv3.vo.Resource
import com.katherineryn.motv3.vo.Status

class TvShowFragment : Fragment(), TvShowAdapter.OnItemClickCallback {

    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var fragmentTvShowBinding: FragmentTvshowBinding
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowBinding = FragmentTvshowBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as MainActivity).setActionBarTitle("Tv Shows")

            showProgressBar(true)
            val factory = ViewModelFactory.getInstance(requireActivity())
            tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            tvShowAdapter = TvShowAdapter()

            tvShowViewModel.getTvShows(NEWEST).observe(viewLifecycleOwner, tvShowObserver)

            with(fragmentTvShowBinding.rvTvshow) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                this.adapter = tvShowAdapter
            }
        }
    }

    private val tvShowObserver = Observer<Resource<PagedList<TvShowEntity>>> { tvShows ->
        if (tvShows != null) {
            when (tvShows.status) {
                Status.LOADING -> showProgressBar(true)
                Status.SUCCESS -> {
                    showProgressBar(false)
                    tvShowAdapter.submitList(tvShows.data)
                    tvShowAdapter.setOnItemClickCallback(this)
                    tvShowAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showProgressBar(false)
                    Toast.makeText(context, "Something wrong when retrieve the data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> sort = NEWEST
            R.id.action_oldest -> sort = SortUtils.OLDEST
            R.id.action_random -> sort = SortUtils.RANDOM
        }

        tvShowViewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, id)
            putExtra(DetailActivity.EXTRA_CATEGORY, TV_SHOW)
        }
        context?.startActivity(intent)
    }

    private fun showProgressBar(state: Boolean) {
        // if progress bar visible, recyclerview invisible
        fragmentTvShowBinding.progressBar.isVisible = state
        fragmentTvShowBinding.rvTvshow.isInvisible = state
    }
}