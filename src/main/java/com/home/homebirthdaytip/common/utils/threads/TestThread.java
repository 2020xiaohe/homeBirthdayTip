package com.home.homebirthdaytip.common.utils.threads;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestThread implements Runnable{
    int i =50;

    public synchronized int getI() {
        return i--;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        try {
            for (int j = getI(); j>0 ; j= getI()) {
                int time =  new Random().nextInt(400);
                System.out.println(time);
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName()+"执行了第"+j+"个任务");
                //i--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("code","1111");
        String param= JSON.toJSONString(map);
        System.out.println(param);


//        TestThread testThread = new TestThread();
//        for (int i = 0; i < 5; i++) {
//            Thread t = new Thread(testThread);
//            int j = i + 1;
//            t.setName("小何" + j + "号");
//            t.start();
//        }
    }
}
