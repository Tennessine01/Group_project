package com.ptit.englishapp.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ptit.englishapp.R;
import com.ptit.englishapp.model.Article;
import com.ptit.englishapp.realtimedatabase.FirebaseHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CreateVoteActivity extends AppCompatActivity {

    private static final String TAG = "To";

    private Button bt;
    private Button btCreate;
    private EditText title;
    private EditText content;
    private ImageView img;
    private Uri imageUri;

    private FirebaseHelper firebaseHelper = new FirebaseHelper();

    private String saveCurDate, saveCurTime;

    private String downloadImgUrl, randomKey;

    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vote);
        bt = findViewById(R.id.btChooseFile);
        img = findViewById(R.id.uploadImage);
        btCreate = findViewById(R.id.btCreate);
        title = findViewById(R.id.titleArticleCreate);
        content = findViewById(R.id.contentArticleCreate);


        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(CreateVoteActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        if (imageUri == null) {
            Toast.makeText(CreateVoteActivity.this, "Không chọn ảnh à bạn ơi?", Toast.LENGTH_SHORT).show();
        } else if (title.getText().toString().isEmpty() || content.getText().toString().isEmpty()) {
            Toast.makeText(this, "Không được để trống title hoặc content", Toast.LENGTH_SHORT).show();
        } else {
            uploadFile();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(img);
            img.setImageURI(imageUri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile() {
        if (imageUri != null) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat curDate = new SimpleDateFormat("dd-MM-yyyy");
            saveCurDate = curDate.format(c.getTime());
            SimpleDateFormat curTime = new SimpleDateFormat("HH:mm:ss");
            saveCurTime = curTime.format(c.getTime());
            randomKey = saveCurDate + "-" + saveCurTime;
            StorageReference filePath = storageRef.child(
                    imageUri.getLastPathSegment() + randomKey + ".jpg");
            final UploadTask uploadTask = filePath.putFile(imageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(CreateVoteActivity.this,
                            "error: " + message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CreateVoteActivity.this,
                            "up anh thanh cong!", Toast.LENGTH_SHORT).show();
                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            downloadImgUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            downloadImgUrl = task.getResult().toString();
                            Toast.makeText(CreateVoteActivity.this,
                                    "Luu Url anh thanh cong!", Toast.LENGTH_SHORT).show();
                            saveArticleToDatabase();
                        }
                    });
                }
            });


        }
    }

    private void saveArticleToDatabase() {
//        HashMap<String, Object> work = new HashMap<>();
//        work.put("id", TAG + randomKey);
//        work.put("title", title);
//        work.put("content", content);
//        work.put("img", downloadImgUrl);
//        databaseRef.child("article").child(TAG + randomKey).updateChildren(work)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(CreateVoteActivity.this, "Tạo bài thành công", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(CreateVoteActivity.this,
//                                    "Tạo bài không thành công xin vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        Article article = new Article();
        article.setCare(0);
        article.setType(1);
        article.setUrl(downloadImgUrl);
        article.setTime(new Date().getTime());
        article.setTitle(title.getText().toString());
        article.setContent(content.getText().toString());

        firebaseHelper.createArticle(this, article);
    }
}