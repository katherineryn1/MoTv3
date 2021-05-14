package com.katherineryn.motv3.ui.favorite.tvshow

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
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.databinding.FragmentTvShowFavBinding
import com.katherineryn.motv3.ui.detail.DetailActivity
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.katherineryn.motv3.viewmodel.ViewModelFactory

class TvShowFavFragment : Fragment(), TvShowFavAdapter.OnItemClickCallback {

    private var _fragmentTvshowFavBinding: FragmentTvShowFavBinding? = null
    private val binding get() = _fragmentTvshowFavBinding

    private lateinit var viewModel: TvShowFavViewModel
    private lateinit var tvShowAdapter: TvShowFavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvshowFavBinding = FragmentTvShowFavBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavTvShows().observe(viewLifecycleOwner, favTvShowObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavTvshow)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[TvShowFavViewModel::class.java]

            tvShowAdapter = TvShowFavAdapter()
            tvShowAdapter.setOnItemClickCallback(this)

            viewModel.getFavTvShows().observe(viewLifecycleOwner, favTvShowObserver)

            with(binding?.rvFavTvshow) {
                this?.layoutManager = GridLayoutManager(context, 2)
                this?.setHasFixedSize(true)
                this?.adapter = tvShowAdapter
            }
        }
    }

    private val favTvShowObserver = Observer<PagedList<TvShowEntity>> { favTvShows ->
        if (favTvShows != null) {
            tvShowAdapter.submitList(favTvShows)
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val tvShowEntity = tvShowAdapter.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavTvShow(it) }

                val snackBar = Snackbar.make(requireView(), R.string.msg_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.msg_ok) { _ ->
                    tvShowEntity?.let { viewModel.setFavTvShow(it) }
                }
                snackBar.show()
            }
        }
    })

    override fun onDestroy() {
        super.onDestroy()
        _fragmentTvshowFavBinding = null
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, id)
            putExtra(DetailActivity.EXTRA_CATEGORY, TV_SHOW)
        }
        context?.startActivity(intent)
    }
}