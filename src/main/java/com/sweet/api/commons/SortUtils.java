package com.sweet.api.commons;

import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/7/11.
 */
public class SortUtils {
    /**
     * 对list排序
     *
     * @param <T>
     * @param list
     * @param field     排序字段
     * @param orderType 排序枚举类型 asc ,desc
     * @return
     */
    public static <T> List<T> sortList(List<T> list, String field, SortType orderType) {
        int orderNum = 1;
        if (orderType == SortType.DESC) {
            orderNum = -1;
        }
        MyComparator<T> com = new MyComparator<T>(field, orderNum);
        Collections.sort(list, com);
        return list;
    }

    /**
     * 排序枚举类型 asc ,desc
     */
    public static enum SortType {
        ASC, DESC;
    }

    /**
     * 比较器
     */
    private static class MyComparator<T> implements Comparator<T> {
        /**
         * 排序字段
         */
        private String sortField;
        /**
         * 排序类型 >=0 asc <0 desc
         */
        private int orderBy;

        @Override
        public int compare(Object oo, Object ot) {
            Field fo = null;
            Field ft = null;
            try {
                // 获取第一个field
                try {
                    fo = oo.getClass().getDeclaredField(this.sortField);
                } catch (Exception e) {
                    fo = oo.getClass().getSuperclass().getDeclaredField(this.sortField);
                }
                if (fo != null) {
                    fo.setAccessible(true);
                }
                // 获取第二个field
                try {
                    ft = ot.getClass().getDeclaredField(this.sortField);
                } catch (Exception e) {
                    ft = ot.getClass().getSuperclass().getDeclaredField(this.sortField);
                }
                if (ft != null) {
                    ft.setAccessible(true);
                }

                if (XMLGregorianCalendar.class == fo.getType() && XMLGregorianCalendar.class == ft.getType()) {
                    // date sort
                    XMLGregorianCalendar dateo = (XMLGregorianCalendar) fo.get(oo);
                    XMLGregorianCalendar datet = (XMLGregorianCalendar) ft.get(ot);
                    if (this.orderBy >= 0) {
                        return (int) (dateo.toGregorianCalendar().getTime().getTime() - datet.toGregorianCalendar().getTime().getTime());
                    } else {
                        return (int) (datet.toGregorianCalendar().getTime().getTime() - dateo.toGregorianCalendar().getTime().getTime());
                    }
                } else if (Date.class == fo.getType() && Date.class == ft.getType()) {
                    // date sort
                    Date dateo = (Date) fo.get(oo);
                    Date datet = (Date) ft.get(ot);
                    if (this.orderBy >= 0) {
                        return (int) (dateo.getTime() - datet.getTime());
                    } else {
                        return (int) (datet.getTime() - dateo.getTime());
                    }
                } else if ((Integer.class == fo.getType() || int.class == fo.getType()) && (Integer.class == ft.getType() || int.class == ft.getType())) {
                    // int sort
                    Integer dateo = (Integer) fo.get(oo);
                    Integer datet = (Integer) ft.get(ot);
                    if (this.orderBy >= 0) {
                        return dateo - datet;
                    } else {
                        return datet - dateo;
                    }
                } else if (String.class == fo.getType() && String.class == ft.getType()) {
                    String dateo = (String) fo.get(oo);
                    String datet = (String) ft.get(ot);
                    if (this.orderBy >= 0) {
                        return dateo.compareTo(datet);
                    } else {
                        return datet.compareTo(dateo);
                    }
                } else if ((Long.class == fo.getType() || long.class == fo.getType()) && (Long.class == ft.getType() || long.class == ft.getType())) {
                    // long sort
                    Long dateo = (Long) fo.get(oo);
                    Long datet = (Long) ft.get(ot);
                    if (this.orderBy >= 0) {
                        return dateo - datet > 0 ? 1 : dateo - datet == 0 ? 0 : -1;
                    } else {
                        return datet - dateo > 0 ? 1 : datet - dateo == 0 ? 0 : -1;
                    }
                } else if ((Double.class == fo.getType() || double.class == fo.getType()) && (Double.class == ft.getType() || double.class == ft.getType())) {
                    // double sort
                    Double dateo = (Double) fo.get(oo);
                    Double datet = (Double) ft.get(ot);
                    if (this.orderBy >= 0) {
                        return dateo - datet > 0 ? 1 : dateo - datet == 0 ? 0 : -1;
                    } else {
                        return datet - dateo > 0 ? 1 : datet - dateo == 0 ? 0 : -1;
                    }
                } else {
                    return 0;
                }
            } catch (Exception e) {
                System.err.println(this.sortField + " - " + e.getMessage());
                return 0;
            }
        }

        public MyComparator(String sortField, int orderBy) {
            this.sortField = sortField;
            this.orderBy = orderBy;
        }

    }
}
