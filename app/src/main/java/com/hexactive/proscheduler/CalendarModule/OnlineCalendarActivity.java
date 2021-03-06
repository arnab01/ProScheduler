package com.hexactive.proscheduler.CalendarModule;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.hexactive.proscheduler.R;

public class OnlineCalendarActivity extends AppCompatActivity {
WebView webView;
Button microsoft,google;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_calendar);
        microsoft=findViewById(R.id.microsoft);
        google=findViewById(R.id.google);
        webView=findViewById(R.id.webview);
        webView.setVisibility(View.INVISIBLE);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.setVisibility(View.VISIBLE);
                String url = "https://calendar.google.com/calendar/r";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        microsoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.setVisibility(View.VISIBLE);
                String url = "https://office.live.com/start/Calendar.aspx?ui=en%2DUS&rs=US";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });




    }
}
