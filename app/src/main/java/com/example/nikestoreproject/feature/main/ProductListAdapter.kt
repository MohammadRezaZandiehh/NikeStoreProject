package com.example.nikestoreproject.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestoreproject.R
import com.example.nikestoreproject.common.formatPrice
import com.example.nikestoreproject.common.implementSpringAnimationTrait
import com.example.nikestoreproject.data.Product
import com.example.nikestoreproject.services.ImageLoadingService
import com.sevenlearn.nikestore.view.NikeImageView

class ProductListAdapter(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var products = ArrayList<Product>()
    var onProductOnClickListener: OnProductClickListener? = null


        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
        val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        val previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)

        fun bindProduct(product: Product) {
            imageLoadingService.load(productIv, product.image)
            titleTv.text = product.title
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                onProductOnClickListener?.onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size




    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }
}

/*
  var products = ArrayList<Product>()
* set(value) {
      field = value
      notifyDataSetChanged()
}
*  1) vaghti safe bala miad khali roosh set she ,
   2) vaghti daryaft shod miad meghdaresho avaz mikone ,
   3) yani har vaght meghadaresh avaz shod biad ye notify i anjam bede .
* */