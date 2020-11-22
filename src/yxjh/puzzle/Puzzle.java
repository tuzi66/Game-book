/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.puzzle;


import yxdq.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Puzzle extends JFrame implements Runnable {
	MainPanel panel;
	String path;//图片路径
	int pattern;//图片的底数
	JMenuBar jmBar;
	JMenu menu, menuSelect, menuChange, menuRank, menuHelp;
	JMenuItem itemStart, itemExit, itemView;
	JRadioButtonMenuItem[] pic_change = new JRadioButtonMenuItem[4];
	JRadioButtonMenuItem[] game_rank = new JRadioButtonMenuItem[3];
	JLabel total_time;
	JLabel total_count;
	long startTime;
	long endTime;

	public Puzzle() {
		jmBar = new JMenuBar();
		menu = new JMenu("菜单");
		menuSelect = new JMenu("选择");
		menuHelp = new JMenu("帮助");
		menuChange = new JMenu("图片更换");
		menuRank = new JMenu("等级");

		itemStart = new JMenuItem("开始");
		itemExit = new JMenuItem("退出");
		itemView = new JMenuItem("查看背景");

		total_time = new JLabel("时间");
		total_count = new JLabel("步数");
		total_time.setForeground(Color.RED);
		total_count.setForeground(Color.RED);

		ButtonGroup groupChange = new ButtonGroup();
		for (int i = 0; i < pic_change.length; i++) {
			pic_change[i] = new JRadioButtonMenuItem("0" + (i + 1) + ".jpg");
			groupChange.add(pic_change[i]);
			menuChange.add(pic_change[i]);

		}
		pic_change[0].setSelected(true);

		ButtonGroup groupRank = new ButtonGroup();
		String content;
		for (int i = 0; i < game_rank.length; i++) {
			if (i == 0) {
				content = "简单";
			} else if (i == 1) {
				content = "普通";
			} else {
				content = "复杂";
			}
			game_rank[i] = new JRadioButtonMenuItem(content);
			groupRank.add(game_rank[i]);
			menuRank.add(game_rank[i]);

		}
		game_rank[0].setSelected(true);


		menu.add(itemStart);
		menu.add(itemView);
		menu.add(itemExit);
		menuSelect.add(menuChange);
		menuSelect.add(menuRank);
		jmBar.add(menu);
		jmBar.add(menuSelect);
		jmBar.add(menuHelp);
		jmBar.add(new JLabel("                       "));
		jmBar.add(total_time);
		jmBar.add(new JLabel("            "));
		jmBar.add(total_count);


		this.setJMenuBar(jmBar);

		itemStart.addActionListener(e -> {
			// TODO Auto-generated method stub
			//开始
			breakState();
		});

		itemExit.addActionListener(e -> {
			// TODO Auto-generated method stub
			System.exit(0);
		});

		itemView.addActionListener(e -> {
			// TODO Auto-generated method stub
			JButton index = new JButton(new ImageIcon(path + "\\index.jpg"));
			JFrame model = new JFrame("拼图模板");
			model.setSize(370, 390);
			model.setResizable(false);
			model.add(index);
			model.setVisible(true);
		});


		this.setTitle("拼图");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(380, 420);
		this.setResizable(false);
		this.setPath();
		this.setPattern();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(
						Puzzle.this, "确定退出拼图游戏? ", "退出游戏 ", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if (e.getWindow() == Puzzle.this) {
						MainFrame mainFrame = new MainFrame();
						yxdq.MainPanel panel = new yxdq.MainPanel(mainFrame);
						mainFrame.add(panel);
						mainFrame.setVisible(true);
						Puzzle.this.setVisible(false);
					} else {

					}
				}
			}
		});


		panel = new MainPanel(path, pattern);

		startTime = System.currentTimeMillis();
		this.add(panel);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle();
		Thread th = new Thread(puzzle);
		th.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			endTime = System.currentTimeMillis();
			int time = (int) ((endTime - startTime) / 1000);
			total_time.setText("时间" + time);
			total_count.setText("步数" + panel.getCount());
		}

	}

	public void breakState() {
		startTime = System.currentTimeMillis();
		setPattern();
		setPath();
		panel.breakRadom(path, pattern);
	}

	public void setPath() {
		//path = "img\\type1\\";
		for (int i = 0; i < pic_change.length; i++) {
			if (pic_change[i].isSelected()) {
				path = "img\\type" + (i + 1) + "\\";
			}

		}
	}

	public void setPattern() {
		//pattern = 3;
		for (int i = 0; i < game_rank.length; i++) {
			if (game_rank[i].isSelected()) {
				if (i == 0) {
					pattern = 3;
				} else if (i != 1) {
					if (i == 2) {
						pattern = 5;
					}
				} else {
					pattern = 4;
				}
			}

		}
	}
}
//顶级容器