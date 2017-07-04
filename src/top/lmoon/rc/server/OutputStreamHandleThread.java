/**
 * 
 */
package top.lmoon.rc.server;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import sun.awt.image.codec.JPEGImageEncoderImpl;
import top.lmoon.rc.test.U;
import top.lmoon.rc.util.ErrorHandler;
import top.lmoon.rc.util.MyException;

/**
 * 
 * @author LMoon
 * @date 2017年7月4日
 *
 */
public class OutputStreamHandleThread extends Thread{
	
	public static final int FPS = 20;//设置的dps
	public static final int SPF = 1000/FPS;
	
	private DataOutputStream dos;

	public OutputStreamHandleThread(DataOutputStream dos){
		this.dos = dos;
	}

	@Override
	public void run() {
		try {
			Robot robot = new Robot();
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dm = tk.getScreenSize();
//			 设定区域的大小
			Rectangle rt = new Rectangle(dm);
			dos.writeDouble(dm.getWidth());
			dos.writeDouble(dm.getHeight());
			while(true){
				long begin = System.currentTimeMillis();
				byte[] data = createImage(robot,rt);
				// 发送:
				// 1.先写一个int ,代表图片数据长度
				dos.writeInt(data.length);
				// 2.写入图片字节数据
				dos.write(data);
				dos.flush();
				long interval = System.currentTimeMillis()-begin;
//                U.debug(U.f("time=%d,size=%d", end-begin, data.length));
                if(interval < SPF) {
                    Thread.sleep(SPF - interval);
                }
			}
			
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			ErrorHandler.error(e, "server over!");
		}
	}

	

	// 取得一张屏幕图片,转成字节数组返回
	private byte[] createImage(Robot robot,Rectangle rt) throws Exception {
//		java.awt.Robot robot = new java.awt.Robot();
//		java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
//		java.awt.Dimension dm = tk.getScreenSize();
		// 设定区域的大小
//		Rectangle rt = new Rectangle(0, 0, width, height);
		// 取得指定大小的一张图片
		BufferedImage image = robot.createScreenCapture(rt);
		// 创建一段内存流

		java.io.ByteArrayOutputStream temB = new ByteArrayOutputStream();
		// 将图片数据写入内存流中
//		javax.imageio.ImageIO.write(image, "jpeg", temB);
		JPEGImageEncoderImpl encoder = new JPEGImageEncoderImpl(temB);
		encoder.encode(image);
		// 做为字节数组返回
		byte[] data = temB.toByteArray();
		return data;
	}

}
