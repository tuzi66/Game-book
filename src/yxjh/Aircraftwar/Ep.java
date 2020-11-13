/*
 * Copyright (C) <year> <copyright holders>
 *
 * ����Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * ����
 * ����The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

import java.util.Random;

/*
 * �л���
 * ������
 * �л������ܶ�
 * �л�����δ֪
 *
 */
public class Ep extends FlyObject {
	int sp; //�л��ٶ�
	int hp;
	int type;

	//���л�����
	public Ep() {
		//���������
		Random rd = new Random();
		//s���������ѡͼƬ
		int index = rd.nextInt(15) + 1;
		//��������
		type = index;
		//ȷ���л���ʾ��ͼƬ
		String path = "/img/ep" + (index < 10 ? "0" : "") + index + ".png";
		img = App.getImg(path);

		//ȷ���л��Ĵ�С
		w = img.getWidth();
		h = img.getHeight();
		//ȷ���л���λ��
		x = rd.nextInt(512 - w);
		y = -h;
		//�����ٶ�
		sp = 17 - index;
	}

	//�л��ƶ�����
	public void move() {
		//�����5��
		if (type == 5) {
			x -= 3;
			y += 5;
		} else if (type == 6) {
			x += 3;
			y += 5;
		} else if (type == 15) {
			//����
			y += 8;
		} else {
			y += sp;
		}

	}

	public boolean shootBy(Fire f) {
		boolean hit = x <= f.x + f.w && x > f.x - w && y <= f.y + f.h && y >= f.y - h;
		return hit;
	}

	public boolean hitBy(Hero f) {
		boolean hit = x <= f.x + f.w && x > f.x - w && y <= f.y + f.h && y >= f.y - h;
		return hit;
	}
}
