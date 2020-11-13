/*
 * Copyright (C) <year> <copyright holders>
 *
 * 　　Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 　　
 * 　　The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package yxjh.puzzle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtil {

	public static boolean cutImage(File sourcePath, int cutNumber, String savePath) {
		try {
			BufferedImage image = ImageIO.read(sourcePath);
			int allWidth = image.getWidth();
			int allHeight = image.getHeight();
			int width = allWidth / cutNumber;
			int height = allHeight / cutNumber;
			for (int i = 0; i < cutNumber; i++) {
				for (int j = 0; j < cutNumber; j++) {
					ImageIO.write(image.getSubimage(j * width, i * height, width, height), "jpg",
							new File(savePath + "\\" + (i * cutNumber + j) + ".jpg"));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		ImageUtil.cutImage(new File("img\\type1\\index.jpg"), 3, "img\\type1\\5");
	}

}
