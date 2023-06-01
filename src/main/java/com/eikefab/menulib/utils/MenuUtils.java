package com.eikefab.menulib.utils;

import com.eikefab.menulib.MenuContext;
import com.eikefab.menulib.MenuItem;

import java.util.Iterator;
import java.util.List;

public class MenuUtils {

    public static void fillCenter(MenuContext context, int start, int end, List<MenuItem> list) {
        final Iterator<MenuItem> iterator = list.iterator();

        for (; start <= end && iterator.hasNext(); start++) {
            if (isBorder(start)) {
                continue;
            }

            final MenuItem menuItem = iterator.next();

            context.setItem(start, menuItem);
        }
    }

    public static boolean isBorder(int slot) {
        return (slot % 9 == 0) || ((slot + 1) % 9 == 0);
    }

}
