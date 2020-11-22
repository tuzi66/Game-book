/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

//中间容器

public class MainPanel extends JPanel {
	yxjh.puzzle.Button[] button = new yxjh.puzzle.Button[25];//按钮数组
	ImageIcon[] icon = new ImageIcon[25];//图片数组
	int[] state = new int[25];//图片存放顺序
	int nullButton;//空白按钮位置顺序
	int pattern;//图片底数
	int total;//图片总数
	int count;//总步数


	public MainPanel(String path, int pattern) {
		this.pattern = pattern;
		total = pattern * pattern;
		breakRadom(path, pattern);
	}

	public void breakRadom(String path, int pattern) {
		count = 0;
		this.pattern = pattern;
		total = pattern * pattern;
		ImageUtil.cutImage(new File(path + "\\index.jpg"), pattern, path + pattern);
		this.removeAll();
		this.updateUI();
		this.setLayout(new GridLayout(pattern, pattern));
		nullButton = total - 1;
		random(state);
		for (int i = 0; i < total; i++) {
			button[i] = new yxjh.puzzle.Button();
			button[i].setRow(i / pattern);
			button[i].setCol(i % pattern);
			this.add(button[i]);

		}
		for (int i = 0; i < total - 1; i++) {
			icon[i] = new ImageIcon(path + pattern + "\\" + state[i] + ".jpg");
			button[i].setIcon(icon[i]);


		}
		for (int i = 0; i < total; i++) {
			button[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					yxjh.puzzle.Button button = (yxjh.puzzle.Button) e.getSource();
					remove(button);
					count++;


				}
			});

		}

	}

	public void random(int[] a) {
		Random cd = new Random();
		int i = 0;
		a[0] = cd.nextInt(total - 1);
		for (i = 0; i < total - 1; i++) {
			int temp = cd.nextInt(total - 1);
			for (int j = 0; j < i; j++) {
				if (a[j] != temp) {
					a[i] = temp;
				} else {
					i--;
					break;
				}

			}
		}
		a[i] = total - 1;
		System.out.println("图片的初始顺序为");
		for (i = 0; i < total; i++) {
			System.out.println(a[i] + "");
		}
	}

	public void remove(Button clicked) {
		int rowN = button[nullButton].getRow();
		int colN = button[nullButton].getCol();
		int rowC = clicked.getRow();
		int colC = clicked.getCol();
		if (((rowN - rowC) == 1 && (colN - colC) == 0) || ((rowN - rowC) == -1 && (colN - colC) == 0)
				|| ((rowN - rowC) == 0 && (colN - colC) == 1) || ((rowN - rowC) == 0 && (colN - colC) == -1)) {
			ImageIcon icon = (ImageIcon) clicked.getIcon();
			button[nullButton].setIcon(icon);
			clicked.setIcon(null);
			int clickState = rowC * pattern + colC;
			nullButton = rowN * pattern + colN;
			state[nullButton] = state[clickState];
			state[clickState] = total - 1;
			nullButton = clickState;
			check();
		} else {
			return;
		}

	}

	public void check() {
		for (int i = 0; i < total; i++) {
			if (state[i] != i) {
				return;
			}
		}
		JOptionPane.showMessageDialog(this, "拼图完成");

	}

	public int getCount() {
		return count;

	}
}

		










