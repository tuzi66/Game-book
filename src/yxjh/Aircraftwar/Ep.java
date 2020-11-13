/*
 * Copyright (C) <year> <copyright holders>
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Aircraftwar;

import java.util.Random;

/*
 * 敌机类
 * 分析：
 * 敌机数量很多
 * 敌机数量未知
 *
 */
public class Ep extends FlyObject {
	int sp; //敌机速度
	int hp;
	int type;

	//给敌机定性
	public Ep() {
		//定义随机数
		Random rd = new Random();
		//s生成随机数选图片
		int index = rd.nextInt(15) + 1;
		//设置类型
		type = index;
		//确定敌机显示的图片
		String path = "/img/ep" + (index < 10 ? "0" : "") + index + ".png";
		img = App.getImg(path);

		//确定敌机的大小
		w = img.getWidth();
		h = img.getHeight();
		//确定敌机的位置
		x = rd.nextInt(512 - w);
		y = -h;
		//设置速度
		sp = 17 - index;
	}

	//敌机移动方法
	public void move() {
		//如果是5号
		if (type == 5) {
			x -= 3;
			y += 5;
		} else if (type == 6) {
			x += 3;
			y += 5;
		} else if (type == 15) {
			//导弹
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
