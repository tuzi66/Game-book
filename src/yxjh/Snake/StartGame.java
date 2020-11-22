/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Snake;

import yxdq.MainFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 菟子
 */
public class StartGame {
	public StartGame() {
		JFrame jFrame = new JFrame("贪吃蛇");
		jFrame.add(new MainPanel());
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		jFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(jFrame,
						"确定退出贪吃蛇游戏? ",
						"退出游戏 ",
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if (e.getWindow() == jFrame) {
						MainFrame mainFrame = new MainFrame();
						yxdq.MainPanel panel = new yxdq.MainPanel(mainFrame);
						mainFrame.add(panel);
						mainFrame.setVisible(true);
						jFrame.setVisible(false);
					}
				}
			}
		});
		jFrame.setBounds(300, 150, 1000, 600);
		jFrame.setResizable(false);
	}

	@SuppressWarnings("InstantiationOfUtilityClass")
	public static void main(String[] args) {
		new StartGame();

	}
}


