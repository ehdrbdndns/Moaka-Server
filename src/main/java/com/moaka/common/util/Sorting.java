package com.moaka.common.util;

import java.util.Comparator;

/**
 * 정렬에 관련된 Class
 */
public class Sorting {
    /**
     * Desc 정렬
     */
    public static class DescendingObject implements Comparator<Object> {
        /**
         * o2.getter(), o1.getter()로 정렬
         */
        @Override
        public int compare(Object o1, Object o2) {
            return Float.compare(o2.hashCode(), o1.hashCode());
        }
    }
}
