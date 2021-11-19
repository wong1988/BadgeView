package io.github.badge.example;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.github.wong1988.badge.BadgeView;
import io.github.wong1988.badge.attr.BadgeMax;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BadgeView badgeView = (BadgeView) findViewById(R.id.badge);
        badgeView.setBadgeMax(BadgeMax.MAX_99);
        badgeView.setDotStyle(false);
        badgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) (Math.random() * 200);
                badgeView.setText(String.valueOf(i));
            }
        });
    }
}