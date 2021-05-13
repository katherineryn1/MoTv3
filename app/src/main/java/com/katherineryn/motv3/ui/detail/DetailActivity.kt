package com.katherineryn.motv3.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.katherineryn.motv3.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CATEGORY = "extra_category"
    }
}