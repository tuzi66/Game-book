/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

public class Hero extends FlyObject {
	//血量
	int hp;

	//构造方法 确定长什么样子 （特点）
	public Hero() {
		//游戏开始时 显示的图片
		img = App.getImg("/img/hero.png");
		//使用和纵坐标 确定位置
		x = 200;
		y = 500;
		//确定大小
		//通过图片确定飞机的大小
		w = img.getWidth();
		h = img.getHeight();

		hp = 3;
	}

	//英雄机移动到鼠标上的方法
	public void moveToMouse(int mx, int my) {
		x = mx - (w / 2);
		y = my - (h / 2);
	}

	public void moveUp() {
		//向上移动
		y -= 20;
	}

	public void moveDown() {
		//向下移动
		y += 20;
	}

	public void moveLeft() {
		//向左移动
		x -= 20;
	}

	public void moveRight() {
		//向右移动
		x += 20;
	}
}
