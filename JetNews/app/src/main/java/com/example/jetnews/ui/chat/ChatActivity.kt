package com.example.jetnews.ui.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.jetnews.BuildConfig
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.enums.PNLogVerbosity
import com.pubnub.components.chat.provider.LocalMemberRepository
import com.pubnub.components.chat.provider.LocalMembershipRepository
import com.pubnub.components.chat.ui.component.provider.LocalChannel
import com.pubnub.components.chat.viewmodel.message.MessageViewModel
import com.pubnub.components.data.member.DBMember
import com.pubnub.components.data.membership.DBMembership
import com.example.jetnews.ui.theme.ChatAppTheme
import com.pubnub.components.repository.member.DefaultMemberRepository
import com.pubnub.components.repository.membership.DefaultMembershipRepository
import com.pubnub.framework.data.ChannelId
import kotlinx.coroutines.launch

class ChatActivity : ComponentActivity() {

    private lateinit var pubNub: PubNub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePubNub()
        var channelId = "default-article"; //default channel name
        setContent {
            ChatAppTheme(pubNub = pubNub) {
                AddDummyData(channelId)
                Box(modifier = Modifier.fillMaxSize()) {
                    ChannelView(id = channelId)
                }
            }
        }
    }

    override fun onDestroy() {
        destroyPubNub()
        super.onDestroy()
    }

    private fun initializePubNub(){
        pubNub  = PubNub(
            PNConfiguration(uuid = getRandomString(6)).apply {
                publishKey = BuildConfig.PUBLISH_KEY
                subscribeKey = BuildConfig.SUBSCRIBE_KEY
            }
        )
    }

    //Generates random username based on length of integer. Taken from https://stackoverflow.com/questions/46943860/idiomatic-way-to-generate-a-random-alphanumeric-string-in-kotlin
    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun destroyPubNub(){
        pubNub.destroy()
    }

    // Channel view
    @Composable
    fun ChannelView(id: ChannelId) {
        // region Content data
        val messageViewModel: MessageViewModel = MessageViewModel.defaultWithMediator(id)
        val messages = remember { messageViewModel.getAll() }
        // endregion

        CompositionLocalProvider(LocalChannel provides id) {
            Chat.Content(
                messages = messages,
            )
        }
    }

    @Composable
    fun AddDummyData(vararg channelId: ChannelId) {

        // Creates a user object with uuid
        val memberRepository: DefaultMemberRepository = LocalMemberRepository.current
        val member: DBMember = DBMember(id = pubNub.configuration.uuid, name = pubNub.configuration.uuid, profileUrl = "https://picsum.photos/seed/${pubNub.configuration.uuid}/200")

        // Creates a membership so that the user could subscribe to channels
        val membershipRepository: DefaultMembershipRepository = LocalMembershipRepository.current
        val memberships: Array<DBMembership> = channelId.map { id -> DBMembership(channelId = id, memberId = member.id) }.toTypedArray()

        // Fills the database with member and memberships data
        val scope = rememberCoroutineScope()
        LaunchedEffect(null) {
            scope.launch {
                memberRepository.add(member)
                membershipRepository.add(*memberships)
            }
        }
    }
}
