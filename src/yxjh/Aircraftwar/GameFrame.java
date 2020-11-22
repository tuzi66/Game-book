/*
 * Copyright (C) 2020 ����
 *
 * ����Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * ����
 * ����The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

import yxdq.MainFrame;
import yxdq.MainPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * ��Ϸ����
 */
public class GameFrame extends JFrame {
	public GameFrame() {
		//���ñ���
		setTitle("�޵�С�ɻ�");
		//���ô�С
		setSize(512, 768);
		//���ô��������ʾ
		//��������ϽǾ���
		setLocationRelativeTo(null);
		//���ô��岻����ı�����С
		setResizable(false);
		//����Ĭ�ϵĹر�ѡ��
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(
						GameFrame.this, "ȷ���˳��޵�С�ɻ�? ", "�˳���Ϸ ", JOptionPane.YES_OPTION);
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
		//�����������
		GameFrame frame = new GameFrame();
		//����������
		GamePanel panel = new GamePanel(frame);
		//������Ϸ
		panel.action();
		//�������ӵ�������
		frame.add(panel);
		frame.setVisible(true);

	}
}
