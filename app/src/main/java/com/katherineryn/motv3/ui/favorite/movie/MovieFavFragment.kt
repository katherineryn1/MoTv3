package com.katherineryn.motv3.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.katherineryn.motv3.R
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.databinding.FragmentMovieFavBinding
import com.katherineryn.motv3.ui.detail.DetailActivity
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.MOVIE
import com.katherineryn.motv3.viewmodel.ViewModelFactory

class MovieFavFragment : Fragment(), MovieFavAdapter.OnItemClickCallback {

    private var _fragmentMovieFavBinding: FragmentMovieFavBinding? = null
    private val binding get() = _fragmentMovieFavBinding

    private lateinit var viewModel: MovieFavViewModel
    private lateinit var movieAdapter: MovieFavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieFavBinding = FragmentMovieFavBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavMovies().observe(viewLifecycleOwner, favMovieObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavMovie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieFavViewModel::class.java]

            movieAdapter = MovieFavAdapter()
            movieAdapter.setOnItemClickCallback(this)

            viewModel.getFavMovies().observe(viewLifecycleOwner, favMovieObserver)

            with(binding?.rvFavMovie) {
                this?.layoutManager = GridLayoutManager(context, 2)
                this?.setHasFixedSize(true)
                this?.adapter = movieAdapter
            }
        }
    }

    private val favMovieObserver = Observer<PagedList<MovieEntity>> { favMovies ->
        if (favMovies != null) {
            movieAdapter.submitList(favMovies)
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val movieEntity = movieAdapter.getSwipedData(swipedPosition)
                movieEntity?.let { viewModel.setFavMovie(it) }

                val snackBar = Snackbar.make(requireView(), R.string.msg_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.msg_ok) { _ ->
                    movieEntity?.let { viewModel.setFavMovie(it) }
                }
                snackBar.show()
            }
        }
    })

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieFavBinding = null
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, id)
            putExtra(DetailActivity.EXTRA_CATEGORY, MOVIE)
        }
        context?.startActivity(intent)
    }
}