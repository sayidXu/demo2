package com.xutt.sky.learn.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo1 {

    private static Map<Long, Integer> widgetCacheMap = new ConcurrentHashMap<Long, Integer>();

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            Thread tt = new Thread(new Rund());
            tt.start();
        }
    }

    static class Rund implements Runnable {

        public void run() {
            test();
        }

        /**
         * 1W次，总有那么几次线程不安全
         */
        public void test() {
            //synchronized ("") {// 解决方案
                ConcurrentHashMapDemo1 tt = new ConcurrentHashMapDemo1();
                tt.set();
                int s1 = widgetCacheMap.get(1L).intValue();
                tt.change();
                int s2 = widgetCacheMap.get(1L).intValue();
                if (s1 == s2) {
                    System.out.println(s1 + ":" + s2);
                }
            //}
        }

    }

    public void set() {
        Map<Long, Integer> mm = new HashMap<Long, Integer>();
        mm.put(1L, 1);
        widgetCacheMap = mm;
    }

    public void change() {
        Map<Long, Integer> mm = new HashMap<Long, Integer>();
        mm.put(1L, 2);
        widgetCacheMap = mm;
    }

}
