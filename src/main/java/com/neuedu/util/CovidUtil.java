package com.neuedu.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.Entity.Detail;
import com.neuedu.Entity.Province;

import java.util.Comparator;

public class CovidUtil {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static String COVID_DETAIL = "Detail";
    public static String COVID_PATIENT = "Patient";
    public static String COVID_MAP = "Map";
    public static String URL_163 = "https://c.m.163.com/ug/api/wuhan/app/data/list-total";
    public static String URL_tencent = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=statisGradeCityDetail,diseaseh5Shelf";
    public static Long TIME_OUT = 60L*60*24;
    public static Comparator<Detail> detailComparator = new Comparator<Detail>() {
        @Override
        public int compare(Detail o1, Detail o2) {
            int y1 = o1.getYear();
            int y2 = o2.getYear();
            if(y1<y2){
                return -1;
            }
            else if(y2>y2)
                return 1;
            else
            {
                int m1= o1.getMonth();
                int m2 = o2.getMonth();
                if(m1<m2)
                    return -1;
                else if(m1 > m2)
                    return 1;
                else
                {
                    int d1 = o1.getDate();
                    int d2 = o2.getDate();
                    if(d1<d2)
                        return -1;
                    else
                        return 1;
                }
            }
        }
    };
    public static Comparator<Province> provinceComparator = new Comparator<Province>() {
        @Override
        public int compare(Province o1, Province o2) {
            int n1 = o1.getSumnum();
            int n2 = o2.getSumnum();
            if(n1 < n2)
                return 1;
            return -1;
        }
    };

}
