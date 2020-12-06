package com.liziyi.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
	long begin=System.currentTimeMillis();
	//游戏所需的基本变量
	Tank lzyTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
	List<Bullet> bullets = new ArrayList<>();
	List<Tank> tanks = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();

	//游戏界面的大小
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;
//定义游戏的基本框架
	public TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setResizable(false);
		setTitle("坦克大战单机版");
		setVisible(true);
		//为界面添加一个键盘监听，这里也初始化了一个对象
		this.addKeyListener(new MyKeyListener());
		//这里windowadapter是一个类，是一个匿名内部类
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	//用来处理闪烁问题
	Image offScreenImage = null;

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		//把背景设置为黑色
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		//在缓冲区画图
		paint(gOffScreen);
		//释放缓冲区内存
		g.drawImage(offScreenImage, 0, 0, null);
	}
	long end=System.currentTimeMillis();
	//用于绘图，每次调用时，调用不同类的绘图函数
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);

		if(!lzyTank.living||tanks.size()==0)
		{
			g.setFont(new Font("Tahoma", Font.BOLD, 50));
			g.drawString("游戏结束" , 400, 480);
			g.drawString("当前分数"+(2000-tanks.size()*100+(end-begin)),10,100);
		}
		if(lzyTank.living&&tanks.size()!=0)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Tahoma", Font.BOLD, 15));
			g.drawString("子弹的数量:" + bullets.size(), 10, 60);
			g.drawString("敌人的数量:" + tanks.size(), 10, 80);
			g.drawString("爆炸的数量:" + explodes.size(), 10, 100);
			lzyTank.paint(g);
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		
		for (int i = 0; i < tanks.size(); i++) {
			tanks.get(i).paint(g);
		}
		
		for (int i = 0; i < explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
		//用于检测碰撞
		for(int i=0; i<bullets.size(); i++) {
			for(int j = 0; j<tanks.size(); j++) 
				bullets.get(i).collideWith(tanks.get(j));
			bullets.get(i).collideWith(lzyTank);
		}

	}
	//一个内部键盘事件监听类，继承来自keyadpter
	class MyKeyListener extends KeyAdapter {

		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		//重写键盘监听方法
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;

			default:
				break;
			}

			setMainTankDir();

			new Thread(()->new Audio("audio/tank_move.wav").play()).start();
		}
		//重写方法，用于检测键盘释放
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;

			case KeyEvent.VK_CONTROL:
				lzyTank.fire();
				break;

			default:
				break;
			}

			setMainTankDir();
		}
		//用于将当前键盘的各个状态传递
		private void setMainTankDir() {

			if (!bL && !bU && !bR && !bD)
				lzyTank.setMoving(false);
			else {
				lzyTank.setMoving(true);

				if (bL)
					lzyTank.setDir(Dir.LEFT);
				if (bU)
					lzyTank.setDir(Dir.UP);
				if (bR)
					lzyTank.setDir(Dir.RIGHT);
				if (bD)
					lzyTank.setDir(Dir.DOWN);
			}
		}
	}
}
