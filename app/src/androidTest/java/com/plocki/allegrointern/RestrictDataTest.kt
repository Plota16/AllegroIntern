package com.plocki.allegrointern

import androidx.test.rule.ActivityTestRule
import com.plocki.allegrointern.model.Offer
import com.plocki.allegrointern.model.Price
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class RestrictDataTest {

    @get:Rule
    var intentsRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private val offerToLowPrice = Offer()
    private val offerGoodPrice1 = Offer()
    private val offerGoodPrice2 = Offer()
    private val offerGoodPrice3 = Offer()
    private val offerToHighPrice = Offer()

    @Before
    fun init() {
        val priceToLow = Price()
        priceToLow.amount = 10.0
        offerToLowPrice.price = priceToLow
        val priceGood1 = Price()
        priceGood1.amount = 50.0
        offerGoodPrice1.price = priceGood1
        val priceGood2 = Price()
        priceGood2.amount = 200.0
        offerGoodPrice2.price = priceGood2
        val priceGood3 = Price()
        priceGood3.amount = 1000.0
        offerGoodPrice3.price = priceGood3
        val priceToHigh = Price()
        priceToHigh.amount = 2000.0
        offerToHighPrice.price = priceToHigh
    }

    @Test
    fun restrictDataAllGood(){
        val mainActivity = intentsRule.activity

        val originalArray =  arrayListOf(offerGoodPrice1,offerGoodPrice2,offerGoodPrice3)
        val restrictedArray = arrayListOf(offerGoodPrice1,offerGoodPrice2,offerGoodPrice3)

        assertTrue(restrictedArray == mainActivity.restrictData(originalArray))
    }

    @Test
    fun restrictDataWithToLow(){
        val mainActivity = intentsRule.activity

        val originalArray =  arrayListOf(offerToLowPrice,offerGoodPrice1,offerGoodPrice3)
        val restrictedArray = arrayListOf(offerGoodPrice1,offerGoodPrice3)

        assertTrue(restrictedArray == mainActivity.restrictData(originalArray))
    }

    @Test
    fun restrictDataWithToHigh(){
        val mainActivity = intentsRule.activity

        val originalArray =  arrayListOf(offerGoodPrice2,offerGoodPrice3,offerToHighPrice)
        val restrictedArray = arrayListOf(offerGoodPrice2,offerGoodPrice3)

        assertTrue(restrictedArray == mainActivity.restrictData(originalArray))
    }

    @Test
    fun restrictDataWithBoth(){
        val mainActivity = intentsRule.activity

        val originalArray =  arrayListOf(offerToHighPrice, offerGoodPrice3,offerToLowPrice,offerGoodPrice1,offerGoodPrice2)
        val restrictedArray = arrayListOf(offerGoodPrice3,offerGoodPrice1,offerGoodPrice2)

        assertTrue(restrictedArray == mainActivity.restrictData(originalArray))
    }
}