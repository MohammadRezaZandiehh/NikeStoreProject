package com.example.nikestoreproject.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.EXTRA_KEY_ID
import com.example.nikestoreproject.data.Comment
import com.example.nikestoreproject.feature.product.CommentAdapter
import com.example.nikestoreproject.common.NikeActivity
import com.sevenlearn.nikestore.feature.product.comment.CommentListViewModel
import kotlinx.android.synthetic.main.activity_comment_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    private val commentListViewModel: CommentListViewModel by viewModel { parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        commentListViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        commentListViewModel.commentsLiveData.observe(this) {
            val adapter = CommentAdapter(true)
            commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter.comments = it as ArrayList<Comment>
            commentsRv.adapter = adapter
        }

        commentListToolbar.onBackButtonClickListener= View.OnClickListener {
            finish()
        }
    }
}