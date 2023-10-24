package com.ptit.englishapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ptit.englishapp.MainActivity;
import com.ptit.englishapp.R;
import com.ptit.englishapp.realtimedatabase.FirebaseHelper;
import com.ptit.englishapp.realtimedatabase.model.Payment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HandlerResultPaymentActivity extends AppCompatActivity {

    TextView info;

    Button backHome;

    FirebaseHelper firebaseHelper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_result_payment);
        info = findViewById(R.id.infoPayment);
        backHome = findViewById(R.id.backHome);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HandlerResultPaymentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        Uri data = intent.getData();
        String vnp_Amount = data.getQueryParameter("vnp_Amount");
        String vnp_OrderInfo = data.getQueryParameter("vnp_OrderInfo");
        String vnp_ResponseCode = data.getQueryParameter("vnp_ResponseCode");
        String uid = data.getQueryParameter("uid");
        String date = data.getQueryParameter("vnp_PayDate");

        if (vnp_ResponseCode != null && vnp_ResponseCode.equals("00")) {
            info.setText(
                    "Thanh toán thành công \n" +
                    "Mã người thanh toán: " + uid + "\n" +
                    "Số tiền: " + Double.parseDouble(vnp_Amount) / 100 + " đồng \n" +
                    "Thông tin: " + vnp_OrderInfo + "\n" +
                    "Thời gian: " + formatDate(date)
            );
            Payment payment = new Payment(true, uid, Double.parseDouble(vnp_Amount) / 100, new Date().getTime());
            firebaseHelper.addLogPayment(payment);

            firebaseHelper.extendFeature(payment, this);

        } else {
            info.setText("Thanh toán chưa thành công\n" +
                    "Xin vui lòng thực hiện lại");
            Payment payment = new Payment(false, uid, Double.parseDouble(vnp_Amount) / 100, new Date().getTime());
            firebaseHelper.addLogPayment(payment);
        }
    }

    private String formatDate(String s) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
}