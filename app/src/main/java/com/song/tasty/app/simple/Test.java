package com.song.tasty.app.simple;

import android.net.Uri;
import android.telecom.PhoneAccount;

public class Test {

    public static void main(String[] args) {

        String number = "15195650467";

        Uri uri1 = Uri.fromParts(PhoneAccount.SCHEME_SIP, number, null);

        Uri uri2 = Uri.fromParts(PhoneAccount.SCHEME_TEL, number, null);

        System.out.println(uri1.getScheme());
        System.out.println(uri1.getSchemeSpecificPart());
        System.out.println(uri1.getUserInfo());

        System.out.println(uri2.getScheme());
        System.out.println(uri2.getSchemeSpecificPart());
        System.out.println(uri2.getUserInfo());

    }
}
