package com.boa.userservice.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MaskEmailConverter extends ClassicConverter {
    private static final Pattern EMAIL = Pattern.compile(
            "([A-Za-z0-9._%+-])([A-Za-z0-9._%+-]*)([A-Za-z0-9._%+-])@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})"
    );

    private static final Pattern NAME=Pattern.compile("[A-Za-z]{5,10}");
    @Override
    public String convert(ILoggingEvent event) {


       String in = event.getFormattedMessage();
        if (in == null || in.isEmpty()) return in;

        String masked=EMAIL.matcher(in).replaceAll("****@***").toLowerCase();
         masked=NAME.matcher(masked).replaceAll("***").toLowerCase();
         return masked;
         /*
        Matcher m = EMAIL.matcher(in);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String first = m.group(1), mid = m.group(2), last = m.group(3), dom = m.group(4);
            m.appendReplacement(sb, first + mid.replaceAll(".", "*") + last + "@" + dom);
        }
        m.appendTail(sb);*/

      // Matcher m1 = NAME.matcher(in);

        //System.out.println(m1.find());
        //m1.group().replaceAll(NAME,"*");
       // m.appendTail(sb);

       // return sb.toString();
    }
}
