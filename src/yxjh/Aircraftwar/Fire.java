/*
 * Copyright (C) <year> <copyright holders>
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

/*
 * 子弹类
 */
public class Fire extends FlyObject {
	int dir; //子弹当前移动方向 0 左上角 1中间 2右上角

	//初始化子弹 hx英雄机横坐标
	public Fire(int hx, int hy, int dir) {
		//确定子弹图片
		img = App.getImg("/img/fire.png");
		//确定子弹大小
		w = img.getWidth() / 4;
		h = img.getHeight() / 4;
		//确定子弹的位置  （初始位置在飞机上）
		x = hx;
		y = hy;
		this.dir = dir;
	}

	//子弹移动方法
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
