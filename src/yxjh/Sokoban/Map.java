/*
 * Copyright (C) 2020 菟子
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.Sokoban;

/***
 *
 * @author 于子慧
 *
 * 存储地图信息的map数组，一个Map对象，表示一个关卡
 * manX,manY表示人物所在行列数
 */
public class Map {
	int manX = 0;
	int manY = 0;
	byte[][] map;
	int grade;

	public Map(int manX, int manY, byte[][] map) {
		this.manX = manX;
		this.manY = manY;
		int row = map.length;
		int column = map[0].length;
		byte[][] temp = new byte[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				temp[i][j] = map[i][j];
			}
		}
		this.map = temp;//避免直接引用

	}

	public Map(int manX, int manY, byte[][] map, int grade) {
		this(manX, manY, map);
		this.grade = grade;
	}

	public int getManX() {
		return manX;
	}

	public int getManY() {
		return manY;
	}

	public byte[][] getMap() {
		return map;
	}

	public int getGrade() {
		return grade;
	}

}
