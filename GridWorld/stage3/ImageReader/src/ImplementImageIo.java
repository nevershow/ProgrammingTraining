import imagereader.IImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImplementImageIo implements IImageIO {
	private static final int BMP_HEAD = 14; // bmp head has 14 byte, 0~13
	private static final int BMP_INFO = 40; // bmp info has 40 byte, 14~53
	private int biWidth;
	private int biHeight;
	private int[] pixels;

	/*
	 *  convert several bytes to integer 
	 */
	private int bytes2int(byte[] b, int len, int offset) {
		int word = 0;
		for (int i = 0; i < len; i++)
			// fill first 3 bytes with 0
			word += ((b[offset + i] & 0xff) << (i * 8));
		return word;
	}

	/*
	 *  read a bmp file without use Java API
	 */
	public Image myRead(String filepath) throws IOException {
		int ct;
		FileInputStream in = new FileInputStream(filepath);
		byte[] bmphead = new byte[BMP_HEAD];
		ct = in.read(bmphead);
		byte[] bmpinfo = new byte[BMP_INFO];
		ct = in.read(bmpinfo);

		try {
			if (bmpinfo[14] != 24)
				throw new UnsupportedOperationException("The image isn't a 24-bit(true color) bitmap file!");
			biWidth = bytes2int(bmpinfo, 4, 4);
			biHeight = bytes2int(bmpinfo, 4, 8);
			pixels = new int[biWidth * biHeight];

			// one pixel stores in 3 bytes: R G B
			int fillbyte = (4 - biWidth * 3 % 4) % 4;
			byte[] fill = new byte[fillbyte];
			byte[] rgb = new byte[] { 0, 0, 0 };

			for (int i = biHeight - 1; i >= 0; --i, ct = in.read(fill))
				for (int j = 0; j < biWidth; ++j) {
					ct = in.read(rgb);
					// the highest byte stands for the opacity
					pixels[i * biWidth + j] = bytes2int(rgb, 3, 0) | (0xff << 24);
				}
		} catch (Exception e) {
			System.err.println(e);
			return null;
		} finally {
			in.close();
		}
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(biWidth, biHeight, pixels, 0, biWidth));
	}

	/*
	 *  convert Image to BufferedImage and write it into a file
	 */
	public Image myWrite(Image img, String filepath) throws IOException {
		try {
			File outFile = new File(filepath + ".bmp");
			BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics gh = bi.createGraphics();
			gh.drawImage(img, 0, 0, null);
			gh.dispose();
			ImageIO.write(bi, "bmp", outFile);
		} catch (Exception e) {
			System.err.println(e);
		}
		return img;
	}
}
