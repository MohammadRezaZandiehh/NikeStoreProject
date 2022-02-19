package com.example.nikestoreproject

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class NikeActivity : AppCompatActivity(), NikeView {
    override fun setProgressIndicator(mustShow: Boolean) {

    }
}


abstract class NikeFragment : Fragment(), NikeView {
    override fun setProgressIndicator(mustShow: Boolean) {

    }
}


interface NikeView {
    fun setProgressIndicator(mustShow: Boolean)
}


abstract class NikeViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()

    }
}


/*

* --> in file va class hgaye tooshon ro sakhtim ta masalan agar khastim b hameye fragment ha felan option ro add konim to hamin ja anjam beshan. <--

* abstracy mikonim chon bazi az class ha hastan k chon bazi az fun hashon bayad tavasote farzandaneshoon override shavand.
*
* */