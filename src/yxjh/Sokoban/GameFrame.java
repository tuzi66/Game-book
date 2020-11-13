/*
 * Copyright (C) <year> <copyright holders>
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Sokoban;

import yxdq.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * @author 于子慧
 */
public class GameFrame extends JFrame implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	final byte WALL = 1, BOX = 2, BOXONEND = 3, END = 4, MANDOWN = 5,
			MANLEFT = 6, MANRIGHT = 7, MANUP = 8, GRASS = 9, MANDOWNONEND = 10, MANLEFTONEND = 11,
			MANRIGHTONEND = 12, MANUPONEND = 13;

	private final ArrayList<Map> list = new ArrayList<>();

	//关卡数
	private int grade = 0;
	//row,column表示人物坐标；leftX,leftY记载左上角图片位置
	private int row = 7, column = 7, leftX = 0, leftY = 0;
	/* 地图的行列数 */
	private int mapRow = 0, mapColumn = 0;
	/* 屏幕大小 */
	private int width = 0, height = 0;
	private boolean acceptKey = true;
	private Image[] pic = null;
	private byte[][] map = null;

	/***
	 * @author 于子慧
	 *
	 * 构造方法，主要工作如下：
	 * 1.对窗口进行属性配置的初始化
	 * 2.添加按键和鼠标点击的监听事件
	 * 3.图片的加载
	 */
	public GameFrame() {
		super("推箱子游戏");
		setSize(600, 600);
		setVisible(true);
		setResizable(false);
		setLocation(300, 20);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(
						GameFrame.this, "确定退出推箱子游戏? ", "退出游戏 ", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if (e.getWindow() == GameFrame.this) {
						MainFrame mainFrame = new MainFrame();
						yxdq.MainPanel panel = new yxdq.MainPanel(mainFrame);
						mainFrame.add(panel);
						mainFrame.setVisible(true);
						GameFrame.this.setVisible(false);
					}
				}
			}
		});
		Container cont = getContentPane();
		cont.setLayout(null);
		cont.setBackground(Color.black);
		getPic();
		width = this.getWidth();
		height = this.getHeight();
		this.setFocusable(true);
		initMap();
		this.addKeyListener(this);
		this.addMouseListener(this);




	}

	/***
	 * @author 于子慧
	 *
	 * 主方法，启动游戏
	 */
	public static void main(String[] args) {
		new GameFrame();
	}

	/***
	 * @author 于子慧
	 *
	 * 根据当前的关卡数，进行地图的初始化，并清除步数记录
	 * 注释处用来打印当前关卡地图数据，取消注释可在控制台看到地图数组的信息
	 * 最后将地图大小，人物位置等信息存储到类成员中
	 */
	public void initMap() {
		map = getMap(grade);
		list.clear();
		/*
	    byte[][] temp = map;
		for(int i=0;i<temp.length;i++){
			for(int j=0;j<temp[0].length;j++){
				System.out.print(temp[i][j]+" ");
			}
			System.out.println();
		}
		*/
		getMapSizeAndPosition();
		getManPosition();
	}

	/***
	 * @author 于子慧
	 *
	 * 遍历地图数组，获取人物当前位置，存储下他所在的行列数
	 */
	public void getManPosition() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == MANDOWN || map[i][j] == MANUP || map[i][j] == MANLEFT || map[i][j] == MANRIGHT) {
					row = i;
					column = j;
					break;
				}
			}
		}
	}

	/***
	 * @author 于子慧
	 *
	 * 获取地图数组的行列数，进行存储
	 * 计算游戏主界面的上下左右边距
	 * 例如：左边距 = （窗口宽度 - 列数 * 方块宽度）/2
	 */
	public void getMapSizeAndPosition() {
		mapRow = map.length;
		mapColumn = map[0].length;
		leftX = (width - map[0].length * 30) / 2;
		leftY = (height - map.length * 30) / 2;
//		System.out.println(leftX);
//		System.out.println(leftY);
//		System.out.println(mapRow);
//		System.out.println(mapColumn);
	}

	/***
	 * @author 于子慧
	 *
	 * 初始化图片资源 ,从资源文件夹读取图片，并存储到pic数组中
	 */
	public void getPic() {
		pic = new Image[14];
		for (int i = 0; i <= 13; i++) {
			pic[i] = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/GameDrawable/pic" + i + ".png");
		}
	}

	/***
	 * @author 于子慧
	 *
	 * 判断进行移动前，人物当前是在草地还是目标点上
	 */
	public byte grassOrEnd(byte man) {
		byte result = GRASS;
		if (man == MANLEFTONEND || man == MANRIGHTONEND || man == MANUPONEND || man == MANDOWNONEND) {
			result = END;
		}
		return result;
	}

	/***
	 * @author 于子慧
	 *
	 * 上移操作，首先判断人物上方一格是不是墙，如果是墙return;
	 *
	 * -人物上方是箱子或位于目标点上的箱子吗？
	 * --true:人物上方第二格是草地或者过道吗？
	 * -------true:保存移动前的地图数据到list中，进行移动相关操作
	 * --false:此时人物向上一格是过道或者终点，进行移动相关操作
	 */
	private void moveUp() {
		if (map[row - 1][column] == WALL) {
			return;
		}
		byte tempBox;
		byte tempMan;

		if (map[row - 1][column] == BOX || map[row - 1][column] == BOXONEND) {        //如果向上一格是箱子
			if (map[row - 2][column] == GRASS || map[row - 2][column] == END) {     //如果向上第二格是草地或者目标点
				Map currentMap = new Map(row, column, map);
				list.add(currentMap);//用于撤回操作
				//人物向上两格是目标点吗？是，暂存下箱子在目标点上的状态；否，暂存下普通的箱子状态
				tempBox = map[row - 2][column] == END ? BOXONEND : BOX;
				//人物向上一格的箱子位于目标点上吗？是，暂存下人物位于目标点上并向上走的状态；否，暂存下人物向上走的状态
				tempMan = map[row - 1][column] == BOXONEND ? MANUPONEND : MANUP;
				//根据人物移动前的状态，去设置移动后，该位置是恢复成草地还是目标点。
				map[row][column] = grassOrEnd(map[row][column]);
				//对移动后的人物和箱子状态进行更新
				map[row - 2][column] = tempBox;
				map[row - 1][column] = tempMan;
				//人物所在行数上移了一行
				row--;
			}
		} else {//如果向上一格是过道或者目标点
			Map currentMap = new Map(row, column, map);
			list.add(currentMap);//用于撤回操作
			//人物上一格是草地吗？是，暂存下人向上走的状态；否，暂存下人位于目标点上并且向上走的状态。
			tempMan = map[row - 1][column] == GRASS ? MANUP : MANUPONEND;
			//对移动后人物和之前人物所在的两个位置的状态进行更新
			map[row][column] = grassOrEnd(map[row][column]);
			map[row - 1][column] = tempMan;
			//人物所在行数上移了一行
			row--;
		}

	}

	/***
	 * @author 于子慧
	 *
	 * 下移操作
	 *
	 * 可以参考上移操作的注释，运用的判断逻辑都一样，不再赘述
	 */
	private void moveDown() {


		if (map[row + 1][column] == WALL) {
			return;
		}

		byte tempBox;
		byte tempMan;

		if (map[row + 1][column] == BOX || map[row + 1][column] == BOXONEND) {
			if (map[row + 2][column] == END || map[row + 2][column] == GRASS) {
				Map currentMap = new Map(row, column, map);
				list.add(currentMap);//用于撤回操作
				tempBox = map[row + 2][column] == END ? BOXONEND : BOX;
				tempMan = map[row + 1][column] == BOXONEND ? MANDOWNONEND : MANDOWN;
				map[row][column] = grassOrEnd(map[row][column]);
				map[row + 2][column] = tempBox;
				map[row + 1][column] = tempMan;
				row++;
			}
		} else {
			Map currentMap = new Map(row, column, map);
			list.add(currentMap);//用于撤回操作
			tempMan = map[row + 1][column] == GRASS ? MANDOWN : MANDOWNONEND;
			map[row][column] = grassOrEnd(map[row][column]);
			map[row + 1][column] = tempMan;
			row++;
		}

	}

	/***
	 * @author 于子慧
	 *
	 * 左移操作
	 *
	 * 可以参考上移操作的注释，运用的判断逻辑都一样，不再赘述
	 */
	private void moveLeft() {

		if (map[row][column - 1] == WALL) {
			return;
		}

		byte tempBox;
		byte tempMan;

		if (map[row][column - 1] == BOX || map[row][column - 1] == BOXONEND) {
			if (map[row][column - 2] == END || map[row][column - 2] == GRASS) {
				Map currentMap = new Map(row, column, map);
				list.add(currentMap);//用于撤回操作
				tempBox = map[row][column - 2] == END ? BOXONEND : BOX;
				tempMan = map[row][column - 1] == BOXONEND ? MANLEFTONEND : MANLEFT;
				map[row][column] = grassOrEnd(map[row][column]);
				map[row][column - 2] = tempBox;
				map[row][column - 1] = tempMan;
				column--;
			}
		} else {
			Map currentMap = new Map(row, column, map);
			list.add(currentMap);//用于撤回操作
			tempMan = map[row][column - 1] == GRASS ? MANLEFT : MANLEFTONEND;
			map[row][column] = grassOrEnd(map[row][column]);
			map[row][column - 1] = tempMan;
			column--;
		}

	}

	/***
	 * @author 于子慧
	 *
	 * 右移操作
	 *
	 * 可以参考上移操作的注释，运用的判断逻辑都一样，不再赘述
	 */
	private void moveRight() {

		if (map[row][column + 1] == WALL) {
			return;
		}

		byte tempBox;
		byte tempMan;

		if (map[row][column + 1] == BOX || map[row][column + 1] == BOXONEND) {
			if (map[row][column + 2] == END || map[row][column + 2] == GRASS) {
				Map currentMap = new Map(row, column, map);
				list.add(currentMap);//用于撤回操作
				tempBox = map[row][column + 2] == END ? BOXONEND : BOX;
				tempMan = map[row][column + 1] == BOXONEND ? MANRIGHTONEND : MANRIGHT;
				map[row][column] = grassOrEnd(map[row][column]);
				map[row][column + 2] = tempBox;
				map[row][column + 1] = tempMan;
				column++;
			}
		} else {
			Map currentMap = new Map(row, column, map);
			list.add(currentMap);//用于撤回操作
			tempMan = map[row][column + 1] == GRASS ? MANRIGHT : MANRIGHTONEND;
			map[row][column] = grassOrEnd(map[row][column]);
			map[row][column + 1] = tempMan;
			column++;
		}

	}

	/***
	 * @author 于子慧
	 *
	 * 判断当前关卡是否通关。
	 *
	 * 原理：
	 * 遍历地图数组，看是否还存在没有被箱子覆盖的目标点
	 * 如果存在，说明当前关卡还没有通关。
	 * */
	public boolean isFinished() {
		for (int i = 0; i < mapRow; i++) {
			for (int j = 0; j < mapColumn; j++) {
				if (map[i][j] == END || map[i][j] == MANDOWNONEND || map[i][j] == MANUPONEND ||
						map[i][j] == MANLEFTONEND || map[i][j] == MANRIGHTONEND) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/***
	 *
	 * @author 于子慧
	 *
	 * 实现监听按键事件的方法
	 *
	 * 上下左右键执行人物移动逻辑
	 * A键上一关，D键下一关，B键撤回到上一步
	 *
	 * 人物移动或跳关的操作执行完，repaint重画游戏界面
	 * 判断当前关卡是否通关，如果通关，先屏蔽掉按键，等用户点击询问框后，再恢复按键监听
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveUp();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moveDown();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {//上一关
			acceptKey = true;
			priorGrade();
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {//下一关
			acceptKey = true;
			nextGrade();
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {//撤回
			undo();
		}

		repaint();

		if (isFinished()) {
			//禁用按键
			acceptKey = false;
			if (grade == 10) {
				JOptionPane.showMessageDialog(this, "恭喜通过最后一关");
			} else {
				String msg = "恭喜你通过第" + (grade + 1) + "关！！！\n是否要进入下一关？";
				int type = JOptionPane.YES_NO_OPTION;
				String title = "过关";
				int choice = 0;
				choice = JOptionPane.showConfirmDialog(null, msg, title, type);
				if (choice == 1) {
					System.exit(0);
				} else if (choice == 0) {
					acceptKey = true;
					nextGrade();
				}
			}
		}

	}

	/***
	 *
	 * @author 于子慧
	 *
	 * 绘画方法，每次调用repaint后，会通过paint进行画面绘制
	 *
	 * 遍历地图数组，根据不同的值，绘画不同的图片。
	 * 左上角x坐标 = 主界面左边距+当前列数*砖块边长
	 * 左上角y坐标 = 主界面上边距+当前行数*砖块边长
	 * 砖块的边长是30像素。
	 */
	@Override

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 600, 600);
		    for (int i = 0; i < mapRow; i++) {
			for (int j = 0; j < mapColumn; j++) {
				if (map[i][j] != 0) {
					g.drawImage(pic[map[i][j]], leftX + j * 30, leftY + i * 30, 30, 30, this);
				}
			}
		}

		g.setColor(Color.RED);
		g.setFont(new Font("楷体_2312", Font.BOLD, 30));
		g.drawString("现在是第", 150, 140);
		g.drawString(String.valueOf(grade + 1), 310, 140);
		g.drawString("关", 360, 140);
		g.setColor(Color.cyan);
		g.setFont(new Font("楷体_2312", Font.BOLD, 20));
		g.drawString("A键上一关，D键下一关，B键撤回到上一步", 150, 550);
	}

	/***
	 * @author 于子慧
	 *
	 * 获取人物当前所在行数
	 */
	public int getManX() {
		return row;
	}

	/***
	 * @author 于子慧
	 *
	 * 获取人物当前所在列数
	 */
	public int getManY() {
		return column;
	}

	/***
	 * @author 于子慧
	 *
	 * 获取当前关卡数
	 */
	public int getGrade() {
		return grade;
	}

	/***
	 * @author 于子慧
	 *
	 * 传入第几关的参数grade,返回这个关卡数的地图数组map
	 */
	public byte[][] getMap(int grade) {
		return MapFactory.getMap(grade);
	}

	/***
	 * @author 于子慧
	 *
	 * 传入string,进行弹窗提示
	 */
	public void DisplayToast(String str) {
		JOptionPane.showMessageDialog(null, str, "提示", JOptionPane.ERROR_MESSAGE);
	}

	/***
	 * @author 于子慧
	 *
	 * 撤销一步，类似于悔棋。
	 * 原理是从list中读取最后一个元素，这个元素是一个地图数组。
	 * 通过这个数组，可以恢复当前游戏数据到上一步。
	 *
	 */
	public void undo() {
		if (acceptKey) {
			if (list.size() > 0) {
				Map priorMap = list.get(list.size() - 1);
				map = priorMap.getMap();
				row = priorMap.getManX();
				column = priorMap.getManY();
				repaint();
				list.remove(list.size() - 1);
			} else {
				DisplayToast("不能再撤销");
			}
		} else {
			DisplayToast("此关已完成，不能撤销");
		}
	}

	/***
	 *
	 * @author 于子慧
	 *
	 * 调关操作，回到上一关
	 */
	public void priorGrade() {
		grade--;
		acceptKey = true;
		if (grade < 0) {
			grade = 0;
		}
		initMap();
		clearPaint(this.getGraphics());
		repaint();
	}

	/***
	 *
	 * @author 于子慧
	 *
	 * 调关操作，跳到下一关
	 */
	public void nextGrade() {
		if (grade >= MapFactory.getCount() - 1) {
			DisplayToast("恭喜你完成所有关卡");
			acceptKey = false;
		} else {
			grade++;
			initMap();
			clearPaint(this.getGraphics());
			repaint();
			acceptKey = true;
		}
	}

	/***
	 * @author 于子慧
	 *
	 * 清空画布操作
	 */
	private void clearPaint(Graphics g) {
		g.clearRect(0, 0, width + leftX, height + leftY);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/***
	 * @author 于子慧
	 *
	 * 监听鼠标点击事件，按鼠标右键回到上一步
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON3) {
			undo();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
