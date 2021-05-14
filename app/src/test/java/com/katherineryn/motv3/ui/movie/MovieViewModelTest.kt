package com.katherineryn.motv3.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.katherineryn.motv3.data.MotvRepository
import org.junit.Rule
import org.mockito.Mock

class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var motvRepository: MotvRepository


}