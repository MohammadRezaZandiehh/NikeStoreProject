package com.example.nikestoreproject.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_DATA
import com.example.nikestoreproject.feature.common.ProductListAdapter
import com.example.nikestoreproject.feature.common.VIEW_TYPE_LARGE
import com.example.nikestoreproject.feature.common.VIEW_TYPE_SMALL
import com.example.nikestoreproject.feature.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.nikestoreproject.common.NikeActivity
import com.example.nikestoreproject.data.model.Product
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity(), ProductListAdapter.ProductEventListener {

    val viewModel: ProductListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }
    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val gridLayoutManager = GridLayoutManager(this, 2)
        productsRv.layoutManager = gridLayoutManager
        productsRv.adapter = productListAdapter
        productListAdapter.productEventListener = this
        viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            } else {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }


        viewModel.selectedSortTitleLiveData.observe(this) {
            selectedSortTitleTv.text = getString(it)
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }


        viewModel.productsLiveData.observe(this) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }


        toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }


        sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortTitlesArray, viewModel.sort
                ) { dialog, selectedSortIndex ->
                    dialog.dismiss()
                    viewModel.onSelectedSortChangedByUser(selectedSortIndex)
                }.setTitle(getString(R.string.sort))
            dialog.show()

        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onFavouriteBtnClick(product: Product) {
        TODO("Not yet implemented")
    }
}