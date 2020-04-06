package com.plocki.allegrointern

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.plocki.allegrointern.model.ApiResponse
import com.plocki.allegrointern.model.Offer
import com.plocki.allegrointern.recycler.ListAdapter
import com.plocki.allegrointern.services.OfferService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    private var context : Context?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        downloadOffers()



    }


    private fun loadRecycler(offerList : ApiResponse){
        progressView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(context)

        val restrictedData = restrictData(offerList)
        recyclerView.adapter = ListAdapter(restrictedData,context!!)
    }

    private fun restrictData(offerList : ApiResponse) : ArrayList<Offer>{
        val oldData = offerList.offers
        val sortedData = oldData.sortedBy { it.price!!.amount }
        val newData = ArrayList<Offer>()
        for(offer: Offer in sortedData){
            if(offer.price!!.amount!!.toDouble() in 50.0..1000.0){
                newData.add(offer)
            }
        }
        return newData
    }


    private fun showErrorDialog(errorMessage: String?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_dialog_title)
            .setMessage(errorMessage)
            .setPositiveButton(R.string.error_dialog_try_again_button) { _, _ -> downloadOffers() }
            .setNegativeButton(R.string.error_dialog_cancel_button) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun downloadOffers(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://private-987cdf-allegromobileinterntest.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OfferService::class.java)
        val call = service.getOffers()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.code() == 200) {
                    val offerList = response.body()!!
                    loadRecycler(offerList)
                }
                else{
                    showErrorDialog("Błąd ${response.code()}")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                showErrorDialog(t.localizedMessage)
            }
        })
    }
}
