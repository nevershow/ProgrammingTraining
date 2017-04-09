import imagereader.IImageProcessor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImplementImageProcessor implements IImageProcessor{
	private static final int R_MASK = 0xffff0000;
	private static final int G_MASK = 0xff00ff00;
	private static final int B_MASK = 0xff0000ff;
	
	/*
	 * chanel filter: R G B or gray
	 */
	private Image filter(Image img, int chanel) {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics gh = bi.createGraphics();
		gh.drawImage(img, 0, 0, null);
		gh.dispose();
		
		int w = bi.getWidth();
		int h = bi.getHeight();
		int rgb;
		for (int i = h - 1; i >= 0; --i) 
			for (int j = 0; j < w; ++j) {
				rgb = bi.getRGB(j, i);
				if (chanel == 0)
					bi.setRGB(j, i, getGray(rgb));
				else
					bi.setRGB(j, i, rgb & chanel);
			}

		return bi;
	}
	
	/*
	 *  calculate gray from rgb
	 */
	private int getGray(int rgb) {
		int tmp = rgb & 0x00ffffff;  // set alpha to 0
		int gray = (int)(((tmp & R_MASK) >> 16) * 0.299 + ((tmp & G_MASK) >> 8) * 0.587 + (tmp & B_MASK) * 0.114);
		return (rgb & 0xff000000) + (gray << 16) + (gray << 8) + gray;
	}
	
	public Image showChanelR(Image img) {
		return filter(img, R_MASK);
	}
	
	public Image showChanelG(Image img) {
		return filter(img, G_MASK);
	}
	
	public Image showChanelB(Image img) {
		return filter(img, B_MASK);
	}
	
	public Image showGray(Image img) {
		return filter(img, 0);
	}
}