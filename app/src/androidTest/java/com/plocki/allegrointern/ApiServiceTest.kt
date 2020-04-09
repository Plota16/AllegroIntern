package com.plocki.allegrointern

import androidx.test.platform.app.InstrumentationRegistry
import com.plocki.allegrointern.services.OfferService
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection

class ApiServiceTest {

    private var mockWebServer = MockWebServer()

    private lateinit var apiService: OfferService

    @Before
    fun setup() {
        mockWebServer.start()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://private-987cdf-allegromobileinterntest.apiary-mock.com/")
//            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //Call service
        apiService = retrofit.create(OfferService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testApi() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readContentFromFilePath())
        mockWebServer.enqueue(response)


        val offers = apiService.getOffers().execute()

        assertTrue(offers.isSuccessful)
        assertTrue(offers.body()!!.offers.size > 0)

    }

    private fun readContentFromFilePath(): String {
            val inputStream: InputStream = InstrumentationRegistry.getInstrumentation().context.assets.open("test.json")
        return inputStream.bufferedReader().use {it.readText()}
    }

}