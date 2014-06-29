package com.spiritflightapps.newberg.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BergDateUtility {

    final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    public String getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US);
        return sdf.format(new Date());
    }
}
