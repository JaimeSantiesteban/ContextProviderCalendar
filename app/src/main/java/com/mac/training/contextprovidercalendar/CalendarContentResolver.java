package com.mac.training.contextprovidercalendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.mac.training.contextprovidercalendar.model.AndroidCalendar;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 8/30/2016.
 */
public class CalendarContentResolver {
    public static final String[] FIELDS = {
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars._ID
    };

    public static final Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/calendars");

    private ContentResolver contentResolver;
    private Set<AndroidCalendar> calendars = new HashSet<AndroidCalendar>();

    // Calendar test
    private long calID = 1;
    private long startMillis = 0;
    private long endMillis = 0;
    Calendar beginTime = Calendar.getInstance();
    Calendar endTime = Calendar.getInstance();
    //

    //
    Context ctx;
    //

    public CalendarContentResolver(Context ctx) {
        this.ctx = ctx;
        contentResolver = ctx.getContentResolver();

        //Setting
        beginTime.set(2016, 9, 1, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        endTime.set(2016, 9, 1, 8, 45);
        endMillis = endTime.getTimeInMillis();
        //
    }

    public Set<AndroidCalendar> getCalendars() {
        Cursor cursor = contentResolver.query(CALENDAR_URI, FIELDS, null, null, null);

        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    AndroidCalendar cal = new AndroidCalendar();
                    cal.setName(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.NAME)));
                    cal.setDisplayName(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)));
                    cal.setColor(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR)));
                    cal.setSelected(!cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.VISIBLE)).equals("0"));
                    cal.setId(cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID)));
                    calendars.add(cal);
                }
            }
        } catch (AssertionError ex) { /*TODO: log exception and bail*/ }
        return calendars;
    }

    public AndroidCalendar getCalendarById(long id) {

        String selection = CalendarContract.Calendars._ID + " = ?";

        Cursor cursor = contentResolver.query(CALENDAR_URI, FIELDS, selection, new String[]{String.valueOf(id)}, null);

        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    AndroidCalendar cal = new AndroidCalendar();
                    cal.setName(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.NAME)));
                    cal.setDisplayName(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)));
                    cal.setColor(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR)));
                    cal.setSelected(!cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.VISIBLE)).equals("0"));
                    cal.setId(cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID)));
                    return cal;
                }
            }
        } catch (AssertionError ex) {//*TODO: log exception and bail*//*

        }
        return null;
    }

    public long insertCalendarEvent() {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            ContentValues values = new ContentValues();
            values.put(Events.DTSTART, startMillis);
            values.put(Events.DTEND, endMillis);
            values.put(Events.TITLE, "Jazzercise");
            values.put(Events.DESCRIPTION, "Group workout");
            values.put(Events.CALENDAR_ID, calID);
            values.put(Events.EVENT_TIMEZONE, "America/Los_Angeles");

            Uri uri = contentResolver.insert(Events.CONTENT_URI, values);


            // get the event ID that is the last element in the Uri
            long eventID = Long.parseLong(uri.getLastPathSegment());
            return eventID;
        }
        return -1;
    }
}