package com.katherineryn.motv3.ui.movie

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
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.databinding.FragmentMovieBinding
import com.katherineryn.motv3.ui.MainActivity
import com.katherineryn.motv3.ui.detail.DetailActivity
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.MOVIE
import com.katherineryn.motv3.utils.SortUtils.NEWEST
import com.katherineryn.motv3.utils.SortUtils.OLDEST
import com.katherineryn.motv3.utils.SortUtils.RANDOM
import com.katherineryn.motv3.viewmodel.ViewModelFactory
import com.katherineryn.motv3.vo.Resource
import com.katherineryn.motv3.vo.Status

class MovieFragment : Fragment(), MovieAdapter.OnItemClickCallback {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as MainActivity).setActionBarTitle("Movies")

            showProgressBar(true)
            val factory = ViewModelFactory.getInstance(requireActivity())
            movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            movieAdapter = MovieAdapter()

            movieViewModel.getMovies(NEWEST).observe(viewLifecycleOwner, movieObserver)

            with(fragmentMovieBinding.rvMovie) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                this.adapter = movieAdapter
            }
        }
    }

    private val movieObserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
        if (movies != null) {
            when (movies.status) {
                Status.LOADING -> showProgressBar(true)
                Status.SUCCESS -> {
                    showProgressBar(false)
                    movieAdapter.submitList(movies.data)
                    movieAdapter.setOnItemClickCallback(this)
                    movieAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showProgressBar(false)
                    Toast.makeText(context, "Something wrong when retrieve the data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, id)
            putExtra(DetailActivity.EXTRA_CATEGORY, MOVIE)
        }
        context?.startActivity(intent)
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
            R.id.action_oldest -> sort = OLDEST
            R.id.action_random -> sort = RANDOM
        }

        movieViewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar(visibilityStatus: Boolean) {
        // if progress bar visible, recyclerview invisible
        fragmentMovieBinding.progressBar.isVisible = visibilityStatus
        fragmentMovieBinding.rvMovie.isInvisible = visibilityStatus
    }
}