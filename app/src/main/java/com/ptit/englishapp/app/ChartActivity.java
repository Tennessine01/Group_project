package com.ptit.englishapp.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.englishapp.R;
import com.ptit.englishapp.realtimedatabase.model.Login;
import com.ptit.englishapp.realtimedatabase.model.Payment;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ChartActivity extends AppCompatActivity {

    private static int MAX_X_VALUE = 12;
    private static final String LABEL_BAR_CHART = "Thống kê doanh thu theo tháng";
    private static final String LABEL_LINE_CHART = "Thống kê số người dùng đăng nhập theo từng giờ";
    private static final String[] DAYS = {"T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12"};
    private static final String[] HOURS = {"24h", "1h", "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h", "17h", "18h", "19h", "20h", "21h", "22h", "23h"};
    private BarChart chart;

    private LineChart lineChart;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    BarData data = new BarData();

    LineData lineData = new LineData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        lineChart = findViewById(R.id.linechart);
        chart = findViewById(R.id.barchart);
        chart.setMinimumHeight(1000);
        lineChart.setMinimumHeight(1000);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0);

        prepareChartData();
        configureChartAppearance();
        prepareChartData(data);

        prepareLineChartData();
        configureLineChartAppearance();
        prepareLineChartData(lineData);
    }

    private void configureLineChartAppearance() {
        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return HOURS[(int) value];
            }
        });
    }

    private void prepareLineChartData() {
        DatabaseReference myRef = database.getReference().child("login");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Login> loginList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Login login1 = dataSnapshot.getValue(Login.class);
                    loginList.add(login1);
                }
                Map<Integer, Long> loginByHour = loginList.stream()
                        .filter(login -> isToday(login.getLoginTime()))
                        .collect(Collectors.groupingBy(
                                login -> getHourFromTimestamp(login.getLoginTime()),
                                Collectors.counting()
                        ));
                List<Entry> values = new ArrayList<>();
                for (int hour = 0; hour <= 23; hour++) {
                    long count = loginByHour.getOrDefault(hour, 0L);
                    values.add(new Entry(hour, count));
                    System.out.println("Hour " + hour + " - " + count);
                }

                LineDataSet set = new LineDataSet(values, LABEL_LINE_CHART);
                set.setColors(ColorTemplate.COLORFUL_COLORS);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set);

                LineData data1 = new LineData(dataSets);

                prepareLineChartData(data1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void configureChartAppearance() {
        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });
    }

    private void prepareChartData() {
        DatabaseReference myRef = database.getReference().child("payment");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Payment> paymentList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String uid = dataSnapshot.getKey();
                    DatabaseReference uidRef = myRef.child(uid);

                    uidRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Payment payment = snapshot1.getValue(Payment.class);
                                System.out.println(payment);
                                paymentList.add(payment);
                            }
                            System.out.println("Ao that day");

                            Map<Integer, Double> summaries = paymentList.stream()
                                    .collect(
                                            Collectors.groupingBy(o -> getMonth(o.getDate()),
                                                    Collectors.summingDouble(Payment::getAmount))
                                    );

                            summaries.forEach((integer, aDouble) -> System.out.println(integer + ":" + aDouble));

                            // Sau khi tính toán xong, tạo dữ liệu cho biểu đồ
                            List<BarEntry> values = new ArrayList<>();
                            for (int i = 0; i < MAX_X_VALUE; i++) {
                                float x = i;
                                double y = summaries.getOrDefault(i, 0.0);
                                values.add(new BarEntry(x, (float) y));
                            }
                            BarDataSet set1 = new BarDataSet(values, LABEL_BAR_CHART);
                            set1.setColors(ColorTemplate.COLORFUL_COLORS);

                            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                            dataSets.add(set1);

                            BarData data = new BarData(dataSets);

                            prepareChartData(data);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int getMonth(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH);
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }

    private void prepareLineChartData(LineData data) {
        data.setValueTextSize(12f);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isToday(long timestamp) {
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime targetDateTime = ZonedDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(timestamp),
                ZoneOffset.UTC
        );

        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalDate targetDate = targetDateTime.toLocalDate();

        return currentDate.equals(targetDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getHourFromTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}