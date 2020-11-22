/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxdq;

import yxjh.Aircraftwar.GameFrame;
import yxjh.Aircraftwar.GamePanel;
import yxjh.Snake.StartGame;
import yxjh.puzzle.Puzzle;

import javax.swing.*;
import java.awt.*;

	/**
    * @author 于子慧
	 */
public class MainPanel extends JPanel {
	/**
	 * @description: 面版组件
	 * @author: 于子慧
	 * @time: 2020/10/23 19:42
	 */

	public MainPanel(MainFrame mainFrame) {
		/**
		 * @description: 加载所有的图片将图片赋值给按钮之后引用到窗体中
		 * @description: 组件布局定义网格布局 2行2列水平距离3 竖直距离2
		 * @param mainFrame
		 * @author: 于子慧
		 * @time: 2020/10/23 21:44
		 */

		JButton btn1, btn2, btn3, btn4;
		MIcon icon1 = new MIcon(new ImageIcon("img/puzzle.jpg"));
		MIcon icon2 = new MIcon(new ImageIcon("img/Aircraft war.jpg"));
		MIcon icon3 = new MIcon(new ImageIcon("img/txz.jpg"));
		MIcon icon4 = new MIcon(new ImageIcon("img/Snake.png"));
		setLayout(new GridLayout(2, 2, 3, 2));
		add(btn1 = new JButton(icon1));
		add(btn2 = new JButton(icon2));
		add(btn3 = new JButton(icon3));
		add(btn4 = new JButton(icon4));
		/*
		 * 鼠标监听 单击按钮 启动游戏 隐藏主窗体
		 */
		//拼图游戏
		btn1.addActionListener(e -> {
			Puzzle puzzle = new Puzzle();
			Thread th = new Thread(puzzle);
			th.start();
			mainFrame.setVisible(false);
		});
		//无敌小飞机
		btn2.addActionListener(e -> {
			//创建窗体对象
			GameFrame frame = new GameFrame();
			//创建面板对象
			GamePanel panel = new GamePanel(frame);
			//启动游戏
			panel.action();
			//将面板添加到窗体中
			frame.add(panel);
			frame.setVisible(true);
			mainFrame.setVisible(false);
		});
		//推箱子游戏
		btn3.addActionListener(e -> {
			//启动游戏
			new yxjh.Sokoban.GameFrame();
			//隐藏主窗体
			mainFrame.setVisible(false);
		});
		btn4.addActionListener(e -> {
			//启动游戏
			new StartGame();
			//隐藏主窗体
			mainFrame.setVisible(false);
		});

	}
}


