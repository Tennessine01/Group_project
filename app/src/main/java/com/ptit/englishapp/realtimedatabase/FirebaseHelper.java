package com.ptit.englishapp.realtimedatabase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.englishapp.model.Article;
import com.ptit.englishapp.model.Vote;
import com.ptit.englishapp.model.WordRecent;
import com.ptit.englishapp.model.YourWord;
import com.ptit.englishapp.realtimedatabase.model.EndDate;
import com.ptit.englishapp.realtimedatabase.model.Login;
import com.ptit.englishapp.realtimedatabase.model.Payment;
import com.ptit.englishapp.realtimedatabase.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class FirebaseHelper {


    private DatabaseReference mDatabase;

    public FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void addLogPayment(Payment payment) {
        DatabaseReference paymentRef = mDatabase.child("payment");

        String id = paymentRef.push().getKey();
        payment.setId(id);

        paymentRef.child(payment.getUid()).child(id).setValue(payment);
    }

    public void createVote(Context context, Vote vote) {
        DatabaseReference voteRef = mDatabase.child("vote");
        String id = voteRef.push().getKey();
        vote.setId(id);
        voteRef.child(vote.getUid()).setValue(vote).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Vote thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Có lỗi xảy ra khi vote", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addLogCareArticle(Article article) {
        DatabaseReference careRef = mDatabase.child("article");
        article.setCare(article.getCare() + 1);
        careRef.child(article.getId()).setValue(article);
    }

    public void uploadUserProfile(Context context, User user) {
        DatabaseReference userRef = mDatabase.child("user_profile");
        userRef.child(user.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Upload profile thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Có lỗi xảy ra khi upload profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createArticle(Context context, Article article) {
        DatabaseReference articleRef = mDatabase.child("article");
        String id = articleRef.push().getKey();
        article.setId(id);
        article.setCare(0);
        articleRef.child(id).setValue(article).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Tạo bài thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Có lỗi xảy ra khi tạo bài", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void extendFeature(Payment payment, Context context) { // gia hạn thời gian
        DatabaseReference extendRef = mDatabase.child("extend_feature");

        extendRef.child(payment.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EndDate endDate = snapshot.getValue(EndDate.class);
                if (endDate != null) {
                    int month = getTimeExtend(payment.getAmount());
                    if (month == -1) {
                        endDate.setNeverEnd(true);
                    } else {
                        endDate.setNeverEnd(false);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date(endDate.getDateEnd()));
                        calendar.add(Calendar.MONTH, month);
                        endDate.setDateEnd(calendar.getTime().getTime());
                    }

                    extendRef.child(payment.getUid()).setValue(endDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(
                                    context,
                                    "Gia hạn thành công gói " + ((month == -1) ? "vĩnh viễn" : month + " tháng")
                                    , Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                } else {
                    EndDate endDate1 = new EndDate();
                    int month = getTimeExtend(payment.getAmount());
                    if (month == -1) {
                        endDate1.setNeverEnd(true);
                    } else {
                        endDate1.setNeverEnd(false);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.MONTH, month);
                        endDate1.setDateEnd(calendar.getTime().getTime());
                    }

                    extendRef.child(payment.getUid()).setValue(endDate1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(
                                    context,
                                    "Gia hạn thành công gói " + ((month == -1) ? "vĩnh viễn" : month + " tháng")
                                    , Toast.LENGTH_SHORT
                            ).show();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int getTimeExtend(Double price) {
        if (price <= 300000) {
            return 3;
        } if (price <= 500000) {
            return 12;
        }
        return -1; // vinh vien

    }

    public void addLoginData(String uuid) {
        DatabaseReference loginRef = mDatabase.child("login");

        String id = loginRef.push().getKey();
        Login login = new Login(id, uuid, new Date().getTime());

        loginRef.child(id).setValue(login);
    }

    public void addRecentData(String uuid, String word, int typeApp) {
        DatabaseReference recentRef = mDatabase.child("word_recent");
        String id = recentRef.push().getKey();
        WordRecent wordRecent = new WordRecent(word, typeApp, uuid, new Date());
        recentRef.child(uuid).child(id != null ? id : String.valueOf(new Random().nextLong())).setValue(wordRecent);
    }

    public void addYourWord(String uid, YourWord yourWord) {
        DatabaseReference yourWordRef = mDatabase.child("your_word").child(uid)
                .child(yourWord.getId().toString());
        yourWordRef.setValue(yourWord);
    }

    public void updateYourWord(String uid, String yourWordId, YourWord updatedYourWord) {
        DatabaseReference yourWordRef = mDatabase.child("your_word").child(uid).child(yourWordId);
        yourWordRef.setValue(updatedYourWord);
    }

    public void deleteYourWord(String uid, String yourWordId) {
        DatabaseReference yourWordRef = mDatabase.child("your_word").child(uid).child(yourWordId);
        yourWordRef.removeValue();
    }
}
