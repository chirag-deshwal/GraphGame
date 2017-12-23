package com.example.choo827.graphgame;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotiService extends FirebaseMessagingService {
	private static final String TAG = "MyFMService";
	public static final String NOTI_MESSAGE = "noti_message";

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		String noti = remoteMessage.getData().get(NOTI_MESSAGE);
		Intent newNoti = new Intent(this, MainActivity.class);
		newNoti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		newNoti.putExtra(NOTI_MESSAGE, noti);
		startActivity(newNoti);
	}
}
