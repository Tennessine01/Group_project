package com.ptit.englishapp.notify;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ptit.englishapp.MainActivity;
import com.ptit.englishapp.R;
import com.ptit.englishapp.firestore.UserToken;
import com.ptit.englishapp.utils.Commons;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String USERS_COLLECTION = "users";

    private static final String TAG = "MyFirebaseMsgService";

    public MyFirebaseMessagingService() {
    }

    private UserToken userToken = new UserToken();
    private int hour = 0, minute = 0;
    private boolean register = true, isSend;


    public static void registerNotify(String uid, int hour, int minute, boolean register) {
        UserToken userToken = new UserToken();
        userToken.setUserId(uid);
        userToken.setHour(hour);
        userToken.setMinute(minute);
        userToken.setRegister(register);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("test", 1);

        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(token -> {
                    userToken.setFcmToken(token);
                    // Cập nhật token FCM lên Firestore
                    DocumentReference docRef = db.collection(USERS_COLLECTION).document(uid);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    updateFCMToken(uid);
                                } else {
                                    FirebaseFirestore.getInstance()
                                            .collection(USERS_COLLECTION)
                                            .document(uid)
                                            .set(userToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    System.out.println("Token updated successfully for user: " + uid);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.println("Failed to update token for user: " + uid);
                                                }
                                            });
                                    System.out.println("Token: " + token);
                                }
                            } else {
                                System.out.println("Failed to get FCM token for user: " + uid);
                            }
                        }
                    });

                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }

    public static void updateFCMToken(String userId) {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(token -> {
                    // Cập nhật token FCM lên Firestore
                    FirebaseFirestore.getInstance()
                            .collection(USERS_COLLECTION)
                            .document(userId)
                            .update("fcmToken", token)
                            .addOnSuccessListener(aVoid -> {
                                // Cập nhật thành công
                                System.out.println("Token updated successfully for user: " + userId);
                            })
                            .addOnFailureListener(e -> {
                                // Xử lý lỗi
                                System.out.println("Failed to update token for user: " + userId);
                                e.printStackTrace();
                            });
                    System.out.println("Token: " + token);
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi
                    System.out.println("Failed to get FCM token for user: " + userId);
                    e.printStackTrace();
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            // Xử lý dữ liệu payload (tuỳ chỉnh)
//            String title = remoteMessage.getData().get("title");
//            String message = remoteMessage.getData().get("message");
//            // Tiến hành xử lý thông báo hoặc cập nhật giao diện tại đây
//            sendNotification(title, message);
//        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // Xử lý thông báo (nếu có)
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            // Tiến hành xử lý thông báo hoặc cập nhật giao diện tại đây
            sendNotification(title, body);

        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection(USERS_COLLECTION).document();


            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userToken = documentSnapshot.toObject(UserToken.class);
                    hour = userToken.getHour();
                    minute = userToken.getMinute();
                    Date a = new Date();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


            if (/* Check if data needs to be processed by long running job */ true) {

            } else {
                // Handle message within 10 seconds
//                handleNow();
            }

            sendNotification(remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("message"));

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_notify_small)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
