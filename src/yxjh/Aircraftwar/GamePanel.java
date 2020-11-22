/*
 * Copyright (C) 2020 ����
 *
 * ����Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * ����
 * ����The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
 * ��Ϸ���
 */
public class GamePanel extends JPanel {
	//���屳��ͼ
	//����ͼ
	BufferedImage bg;
	//����Ӣ�ۻ�����
	Hero hero = new Hero();
	//�����л��ܲ�
	//Ep ep = new Ep();
	List<Ep> eps = new ArrayList<Ep>();
	//������ҩ��
	List<Fire> fs = new ArrayList<Fire>();
	//�������
	int score;
	//������Ϸ����
	boolean gameover; //��ʼ�� false  ������true
	//���û���
	int power = 1;
	//�����ӵ�����
	int findex = 0;
	int index = 0;

	public GamePanel(GameFrame frame) {
		//���ñ�����ɫ
		setBackground(Color.black);
		//��ʼ��ͼƬ
		bg = App.getImg("/img/bg2.jpg");

		//����ʹ�����������
		MouseAdapter adapter = new MouseAdapter() {
			//ȷ����Ҫ���������¼�
			//����ƶ�mouseMoved()
			//��굥��mouseCliked()
			//��갴��mousePressed()
			//���������Ϸ����mouseEntered()
			//����Ƴ���Ϸ����mouseExited()
			@Override
			public void mouseClicked(MouseEvent e) {
				//������ʱ
				//��Ϸ����ʱ������¿�
				if (gameover) {
					//���¿�ʼ��Ϸ
					//���´���һ��Ӣ�ۻ�
					hero = new Hero();
					//ɾ������
					//������Ϸ����
					gameover = false;
					//�������
					score = 0;
					//��յͼ�
					eps.clear();
					//����ӵ�
					fs.clear();
					//�������
					Random rd = new Random();
					int index = rd.nextInt(5) + 1;
					bg = App.getImg("/img/bg" + index + ".jpg");
					//ˢ�½���
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				//���������Ϸ�������ƶ�ʱ���ᱻ�����ķ���
				//��Ӣ�ۻ�����һ���ƶ� ��Ӣ�ۻ������������������  ��������
				//��ȡ���ĺ�������
				int mx = e.getX(); //��������
				int my = e.getY(); //���������
				//��Ӣ�ۻ��ƶ��������   ��Ϊ
				if (!gameover) {
					hero.moveToMouse(mx, my);
				}

				//ˢ�½��� ��Ӣ�ۻ����»���
				repaint();
			}
		};
		//����������ӵ�������   �̶���ʽ
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		//ʹ�ü���������
		KeyAdapter kd = new KeyAdapter() {
			//ȷ���¼�
			@Override
			public void keyPressed(KeyEvent e) {
				//���̰��µķ���
				//�������̵İ���  ����Ӧ����
				//��ȡ��Ӧ����
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_UP) {
					//�Ͻ�
					hero.moveUp();
				} else if (keyCode == KeyEvent.VK_DOWN) {
					//����
					hero.moveDown();
				} else if (keyCode == KeyEvent.VK_LEFT) {
					//����
					hero.moveLeft();
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					//����
					hero.moveRight();
				}
				repaint();
			}
		};
		//�����̼ӵ�����ļ�����
		frame.addKeyListener(kd);
	}

	//��ʼ��Ϸ�ķ���
	public void action() {
		//����������һ���̣߳�������Ϸ������������ƶ�
		new Thread() {
			public void run() {
				//��ѭ����Ȼ��Ϸһֱ����

				while (true) {
					if (!gameover) {
						//�л��볡
						epEnter();
						//���õл��ƶ�
						epMove();
						//�����ӵ�
						shoot();
						//�ӵ��ƶ�
						fireMove();
						//�ж��ӵ��Ƿ�򵽵л�
						shootEp();
						//�жϵл��Ƿ�ײ��Ӣ�ۻ�
						hit();
						//ִ��һ����Ϣһ��
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//���»���
					repaint();
				}
			}
		}.start();
	}

	//�ж��Ƿ�ײ��
	protected void hit() {
		// TODO Auto-generated method stub
		for (int i = 0; i < eps.size(); i++) {
			Ep e = eps.get(i);
			//���ײ����
			if (e.hitBy(hero)) {
				//1 ɾ���л�
				eps.remove(e);
				//Ѫ������
				hero.hp--;
				//�ӷ���
				score += 5;
				//��Ӣ�ۻ�Ѫ�����ٵ� 0 ʱ��Ϸ����
				if (hero.hp <= 0) {
					//��Ϸ����
					gameover = true;
				}
			}
		}

	}

	//�ӵ��Ƿ���ел�
	//�ų��Ƴ���Ļ��
	protected void shootEp() {
		//���������ӵ�
		for (int i = 0; i < fs.size(); i++) {
			//��ȡÿһ���ӵ�
			Fire f = fs.get(i);
			//ÿ��һ���ӵ������ж�����ӵ��Ƿ���ел�
			bang(f);
		}

	}

	//�ж�һ���ӵ��Ƿ���ел�
	private void bang(Fire f) {
		//�������ел�
		for (int i = 0; i < eps.size(); i++) {
			//��ȡÿһ���л�
			Ep e = eps.get(i);
			//�ж�����ӵ��Ƿ�����˵л�
			if (e.type != 15) {
				if (e.shootBy(f)) {
					//����л�������
					if (e.type == 12) {
						//���ӻ���
						power++;
						if (power > 3) {
							hero.hp++;
							power = 3;
						}
					}
					//1�л���������ʧ���Ӽ�����ɾ����
					eps.remove(e);

					//2ɾ���ӵ�   �Ӽ�����ɾ��
					fs.remove(f);
					//���ӷ���
					score += 5;

				}
			}

		}

	}

	//�ӵ��ƶ��ķŴ�
	protected void fireMove() {
		for (int i = 0; i < fs.size(); i++) {
			//��ȡÿһ���ӵ�
			Fire f = fs.get(i);
			//yidong1
			f.move();
		}

	}

	protected void shoot() {
		findex++;
		if (findex >= 20) {
			if (power == 1) {
				Fire fire3 = new Fire(hero.x + 45, hero.y - 20, 1);
				//�ӵ���ҩ��
				fs.add(fire3);
			} else if (power == 2) {
				Fire fire1 = new Fire(hero.x + 15, hero.y, 0);
				//�ӵ���ҩ��
				fs.add(fire1);
				Fire fire2 = new Fire(hero.x + 75, hero.y, 2);
				//�ӵ���ҩ��
				fs.add(fire2);
			} else {
				Fire fire1 = new Fire(hero.x + 15, hero.y, 0);
				//�ӵ���ҩ��
				fs.add(fire1);
				Fire fire2 = new Fire(hero.x + 75, hero.y, 2);
				//�ӵ���ҩ��
				fs.add(fire2);
				Fire fire3 = new Fire(hero.x + 45, hero.y - 20, 1);
				//�ӵ���ҩ��
				fs.add(fire3);
			}

			findex = 0;
		}


	}

	//�л��ƶ�����
	protected void epMove() {
		//����
		for (int i = 0; i < eps.size(); i++) {
			Ep e = eps.get(i);
			e.move();
		}

	}

	//�л��볡����
	protected void epEnter() {
		//��¼ִ�д���
		index++;
		if (index >= 20) {
			//�����л�
			Ep e = new Ep();
			//���뵽����
			eps.add(e);
			//�� index����
			index = 0;
		}


	}

	//��ͼ����
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		//������ͼƬ
		g.drawImage(bg, 0, 0, 512, 768, null);
		//��ͼ��˳��󻭵ĻḲ��ǰ���
		//��Ӣ�ۻ�
		g.drawImage(hero.img, hero.x, hero.y, hero.w, hero.w, null);
		//���л�
		//�������ϻ������ел�
		for (int i = 0; i < eps.size(); i++) {
			Ep ep = eps.get(i);
			g.drawImage(ep.img, ep.x, ep.y, ep.w, ep.h, null);
		}
		//���ӵ�
		for (int i = 0; i < fs.size(); i++) {
			Fire fire = fs.get(i);
			g.drawImage(fire.img, fire.x, fire.y, fire.w, fire.h, null);
		}
		//������
		g.setColor(Color.white);
		g.setFont(new Font("����", Font.BOLD, 20));
		g.drawString("����" + score, 10, 30);
		//��Ѫ��
		for (int i = 0; i < hero.hp; i++) {
			g.drawImage(hero.img, 350 + i * 35, 5, 30, 30, null);
		}
		//����Ϸ����ʱ���� GameOver
		if (gameover == true) {
			g.setColor(Color.red);
			g.setFont(new Font("����", Font.BOLD, 35));
			g.drawString("GAME OVER ���¿�ʼ��!!!!", 55, 300);
			g.setColor(Color.green);
			g.setFont(new Font("����", Font.BOLD, 20));
			g.drawString("�����Ļ���¿�ʼ��Ϸ!!!", 65, 350);
		}


	}
}
