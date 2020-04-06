package com.plocki.allegrointern

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.plocki.allegrointern.model.ApiResponse
import com.plocki.allegrointern.recycler.ListAdapter
import com.plocki.allegrointern.services.OfferService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var offerList = ApiResponse()
    private var context : Context?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        downloadOffers()



    }



    private fun showError(errorMessage: String?) {
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
                    offerList = response.body()!!
                    progressView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = ListAdapter(offerList,context!!)
                }
                else{
                    showError("Błąd ${response.code()}")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                showError(t.localizedMessage)
            }
        })
    }
}
