package com.plocki.allegrointern

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Setup custom ActionBar
        setupActionBar()

        //Get Offer details
        val offerName = this.intent.getStringExtra("name")
        val offerAmount = this.intent.getStringExtra("amount")
        val offerCurrency = this.intent.getStringExtra("currency")
        val offerDescription = this.intent.getStringExtra("description")
        val offerImageUrl = this.intent.getStringExtra("url")

        //Set name in ActionBar Title
        val view = findViewById<TextView>(R.id.toolbar_title)
        view.text = offerName

        //Set offer price

        val price = "${toPolishFormat(offerAmount)} $offerCurrency"
        detail_price.text = price

        //Set description from html code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detail_desc.text = Html.fromHtml(offerDescription, Html.FROM_HTML_MODE_COMPACT)
        } else {
            detail_desc.text = Html.fromHtml(offerDescription)
        }

        //Load image from Url
        Glide.with(this)
            .load(offerImageUrl)
            .into(detail_image)
    }

    private fun toPolishFormat(price : String): String {
        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = ','
        val decimalFormat = DecimalFormat("0.00", symbols)
        return decimalFormat.format(price.toDouble())
    }

    private fun setupActionBar(){
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.toolbar, null)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        supportActionBar!!.setDisplayShowCustomEnabled(true);
        supportActionBar!!.setCustomView(view,
            ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ))
        val backButton = findViewById<ImageButton>(R.id.toolbar_image)
        backButton.visibility = View.VISIBLE
        backButton.setOnClickListener {
            this.finish()
        }

    }
}
