package com.liziyi.tank;

import java.awt.Graphics;

//爆炸类
public class Explode {
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	
	private int x, y;
	TankFrame tf = null;
	
	private int step = 0;

	public Explode(int x, int y, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
		
		new Thread(()->new Audio("audio/explode.wav").play()).start();
	}
	

	//爆炸类的paint函数
	public void paint(Graphics g) {
		//爆炸发生时，依次播放各个图片
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		
		if(step >= ResourceMgr.explodes.length) 
			tf.explodes.remove(this);

	}
	
	

}
