/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxdq;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @author 于子慧
 */
public class MainFrame extends JFrame {

	public MainFrame() {
		/*定义窗体名称*/
		setTitle("游戏大全");
		/*设置窗体高和宽*/
		setSize(480, 480);
		/*此窗口将置于屏幕的中央*/
		setLocationRelativeTo(null);
		/* 设置窗体不可改变大小*/
		setResizable(false);
		/*关闭窗口*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/*关闭窗口监听 定义一个确认框 如果点击确定就关闭窗口 如果点击取消 就取消关闭*/
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int option = JOptionPane.showConfirmDialog(
						MainFrame.this, "确定退出游戏大全? ", "退出 ", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if (e.getWindow() == MainFrame.this) {
						System.exit(0);
					}
				}
			}
		});
		/*显示窗体*/
		setVisible(true);

	}

	/**
	 * @description: 主方法，启动游戏
	 * @return:
	 * @author: 于子慧
	 * @time: 2020/10/23 19:50
	 */

	public static void main(String[] args) {
		/*创建窗体对象*/
		MainFrame mainFrame = new MainFrame();
		/*创建面板对象*/
		MainPanel panel = new MainPanel(mainFrame);
		/*将面板添加到窗体中*/
		mainFrame.add(panel);
		mainFrame.setVisible(true);
	}
}
