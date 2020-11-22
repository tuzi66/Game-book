/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
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
 * 游戏面板
 */
public class GamePanel extends JPanel {
	//定义背景图
	//背景图
	BufferedImage bg;
	//创建英雄机对象
	Hero hero = new Hero();
	//创建敌机总部
	//Ep ep = new Ep();
	List<Ep> eps = new ArrayList<Ep>();
	//创建弹药库
	List<Fire> fs = new ArrayList<Fire>();
	//定义分数
	int score;
	//设置游戏开关
	boolean gameover; //开始是 false  结束是true
	//设置火力
	int power = 1;
	//发射子弹方法
	int findex = 0;
	int index = 0;

	public GamePanel(GameFrame frame) {
		//设置背景颜色
		setBackground(Color.black);
		//初始化图片
		bg = App.getImg("/img/bg2.jpg");

		//创建使用鼠标适配器
		MouseAdapter adapter = new MouseAdapter() {
			//确定需要监听鼠标的事件
			//鼠标移动mouseMoved()
			//鼠标单击mouseCliked()
			//鼠标按下mousePressed()
			//鼠标移入游戏界面mouseEntered()
			//鼠标移除游戏界面mouseExited()
			@Override
			public void mouseClicked(MouseEvent e) {
				//点击鼠标时
				//游戏结束时点击重新开
				if (gameover) {
					//重新开始游戏
					//重新创建一个英雄机
					hero = new Hero();
					//删除文字
					//重置游戏开关
					gameover = false;
					//清零分数
					score = 0;
					//清空低级
					eps.clear();
					//清空子弹
					fs.clear();
					//随机背景
					Random rd = new Random();
					int index = rd.nextInt(5) + 1;
					bg = App.getImg("/img/bg" + index + ".jpg");
					//刷新界面
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				//当鼠标在游戏界面中移动时，会被触发的方法
				//让英雄机跟着一起移动 让英雄机的坐标等于鼠标的坐标  横纵坐标
				//获取鼠标的横纵坐标
				int mx = e.getX(); //鼠标横坐标
				int my = e.getY(); //鼠标纵坐标
				//让英雄机移动到鼠标上   行为
				if (!gameover) {
					hero.moveToMouse(mx, my);
				}

				//刷新界面 将英雄机重新绘制
				repaint();
			}
		};
		//将适配器添加到监听器   固定格式
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		//使用键盘适配器
		KeyAdapter kd = new KeyAdapter() {
			//确定事件
			@Override
			public void keyPressed(KeyEvent e) {
				//键盘按下的方法
				//监听键盘的按键  都对应数字
				//获取对应数字
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_UP) {
					//上建
					hero.moveUp();
				} else if (keyCode == KeyEvent.VK_DOWN) {
					//向下
					hero.moveDown();
				} else if (keyCode == KeyEvent.VK_LEFT) {
					//向左
					hero.moveLeft();
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					//向右
					hero.moveRight();
				}
				repaint();
			}
		};
		//将键盘加到窗体的监听器
		frame.addKeyListener(kd);
	}

	//开始游戏的方法
	public void action() {
		//创建并启动一个线程，控制游戏场景中物体的移动
		new Thread() {
			public void run() {
				//死循环，然游戏一直运行

				while (true) {
					if (!gameover) {
						//敌机入场
						epEnter();
						//调用敌机移动
						epMove();
						//发射子弹
						shoot();
						//子弹移动
						fireMove();
						//判断子弹是否打到敌机
						shootEp();
						//判断敌机是否撞到英雄机
						hit();
						//执行一次休息一会
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//重新绘制
					repaint();
				}
			}
		}.start();
	}

	//判断是否撞到
	protected void hit() {
		// TODO Auto-generated method stub
		for (int i = 0; i < eps.size(); i++) {
			Ep e = eps.get(i);
			//如果撞到了
			if (e.hitBy(hero)) {
				//1 删除敌机
				eps.remove(e);
				//血量减少
				hero.hp--;
				//加分数
				score += 5;
				//当英雄机血量减少到 0 时游戏结束
				if (hero.hp <= 0) {
					//游戏结束
					gameover = true;
				}
			}
		}

	}

	//子弹是否击中敌机
	//排除移除屏幕的
	protected void shootEp() {
		//遍历所有子弹
		for (int i = 0; i < fs.size(); i++) {
			//获取每一颗子弹
			Fire f = fs.get(i);
			//每拿一颗子弹，就判断这颗子弹是否击中敌机
			bang(f);
		}

	}

	//判断一颗子弹是否击中敌机
	private void bang(Fire f) {
		//遍历所有敌机
		for (int i = 0; i < eps.size(); i++) {
			//获取每一个敌机
			Ep e = eps.get(i);
			//判断这个子弹是否击中了敌机
			if (e.type != 15) {
				if (e.shootBy(f)) {
					//如果敌机被击中
					if (e.type == 12) {
						//增加活力
						power++;
						if (power > 3) {
							hero.hp++;
							power = 3;
						}
					}
					//1敌机死亡（消失：从集合中删除）
					eps.remove(e);

					//2删除子弹   从集合中删除
					fs.remove(f);
					//增加分数
					score += 5;

				}
			}

		}

	}

	//子弹移动的放大
	protected void fireMove() {
		for (int i = 0; i < fs.size(); i++) {
			//获取每一颗子弹
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
				//加到弹药库
				fs.add(fire3);
			} else if (power == 2) {
				Fire fire1 = new Fire(hero.x + 15, hero.y, 0);
				//加到弹药库
				fs.add(fire1);
				Fire fire2 = new Fire(hero.x + 75, hero.y, 2);
				//加到弹药库
				fs.add(fire2);
			} else {
				Fire fire1 = new Fire(hero.x + 15, hero.y, 0);
				//加到弹药库
				fs.add(fire1);
				Fire fire2 = new Fire(hero.x + 75, hero.y, 2);
				//加到弹药库
				fs.add(fire2);
				Fire fire3 = new Fire(hero.x + 45, hero.y - 20, 1);
				//加到弹药库
				fs.add(fire3);
			}

			findex = 0;
		}


	}

	//敌机移动方法
	protected void epMove() {
		//遍历
		for (int i = 0; i < eps.size(); i++) {
			Ep e = eps.get(i);
			e.move();
		}

	}

	//敌机入场方法
	protected void epEnter() {
		//记录执行次数
		index++;
		if (index >= 20) {
			//创建敌机
			Ep e = new Ep();
			//加入到集合
			eps.add(e);
			//将 index归零
			index = 0;
		}


	}

	//画图方法
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		//画背景图片
		g.drawImage(bg, 0, 0, 512, 768, null);
		//画图有顺序后画的会覆盖前面的
		//画英雄机
		g.drawImage(hero.img, hero.x, hero.y, hero.w, hero.w, null);
		//画敌机
		//遍历集合画出所有敌机
		for (int i = 0; i < eps.size(); i++) {
			Ep ep = eps.get(i);
			g.drawImage(ep.img, ep.x, ep.y, ep.w, ep.h, null);
		}
		//画子弹
		for (int i = 0; i < fs.size(); i++) {
			Fire fire = fs.get(i);
			g.drawImage(fire.img, fire.x, fire.y, fire.w, fire.h, null);
		}
		//画分数
		g.setColor(Color.white);
		g.setFont(new Font("楷体", Font.BOLD, 20));
		g.drawString("分数" + score, 10, 30);
		//画血量
		for (int i = 0; i < hero.hp; i++) {
			g.drawImage(hero.img, 350 + i * 35, 5, 30, 30, null);
		}
		//挡游戏结束时画出 GameOver
		if (gameover == true) {
			g.setColor(Color.red);
			g.setFont(new Font("楷体", Font.BOLD, 35));
			g.drawString("GAME OVER 重新开始把!!!!", 55, 300);
			g.setColor(Color.green);
			g.setFont(new Font("楷体", Font.BOLD, 20));
			g.drawString("点击屏幕重新开始游戏!!!", 65, 350);
		}


	}
}
