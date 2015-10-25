package me.packbag.android.util;

import java.util.Collections;
import java.util.List;

/**
 * Created by astra on 17.07.2015.
 */
public class Empties {

    public static <T> List<T> emptyIfNull(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }
}
