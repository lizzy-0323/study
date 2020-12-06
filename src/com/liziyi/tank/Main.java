package com.liziyi.tank;

public class  Main {
//这里需要抛出异常
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
        //初始化敌方坦克
        for(int i=0; i<initTankCount; i++) {
            tf.tanks.add(new Tank(50 + i*100, 200, Dir.UP, Group.BAD, tf));
        }
        //启动一个线程
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
//刷新界面
        while(true) {
            Thread.sleep(25);
            tf.repaint();
        }


    }

}
