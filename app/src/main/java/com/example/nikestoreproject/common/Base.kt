package com.example.nikestoreproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class NikeActivity : AppCompatActivity(), NikeView {
    override val rootView: CoordinatorLayout?
        get() = window.decorView.rootView as CoordinatorLayout
    override val viewContext: Context?
        get() = this
}


abstract class NikeFragment : Fragment(), NikeView {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context
}


interface NikeView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?
    fun setProgressIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.view_loading, it, false)
                    it.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }

}


abstract class NikeViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()

    }
}


/*

* --> in file va class hgaye tooshon ro sakhtim ta masalan agar khastim b hameye fragment ha felan option ro add konim to hamin ja anjam beshan. <--

* abstracy mikonim chon bazi az class ha hastan k chon bazi az fun hashon bayad tavasote farzandaneshoon override shavand.
*
* val rootView: CoordinatorLayout? va     val viewContext: Context?  --->  chera nullable? chon momkene ye vahgt fragment e ma destroy shode bashe va root i nadashte bashe.
*
*/