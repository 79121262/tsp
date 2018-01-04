package com.tc.tsp.core.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cai.tian on 2017/12/4.
 */
public class OspStrings {

    public static List<String> split(final String str, final char separatorChar) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return Collections.EMPTY_LIST;
        }
        final List<String> list = new ArrayList<String>();
        int i = 0, start = 0;
        boolean match = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            match = true;
            i++;
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return list;

    }

    public static List<String> split(final String str, final char separatorChar, int expectParts) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return Collections.EMPTY_LIST;
        }
        final List<String> list = new ArrayList<String>(expectParts);
        int i = 0, start = 0;
        boolean match = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            match = true;
            i++;
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return list;

    }
}
