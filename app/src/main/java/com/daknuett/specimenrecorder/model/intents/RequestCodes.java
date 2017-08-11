package com.daknuett.specimenrecorder.model.intents;

/**
 * Created by daniel on 06.08.17.
 */

public class RequestCodes {
    public static final int REQUEST = 0x2000,
        REQUEST_CAPTURE_PHOTO = REQUEST | 0x01,
        REQUEST_CAPTURE_SMALL_PHOTO = REQUEST | 0x02;

}
