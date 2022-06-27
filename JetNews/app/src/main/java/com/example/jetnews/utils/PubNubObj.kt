package com.example.jetnews.utils

import com.example.jetnews.BuildConfig
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub

/*
Singleton Class Used to house single instance of the PubNub object to be used across
all Activities.
 */


class PubNubObj {
    companion object PubNubObj {
        private val pubNub : PubNub = PubNub(
            PNConfiguration(uuid = getRandomString(6)).apply {
                publishKey = BuildConfig.PUBLISH_KEY
                subscribeKey = BuildConfig.SUBSCRIBE_KEY
            }
        )
        //Generates random username based on length of integer. Taken from https://stackoverflow.com/questions/46943860/idiomatic-way-to-generate-a-random-alphanumeric-string-in-kotlin
        private fun getRandomString(length: Int) : String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }

    //Return the instance of the PubNub companion object to be used across all instances.
    fun getInstance() : PubNub {
        return pubNub
    }
}