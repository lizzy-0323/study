package com.liziyi.tank;

public class FourDirFireStrategy implements FireStrategy {
//重写发射方法
    @Override
    public void fire(Tank t) {
        int bX = t.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = t.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;

        Dir[] dirs = Dir.values();
        //向四个方向发射子弹
        for(Dir dir : dirs) {
            new Bullet(bX, bY, dir, t.group, t.tf);
        }

        if(t.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }

}