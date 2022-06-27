package com.example.nikestoreproject.eventBusExample

import android.media.metrics.Event
import android.os.Bundle
import android.widget.Toast
import com.example.nikestoreproject.R
import com.sevenlearn.nikestore.common.NikeActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ActivityA:NikeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
    }
 

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMyEvent(myEvent: MyEvent){
        Toast.makeText(this, myEvent.message, Toast.LENGTH_SHORT).show()
    }
}