package com.example.nikestoreproject.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_DATA
import com.example.nikestoreproject.common.NikeCompletableObserver
import com.example.nikestoreproject.data.model.CartItem
import com.example.nikestoreproject.feature.auth.AuthActivity
import com.example.nikestoreproject.feature.product.ProductDetailActivity
import com.example.nikestoreproject.services.ImageLoadingService
import com.example.nikestoreproject.common.NikeFragment
import com.example.nikestoreproject.feature.shipping.ShippingActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.view_cart_empty_state.*
import kotlinx.android.synthetic.main.view_cart_empty_state.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment : NikeFragment(), CartItemAdapter.CartItemViewCallbacks {
    val viewModel: CartViewModel by viewModel()
    val imageLoadingService: ImageLoadingService by inject()
    var cartItemAdapter: CartItemAdapter? = null
    val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        viewModel.cartItemsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())

            cartItemsRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            cartItemAdapter =
                CartItemAdapter(it as MutableList<CartItem>, imageLoadingService, this)
            cartItemsRv.adapter = cartItemAdapter
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())

            cartItemAdapter?.let { adapter ->
                adapter.purchaseDetail = it
                adapter.notifyItemChanged(adapter.cartItems.size)
            }
        }

        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
            if (it.mustShow) {
                val emptyState = showEmptyState(R.layout.view_cart_empty_state)

                emptyState?.let { view ->
                    view.emptyStateMessageTv.text = getString(it.messageRecId)
                    view.emptyStateCtaBtn.visibility =
                        if (it.mustShowCallToActionBtn) View.VISIBLE else View.GONE
                    view.emptyStateCtaBtn.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }
                }
            } else
                emptyStateRootView?.visibility = View.GONE
        }

        payBtn.setOnClickListener {
            val intent = Intent(requireContext(), ShippingActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, viewModel.purchaseDetailLiveData.value)
            }
            startActivity(intent)
        }
    }

    override fun onRemoveCartItemButtonClick(cartItem: CartItem) {
        viewModel.removeItemFromCart(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.removeCartItem(cartItem)
                }
            })
    }

    override fun onIncreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.increaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.increaseCount(cartItem)
                }
            })
    }

    override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.decreaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.decreaseCount(cartItem)
                }
            })
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, cartItem.product)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }
}