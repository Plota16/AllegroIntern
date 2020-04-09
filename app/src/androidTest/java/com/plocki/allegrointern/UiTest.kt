package com.plocki.allegrointern

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.plocki.allegrointern.recycler.ViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    var intentsRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun onRecyclerClick(){

        //wait for data to be downloaded
        var isDataDownloaded = false
        while (!isDataDownloaded){
            if (viewIsDisplayed(R.id.recyclerView)){
                isDataDownloaded = true
            }
            else{
                Thread.sleep(100)
            }
        }

        //iterate trough some offers
        for(position : Int in 0..5){
            Intents.init()

            //Get Recycler's item at pointed position name and price
            val recyclerView = intentsRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
            val item = recyclerView.findViewHolderForLayoutPosition(position) as ViewHolder
            val itemPrice = item.offerPrice.text.toString()
            val itemName = item.offerName.text.toString()

            //Click Recycler view'sitem at pointed position
            onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position,click()))

            //Check if DetailActivity is Displayed
            intended(hasComponent(DetailActivity::class.java.name))


            //Check if price and offer name from clicked item matches detail activity price and name TextViews
            onView(withId(R.id.detail_price)).check(matches(withText(itemPrice)))
            onView(withId(R.id.toolbar_title)).check(matches(withText(itemName)))

            //back to main activity
            onView(withId(R.id.toolbar_image)).perform(click())

            Intents.release()
        }


    }

    private fun viewIsDisplayed(viewId: Int): Boolean {
        val isDisplayed = booleanArrayOf(true)
        onView(withId(viewId)).withFailureHandler { _, _ -> isDisplayed[0] = false }
            .check(matches(isDisplayed()))
        return isDisplayed[0]
    }

}



