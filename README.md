# JetNews Application with Chat using PubNub's Chat Components for Android 
This project is the open-source Android Application JetNews integrate with chat using PubNub’s Chat Components for Android. This application was built to showcase how easy it is to add chat to an existing application without the need of massive overhead and managing your own servers. A [detailed tutorial](https://www.pubnub.com/tutorial/simple-chat-app-android-kotlin/) is available for a step-by-step guide on how easy it is to integrate chat into your own application.

The open-source application used is JetNews, which is a sample news reading app built with
[Jetpack Compose](https://developer.android.com/jetpack/compose). 

<img src="JetNews/screenshots/jetnews_pubnub_chat_intro.gif" alt="Screenshot">

## Features

This application contains the base JetNews functionality along with a chat room for each article for users to be able to chat about the article they have just read. Users can view a list of articles, read each article, and chat about these articles with other users.
* Pre-built UI Component that renders and handles displaying the chat room.
* Send messages to other users in the chat room.
* Receive messages from other users in the chat room.
* See past messages from other users in the chat room, even if the current user is offline.
* See typing indicators from users in the [debug console](https://www.pubnub.com/docs/console) and interact with the Android app in real-time.

## Installing / Getting started

Put a short explanation of the steps below and what the developer should accomplish at the end. For example: “For this project we’ll use NPM to install dependencies and run the project. You’ll also need a PubNub account.”

### Requirements
- [Latest Stable Release of Android Studio](https://developer.android.com/studio)
- [PubNub Account](#pubnub-account) (*Free*)

<a href="https://dashboard.pubnub.com/signup">
	<img alt="PubNub Signup" src="https://i.imgur.com/og5DDjf.png" width=260 height=97/>
</a>

### Get Your PubNub Keys
1. Sign in to your [PubNub Dashboard](https://admin.pubnub.com/). You are now in the Admin Portal.
2. Go to Apps on the left hand side of the Portal.
3. Click the Create New App button in the top-right of the Portal.
4. Give your app a name.
5. Click Create.
6. Click your new app to open its settings.
7. When you create a new app, the first set of keys are generated automatically. However, a single app can have as many keysets as you like. PubNub recommends that you create separate keysets for production and test environments.
8. Click on the keyset generated.
9. Enable the Presence feature for your keyset. This will be used to show how many users are active. Enter "ENABLE" in all caps to confirm your choice. Leave the default settings.
10. Enable the Storage and Playback feature for your keyset. Leave the default settings.
11. Click Save Changes on the bottom right of the portal to save your changes.
12. Copy the Publish and Subscribe Keys to a text editor.

### Building and Running
1. Clone the GitHub repository.

	```bash
	git clone https://github.com/PubNubDevelopers/android-kotlin-chat-tutorial.git
	```
2. Open the JetNews Folder in Android Studio.
3. Open the gradle.properties file. 
4. Replace the PUBNUB_PUBLISH_KEY and PUBNUB_SUBSCRIBE_KEY values with your own publish and subscribe keys respectively.
5. Sync the gradle file and run the application.
6. Click on an article to read.
7. Click on the chat message icon on the bottom toolbar to open the chat room.
8. Send and receive messages, and view past messages from other users.
9. Generate new users each time the application is restarted.
10. Receive messages in real-time by interacting with the [debug console](https://www.pubnub.com/docs/console).

## Contributing
Please fork the repository if you'd like to contribute. Pull requests are always welcome. 

## Links

- Project homepage: https://pubnub.com
- Tutorial Link: https://www.pubnub.com/tutorial/simple-chat-app-android-kotlin/
- PubNub Developer Resources: https://www.pubnub.com/developers/ 

## Further Information

Checkout [PubNub Android Chat Docs](https://www.pubnub.com/docs/chat/components/android/get-started-android) page for more information about how to use Android Chat Components in your own Android app.

## License
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
