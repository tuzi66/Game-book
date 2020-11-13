/*
 * Copyright (C) <year> <copyright holders>
 *
 * ����Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * ����
 * ����The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

/*
 * �ӵ���
 */
public class Fire extends FlyObject {
	int dir; //�ӵ���ǰ�ƶ����� 0 ���Ͻ� 1�м� 2���Ͻ�

	//��ʼ���ӵ� hxӢ�ۻ�������
	public Fire(int hx, int hy, int dir) {
		//ȷ���ӵ�ͼƬ
		img = App.getImg("/img/fire.png");
		//ȷ���ӵ���С
		w = img.getWidth() / 4;
		h = img.getHeight() / 4;
		//ȷ���ӵ���λ��  ����ʼλ���ڷɻ��ϣ�
		x = hx;
		y = hy;
		this.dir = dir;
	}

	//�ӵ��ƶ�����
	public void move() {
		if (dir == 0) {
			x -= 1;
			y -= 10;
		} else if (dir == 1) {
			y -= 10;
		} else {
			x += 1;
			y -= 10;
		}


	}
}
