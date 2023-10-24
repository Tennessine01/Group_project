package com.ptit.englishapp.firestore;

import static com.ptit.englishapp.notify.MyFirebaseMessagingService.USERS_COLLECTION;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStoreHelper {
    public FireStoreHelper() {
    }

    public static void registerNotifyReminder(Context context, String uid, boolean register, int hour, int minute) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(USERS_COLLECTION).document(uid);
        Map<String, Object> map = new HashMap<>();
        map.put("register", register);
        map.put("hour", hour);
        map.put("minute", minute);
        docRef.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (register) {
                    Toast.makeText(context, "Đăng ký nhận thông báo thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Hủy đăng ký nhận thông báo thành công", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (register) {
                    Toast.makeText(context, "Có lỗi xảy ra khi đăng ký nhận thông báo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Có lỗi xảy ra khi hủy đăng ký nhận thông báo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
