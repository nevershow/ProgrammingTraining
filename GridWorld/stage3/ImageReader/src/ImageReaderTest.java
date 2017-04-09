import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import junit.framework.TestCase;

public class ImageReaderTest extends TestCase {
	public void testImageReader() throws IOException {
		test("1");
		test("2");
	}
	
	/*
	 * Test methods: myRead, showChanelR, showChanelG, showChanelB, showGray
	 */
	private void test(String testcase) throws IOException {
		System.err.println("Test " + testcase + ".bmp......");
		ImplementImageIo io =  new ImplementImageIo();
		ImplementImageProcessor pro = new ImplementImageProcessor();
		Image mine = io.myRead("bmptest/" + testcase + ".bmp");
		assertEquals(true, compareImage(ImageIO.read(new File("bmptest/"+testcase+".bmp")), mine));
		assertEquals(true, compareImage(ImageIO.read(new File("bmptest/goal/"+testcase+"_red_goal.bmp")), pro.showChanelR(mine)));
		assertEquals(true, compareImage(ImageIO.read(new File("bmptest/goal/"+testcase+"_green_goal.bmp")), pro.showChanelG(mine)));
		assertEquals(true, compareImage(ImageIO.read(new File("bmptest/goal/"+testcase+"_blue_goal.bmp")), pro.showChanelB(mine)));
		assertEquals(true, compareImage(ImageIO.read(new File("bmptest/goal/"+testcase+"_gray_goal.bmp")), pro.showGray(mine)));
		System.err.println("Pass Test " + testcase + "!");
	}
	
	/*
	 * compare the processed image with the goal image.
	 * If two file have different width or height, or some pixel is different, return false;
	 * Otherwise return true.
	 */
	private boolean compareImage(Image goal, Image mine) {
		BufferedImage bi1 = new BufferedImage(goal.getWidth(null), goal.getHeight(null), BufferedImage.TYPE_INT_RGB);
		BufferedImage bi2 = new BufferedImage(mine.getWidth(null), mine.getHeight(null), BufferedImage.TYPE_INT_RGB);
		if (bi1.getWidth() != bi2.getWidth() || bi1.getHeight() != bi2.getHeight()) {
			System.err.println("Two bmp files have different width or height!");
			return false;
		}
		for (int i = bi1.getHeight() - 1; i >= 0; --i) 
			for (int j = 0; j < bi1.getWidth(); ++j)
				if (bi1.getRGB(j, i) != bi2.getRGB(j, i)) {
					System.err.println("pixel (" + j + "," + i + ") is defferent!");
					return false;
				}
		return true;
	}
}
