package com.liziyi.tank;
//定义一个接口，用于表示子弹的发射方式
public interface FireStrategy {
    void fire(Tank t);
}