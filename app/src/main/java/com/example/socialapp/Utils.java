package com.example.socialapp;

public class Utils {

    private final long SECOND_MILLIS = 1000;
    private final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private final long DAY_MILLIS = 24 * HOUR_MILLIS;

    public String getTimeAgo(long time) {
        Long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        long diff = now - time;
        String result;
        if (diff < MINUTE_MILLIS) {
            result = "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            result = "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            result = (diff / MINUTE_MILLIS) + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            result = "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            result = (diff / HOUR_MILLIS) + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            result = "yesterday";
        } else {
            result = (diff / DAY_MILLIS) + " days ago";
        }
        return result;
    }

}
