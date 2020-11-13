/*
 * Copyright (C) <year> <copyright holders>
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * @author 于子慧
 */
public class MainPanel extends JPanel implements ActionListener {


	//声明蛇的属性
	int length;  //蛇的长度
	int[] locX = new int[600];  //蛇每个点的x坐标
	int[] locY = new int[600];  //蛇每个点的y坐标
	int direction;              //蛇移动的方向

	//声明游戏的属性
	boolean isStart;
	boolean isDie;
	Timer timer = new Timer(150, this); //定时器的间隔为100ms
	int score;

	//声明食物的属性
	int foodX;
	int foodY;
	Random random = new Random();


	public MainPanel() {
		init();
		this.setFocusable(true); //获得焦点
		this.addKeyListener(new gameKeyListener());
	}

	public void init() {
		length = 2;
		locX[0] = 105;  //30+25*3
		locX[1] = 80;  //30+25*2
		locY[0] = locY[1] = 200;  //100+4*25
		direction = 3;  //上下左右分别对应0，1，2，3

		isStart = false;
		isDie = false;
		score = 0;

		timer.start();

		foodX = 30 + 25 * random.nextInt(925 / 25);
		foodY = 125 + 25 * random.nextInt(400 / 25);
	}

	// 用来画元素
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //有清屏的作用

		//绘制静态游戏界面
		this.setBackground(Color.cyan);
		g.fillRect(30, 10, 925, 90); // 得分栏
		g.fillRect(30, 125, 925, 400); //游戏界面

		//绘制蛇的身体
		ImageData.headIcon.paintIcon(this, g, locX[0], locY[0]); //绘制头部

		for (int i = 1; i < length; i++) {
			ImageData.bodyIcon.paintIcon(this, g, locX[i], locY[i]); //绘制身体
		}

		//绘制食物
		ImageData.foodIcon.paintIcon(this, g, foodX, foodY);

		//绘制顶头的分数栏
		g.setColor(Color.white);
		g.setFont(new Font("Gigi", Font.PLAIN, 40));
		g.drawString("Score:" + score, 375, 60);

		//绘制开始提示文字
		if (!isStart && !isDie) {
			g.setColor(Color.white);
			g.setFont(new Font("Gigi", Font.PLAIN, 30));
			g.drawString("Please press the space to start the game", 250, 300);
		}

		if (isDie) {
			g.setColor(Color.RED);
			g.setFont(new Font("Gigi", Font.PLAIN, 30));
			g.drawString("You are die. Press the space to restart the game", 250, 300);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//假如游戏开始状态则要让小蛇移动
		if (isStart) {
			//对蛇吃食物进行判断
			if (foodX == locX[0] && foodY == locY[0]) {
				length++;
				score++;
				//吃完后更新食物的坐标
				foodX = 30 + 25 * random.nextInt(925 / 25);
				foodY = 125 + 25 * random.nextInt(400 / 25);
			}


			//先移动身体
			for (int i = length - 1; i > 0; i--) {
				locX[i] = locX[i - 1];
				locY[i] = locY[i - 1];
			}

			//再移动头部
			if (direction == 0) {
				locY[0] -= 25;
			} else if (direction == 1) {
				locY[0] += 25;
			} else if (direction == 2) {
				locX[0] -= 25;
			} else {
				locX[0] += 25;
			}

			//对穿过边界的时候进行判断
			if (locX[0] > 930) {
				locX[0] = 30;
			} else if (locX[0] < 30) {
				locX[0] = 930;
			}
			if (locY[0] < 125) {
				locY[0] = 500;
			} else if (locY[0] > 500) {
				locY[0] = 125;
			}

			//对头撞到身体进行判断
			for (int i = 1; i < length; i++) {
				if (locX[0] == locX[i] && locY[0] == locY[i]) {
					isDie = true;
					isStart = false;
				}
			}

			repaint();
		}
	}

	private class gameKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int keycode = e.getKeyCode();
			if (keycode == KeyEvent.VK_SPACE) {
				if (isDie) {
					isDie = false;
					isStart = true;
					//死亡之后按空格重新开始并且重新初始化数据,并且会重新开始行动
					init();
				} else {
					isStart = !isStart;
				}

				repaint();  //改变了状态之后要repaint来刷新界面
			} else if (keycode == KeyEvent.VK_UP) {
				direction = 0;
			} else if (keycode == KeyEvent.VK_DOWN) {
				direction = 1;
			} else if (keycode == KeyEvent.VK_LEFT) {
				direction = 2;
			} else {
				direction = 3;
			}
		}
	}

}
