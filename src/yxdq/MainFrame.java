/*
 * Copyright (C) 2020 ����
 *
 * ����Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * ����
 * ����The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxdq;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @author ���ӻ�
 */
public class MainFrame extends JFrame {

	public MainFrame() {
		/*���崰������*/
		setTitle("��Ϸ��ȫ");
		/*���ô���ߺͿ�*/
		setSize(480, 480);
		/*�˴��ڽ�������Ļ������*/
		setLocationRelativeTo(null);
		/* ���ô��岻�ɸı��С*/
		setResizable(false);
		/*�رմ���*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/*�رմ��ڼ��� ����һ��ȷ�Ͽ� ������ȷ���͹رմ��� ������ȡ�� ��ȡ���ر�*/
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int option = JOptionPane.showConfirmDialog(
						MainFrame.this, "ȷ���˳���Ϸ��ȫ? ", "�˳� ", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if (e.getWindow() == MainFrame.this) {
						System.exit(0);
					}
				}
			}
		});
		/*��ʾ����*/
		setVisible(true);

	}

	/**
	 * @description: ��������������Ϸ
	 * @return:
	 * @author: ���ӻ�
	 * @time: 2020/10/23 19:50
	 */

	public static void main(String[] args) {
		/*�����������*/
		MainFrame mainFrame = new MainFrame();
		/*����������*/
		MainPanel panel = new MainPanel(mainFrame);
		/*�������ӵ�������*/
		mainFrame.add(panel);
		mainFrame.setVisible(true);
	}
}
