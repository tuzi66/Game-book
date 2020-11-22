/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

import yxdq.MainFrame;
import yxdq.MainPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * 游戏窗体
 */
public class GameFrame extends JFrame {
	public GameFrame() {
		//设置标题
		setTitle("无敌小飞机");
		//设置大小
		setSize(512, 768);
		//设置窗体居中显示
		//相对于左上角居中
		setLocationRelativeTo(null);
		//设置窗体不允许改变界面大小
		setResizable(false);
		//设置默认的关闭选项
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(
						GameFrame.this, "确定退出无敌小飞机? ", "退出游戏 ", JOptionPane.YES_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if (e.getWindow() == GameFrame.this) {
						MainFrame mainFrame = new MainFrame();
						MainPanel panel = new MainPanel(mainFrame);
						mainFrame.add(panel);
						mainFrame.setVisible(true);
						GameFrame.this.setVisible(false);
					} else {
						return;

					}
				}
			}
		});

	}

	public static void main(String[] args) {
		//创建窗体对象
		GameFrame frame = new GameFrame();
		//创建面板对象
		GamePanel panel = new GamePanel(frame);
		//启动游戏
		panel.action();
		//将面板添加到窗体中
		frame.add(panel);
		frame.setVisible(true);

	}
}
