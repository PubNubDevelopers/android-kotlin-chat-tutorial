package com.example.jetnews.utils

import com.example.jetnews.BuildConfig
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import java.util.*

/*
Singleton Class Used to house single instance of the PubNub object to be used across
all Activities.
 */


class PubNubObj {
    companion object PubNubObj {
        private val pubNub : PubNub = PubNub(
            PNConfiguration(uuid = UUID.randomUUID().toString().substring(0,5)).apply {
                publishKey = BuildConfig.PUBLISH_KEY
                subscribeKey = BuildConfig.SUBSCRIBE_KEY
            }
        )
    }

    //Return the instance of the PubNub companion object to be used across all instances.
    fun getInstance() : PubNub {
        return pubNub
    }
}