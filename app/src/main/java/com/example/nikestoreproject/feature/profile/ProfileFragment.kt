package com.example.nikestoreproject.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.NikeFragment
import com.example.nikestoreproject.feature.auth.AuthActivity
import com.example.nikestoreproject.feature.favourite.FavouriteProductActivity
import com.example.nikestoreproject.feature.order.OrderHistoryActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject


class ProfileFragment : NikeFragment() {
    private val viewModel: ProfileViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), FavouriteProductActivity::class.java))
        }

        orderHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistoryActivity::class.java))
        }

/*        orderHistoryBtn.setOnClickListener {
            startActivity(Intent(context, OrderHistoryActivity::class.java))
        }*/
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (viewModel.isSignedIn) {
            authBtn.text = getString(R.string.signOut)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sign_in, 0, 0, 0)
            usernameTv.text = viewModel.username
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthState()
            }
        } else {
            authBtn.text = getString(R.string.signIn)
            authBtn.setOnClickListener {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
            authBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sign_in, 0, 0, 0)
            usernameTv.text = getString(R.string.guest_user)

        }
    }
}