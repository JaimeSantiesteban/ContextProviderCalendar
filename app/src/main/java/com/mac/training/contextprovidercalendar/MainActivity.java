package com.mac.training.contextprovidercalendar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mac.training.contextprovidercalendar.model.AndroidCalendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_LOG = "JS_Log";
    private TextView tvCalendars;
    private static final int CODE_READ_CALENDAR = 10;
    private static final int CODE_WRITE_CALENDAR = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCalendars = ((TextView) this.findViewById(R.id.tvCalendars));
    }

    public void onGetCalendars(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALENDAR)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, CODE_READ_CALENDAR);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, CODE_READ_CALENDAR);
            }
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CALENDAR)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, CODE_WRITE_CALENDAR);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, CODE_WRITE_CALENDAR);
            }
        } else {
            onGetCalendarsAction();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onGetCalendarsAction();
        } else {
            //Dont have permissions
        }

    }

    private void onGetCalendarsAction() {
        CalendarContentResolver calConResolver = new CalendarContentResolver(getApplication());
        for (AndroidCalendar calendarData : calConResolver.getCalendars()) {
            tvCalendars.setText(calendarData.toString() + "\n");
        }

        Log.d(TAG_LOG, calConResolver.getCalendarById(1).toString());


        Log.d(TAG_LOG, "Event Insert result: " + calConResolver.insertCalendarEvent());

    }
}
