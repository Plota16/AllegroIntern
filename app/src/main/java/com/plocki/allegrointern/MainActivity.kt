package com.plocki.allegrointern


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        //Setup custom ActionBar
        setupActionBar()

        //Download offers From API
        downloadOffers()

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
            ));
        context = this
    }

    private fun downloadOffers(){

        //Create retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://private-987cdf-allegromobileinterntest.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Call service
        val service = retrofit.create(OfferService::class.java)
        val call = service.getOffers()

        //Handle service response
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.code() == 200) {
                    //Load response and setup RecyclerView
                    val offerList = response.body()!!
                    loadRecycler(offerList)
                }
                else{
                    //Show Error Dialog with Error Code
                    showErrorDialog("Błąd ${response.code()}")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                //Show Error Dialog with failure message
                showErrorDialog(t.localizedMessage)
            }
        })
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

    private fun loadRecycler(offerList : ApiResponse){

        //Hide progress bar
        progressView.visibility = View.GONE

        //Show Recycler View
        recyclerView.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(context)
        val oldData = offerList.offers

        //Sort offers by price amount
        val sortedData = oldData.sortedBy { it.price!!.amount }

        //Restrict offers
        val restrictedData = restrictData(sortedData)

        //Apply adapter
        recyclerView.adapter = ListAdapter(restrictedData,context!!)
    }

    fun restrictData(offerList : List<Offer>) : ArrayList<Offer>{

        val newData = ArrayList<Offer>()
        for(offer: Offer in offerList){
            if(offer.price!!.amount!!.toDouble() in 50.0..1000.0){
                newData.add(offer)
            }
        }
        return newData
    }
}
