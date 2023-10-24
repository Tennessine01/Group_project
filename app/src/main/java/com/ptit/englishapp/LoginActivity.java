package com.ptit.englishapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.englishapp.app.ChartActivity;
import com.ptit.englishapp.app.CreateVoteActivity;
import com.ptit.englishapp.notify.MyFirebaseMessagingService;
import com.ptit.englishapp.realtimedatabase.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_REGISTER = 10000;
    private final static int REQUEST_CODE_LOGIN_GOOGLE = 10001;
    Button btnLogin;
    EditText txtEmail, txtPassword;
    TextView resetPassword, register;

    ImageView googleBtn;

    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;

    FirebaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        initView();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //test send
        helper = new FirebaseHelper();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("We are checking account");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("400593358302-m84u1fdgtjor20e3ham5ohrqqivrpb0f.apps.googleusercontent.com")
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signInWithEmailAndPassword(
                                txtEmail.getText().toString(), txtPassword.getText().toString()).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser currentUser = auth.getCurrentUser();
                                if (currentUser != null) {
                                    helper.addLoginData(currentUser.getUid());
                                }
                                MyFirebaseMessagingService.registerNotify(
                                        authResult.getUser().getUid(),
                                        9, 0, false
                                );
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this,
                                        MainActivity.class);
//                                Intent intent = new Intent(LoginActivity.this,
//                                        CreateVoteActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,
                                        "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.sendPasswordResetEmail(txtEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "An email has been sent to you.", Toast.LENGTH_LONG).show();
                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REGISTER);
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, REQUEST_CODE_LOGIN_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String email = data.getStringExtra("email");
                final String password = data.getStringExtra("pass");
                //Set lại giá trị cho txtEmail and password
                txtEmail.setText(email);
                txtPassword.setText(password);
            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }

        if (requestCode == REQUEST_CODE_LOGIN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("LoginActivity", "Google sign-in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (account != null) {
                        String uid = auth.getCurrentUser().getUid();
                        helper.addLoginData(uid);
                        MyFirebaseMessagingService.registerNotify(
                                uid,
                                9, 0, false
                        );
                    } else {
                        Toast.makeText(LoginActivity.this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(LoginActivity.this, "Đăng nhập google thành công", Toast.LENGTH_SHORT).show();
                    Log.d("LoginActivity", "signInWithCredential: success");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w("LoginActivity", "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navigateToMainActivity() {
        finish();
        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(intent);
    }

    private void initView() {
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.inputEmail);
        txtPassword = findViewById(R.id.inputPassword);
        resetPassword = findViewById(R.id.forgotPassword);
        register = findViewById(R.id.gotoRegister);
        googleBtn = findViewById(R.id.googleLogin);
    }
}