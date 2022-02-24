package com.example.jetnews.ui.chat

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
//import com.pubnub.components.chat.ui.component.channel.ChannelList
import com.pubnub.components.chat.ui.component.input.MessageInput
import com.pubnub.components.chat.ui.component.input.renderer.AnimatedTypingIndicatorRenderer
import com.pubnub.components.chat.ui.component.member.MemberList
import com.pubnub.components.chat.ui.component.message.MessageList
import com.pubnub.components.chat.ui.component.message.MessageUi
import com.pubnub.components.chat.ui.component.presence.Presence
import com.pubnub.components.chat.ui.component.provider.LocalChannel
//import com.pubnub.components.chat.viewmodel.channel.ChannelViewModel
import com.pubnub.components.chat.viewmodel.member.MemberViewModel
import com.pubnub.components.chat.viewmodel.message.MessageViewModel
import com.pubnub.framework.data.ChannelId
import com.pubnub.framework.data.UserId
import kotlinx.coroutines.flow.Flow

object Chat {

    @Composable
    internal fun Content(
        messages: Flow<PagingData<MessageUi>>,
        presence: Presence? = null,
        onMemberSelected: (UserId) -> Unit = {},
    ) {
        val localFocusManager = LocalFocusManager.current
        //Obtain list of members.

        //val memberViewModel: MemberViewModel = MemberViewModel.default() //similar to chat.kt members.
        //val members = memberViewModel.getList()
        //Log.d("Member size", members.size.toString())
        //Log.d("First member", members[0].id)

        //obtain list of channels.
        //val channelViewModel: ChannelViewModel = ChannelViewModel.default(resources = Resources.)
        //val channels = channelViewModel.getList()
        //Log.d("Channel size", channels.size.toString())

        Column(
            modifier = Modifier.fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                }
        ) {
            MessageList(
                messages = messages,
                presence = presence,
                onMemberSelected = onMemberSelected,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, true),
            )

            MessageInput(
                typingIndicator = true,
                typingIndicatorRenderer = AnimatedTypingIndicatorRenderer,
            )

            //MemberList(
             //   members = members,
            //)
        }
    }

    @Composable
    fun View(channelId: ChannelId) {
        // region Content data
        val messageViewModel: MessageViewModel = MessageViewModel.defaultWithMediator(channelId)
        val messages = remember { messageViewModel.getAll() }
        // endregion

        CompositionLocalProvider(
            LocalChannel provides channelId
        ) {

            Scaffold(
                content = {
                    Content(
                        messages = messages,
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun ChatPreview() {
    Chat.View("dc523f0ed25c")
}