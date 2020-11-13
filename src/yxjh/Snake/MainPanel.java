/*
 * Copyright (C) <year> <copyright holders>
 *
 * ����Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * ����
 * ����The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
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
 * @author ���ӻ�
 */
public class MainPanel extends JPanel implements ActionListener {


	//�����ߵ�����
	int length;  //�ߵĳ���
	int[] locX = new int[600];  //��ÿ�����x����
	int[] locY = new int[600];  //��ÿ�����y����
	int direction;              //���ƶ��ķ���

	//������Ϸ������
	boolean isStart;
	boolean isDie;
	Timer timer = new Timer(150, this); //��ʱ���ļ��Ϊ100ms
	int score;

	//����ʳ�������
	int foodX;
	int foodY;
	Random random = new Random();


	public MainPanel() {
		init();
		this.setFocusable(true); //��ý���
		this.addKeyListener(new gameKeyListener());
	}

	public void init() {
		length = 2;
		locX[0] = 105;  //30+25*3
		locX[1] = 80;  //30+25*2
		locY[0] = locY[1] = 200;  //100+4*25
		direction = 3;  //�������ҷֱ��Ӧ0��1��2��3

		isStart = false;
		isDie = false;
		score = 0;

		timer.start();

		foodX = 30 + 25 * random.nextInt(925 / 25);
		foodY = 125 + 25 * random.nextInt(400 / 25);
	}

	// ������Ԫ��
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //������������

		//���ƾ�̬��Ϸ����
		this.setBackground(Color.cyan);
		g.fillRect(30, 10, 925, 90); // �÷���
		g.fillRect(30, 125, 925, 400); //��Ϸ����

		//�����ߵ�����
		ImageData.headIcon.paintIcon(this, g, locX[0], locY[0]); //����ͷ��

		for (int i = 1; i < length; i++) {
			ImageData.bodyIcon.paintIcon(this, g, locX[i], locY[i]); //��������
		}

		//����ʳ��
		ImageData.foodIcon.paintIcon(this, g, foodX, foodY);

		//���ƶ�ͷ�ķ�����
		g.setColor(Color.white);
		g.setFont(new Font("Gigi", Font.PLAIN, 40));
		g.drawString("Score:" + score, 375, 60);

		//���ƿ�ʼ��ʾ����
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
		//������Ϸ��ʼ״̬��Ҫ��С���ƶ�
		if (isStart) {
			//���߳�ʳ������ж�
			if (foodX == locX[0] && foodY == locY[0]) {
				length++;
				score++;
				//��������ʳ�������
				foodX = 30 + 25 * random.nextInt(925 / 25);
				foodY = 125 + 25 * random.nextInt(400 / 25);
			}


			//���ƶ�����
			for (int i = length - 1; i > 0; i--) {
				locX[i] = locX[i - 1];
				locY[i] = locY[i - 1];
			}

			//���ƶ�ͷ��
			if (direction == 0) {
				locY[0] -= 25;
			} else if (direction == 1) {
				locY[0] += 25;
			} else if (direction == 2) {
				locX[0] -= 25;
			} else {
				locX[0] += 25;
			}

			//�Դ����߽��ʱ������ж�
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

			//��ͷײ����������ж�
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
					//����֮�󰴿ո����¿�ʼ�������³�ʼ������,���һ����¿�ʼ�ж�
					init();
				} else {
					isStart = !isStart;
				}

				repaint();  //�ı���״̬֮��Ҫrepaint��ˢ�½���
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
