package com.liziyi.tank;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
//用于输出
import javax.sound.sampled.SourceDataLine;
//用于播放音频
public class Audio {
	byte[] b = new byte[1024 * 1024 * 15];
	//不断循环播放
	public void loop() {
		try {
			while (true) {
				int len = 0;
				//打开具有指定格式和建议缓冲区大小的行，使该行获取任何所需的系统资源并开始运行
				sourceDataLine.open(audioFormat, 1024 * 1024 * 15);
				//允许读取一行数据，但不进行任何操作
				sourceDataLine.start();
				//System.out.println(audioInputStream.markSupported());
				//标记输入流中的当前位置
				audioInputStream.mark(12358946);
				//读取字节，储存在缓冲区中
				while ((len = audioInputStream.read(b)) > 0) {
					sourceDataLine.write(b, 0, len);
				}
				//定位到上一次的位置
				audioInputStream.reset();
				sourceDataLine.drain();
				sourceDataLine.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private AudioFormat audioFormat = null;
	private SourceDataLine sourceDataLine = null;
	private DataLine.Info dataLine_info = null;

	private AudioInputStream audioInputStream = null;
	//用于读取音频数据，为构造函数
	public Audio(String fileName) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(Audio.class.getClassLoader().getResource(fileName));
			//获取输入流中音频的输入格式
			audioFormat = audioInputStream.getFormat();
			dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLine_info);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//输出音频
	public void play() {
		try {
			byte[] b = new byte[1024*5];
			int len = 0;
			sourceDataLine.open(audioFormat, 1024*5);
			sourceDataLine.start();
			audioInputStream.mark(12358946);
			while ((len = audioInputStream.read(b)) > 0) {
				sourceDataLine.write(b, 0, len);
			}
			//这里只播放一次
			sourceDataLine.drain();
			sourceDataLine.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			audioInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//用于测试
	public static void main(String[] args) {
		Audio a = new Audio("audio/war1.wav");
		a.loop();

	}

}
