/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.test;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author guozy
 * @date 2017-6-30
 * 
 */
public class ScreenTest {
	// 截屏测试
	public static void main(String[] args) throws Exception {
		// 控制台标题
		JFrame jf = new JFrame("控制台");
		// 控制台大小
		jf.setSize(500, 400);
		// imag_lab用于存放画面
		JLabel imag_lab = new JLabel();
		jf.add(imag_lab);
		// 设置控制台可见
		jf.setVisible(true);
		// 控制台置顶
		jf.setAlwaysOnTop(true);
		// 控制台退出模式
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		java.awt.Dimension d = jf.size();
		java.awt.Graphics g = jf.getGraphics();
		// 当前屏幕大小
		Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
		java.awt.Dimension dm = tk.getScreenSize();

		// 创建Robot对象
		java.awt.Robot robot = new java.awt.Robot();
		for (int i = 0; i < 1000; i++) {
			// 截取指定大小的屏幕区域
			Rectangle rec = new Rectangle(0, 0, (int) dm.getWidth(),
					(int) dm.getHeight());
			BufferedImage bimage = robot.createScreenCapture(rec);
			// 将图片转为小图片
			BufferedImage littleImage = resize(bimage, imag_lab.getWidth(),
					imag_lab.getHeight());
			// 将图片保存到文件中
			FileOutputStream fous = new FileOutputStream("screenImg”+i+”.jpeg");
			// javax.imageio.ImageIO.write(littleImage, "jpeg", fous);
			fous.flush();
			fous.close();
			// 将小图片显示到界面上
			imag_lab.setIcon(new javax.swing.ImageIcon(littleImage));
			Thread.sleep(50);
		}
	}

	private static BufferedImage resize(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}
}