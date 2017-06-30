/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.test;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guozy
 * @date 2017-6-30
 * 
 */
public class ServerDealObject {

	private static ExecutorService threadPool = Executors.newCachedThreadPool();

	public static void handleOutputStream(final Robot robot,final Rectangle rt,final DataOutputStream dos) {

//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
				try {
					byte[] data = createImage(robot,rt);
					// 发送:
					// 1.先写一个int ,代表图片数据长度
					dos.writeInt(data.length);
					// 2.写入图片字节数据
					dos.write(data);
					dos.flush();
					
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException(e, "server over!");
				}
//			}
//		};
//		threadPool.submit(runnable);
	}

	public static void handleInputStream(final Robot robot,
			final ObjectInputStream ois) {

//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
				try {
					handleEvents(robot, (InputEvent) ois.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
//		};
//		threadPool.submit(runnable);
	}

	private static void handleEvents(Robot robot, InputEvent event) {
		MouseEvent mevent = null; // 鼠标事件
		MouseWheelEvent mwevent = null;// 鼠标滚动事件
		KeyEvent kevent = null; // 键盘事件
		int mousebuttonmask = -100; // 鼠标按键

		System.out.println(event.getID());
		switch (event.getID()) {
		case MouseEvent.MOUSE_MOVED: // 鼠标移动
			mevent = (MouseEvent) event;
			robot.mouseMove(mevent.getX(), mevent.getY());
			break;
		case MouseEvent.MOUSE_PRESSED: // 鼠标键按下
			mevent = (MouseEvent) event;
			robot.mouseMove(mevent.getX(), mevent.getY());
			mousebuttonmask = getMouseClick(mevent.getButton());
			if (mousebuttonmask != -100)
				robot.mousePress(mousebuttonmask);
			break;
		case MouseEvent.MOUSE_RELEASED: // 鼠标键松开
			mevent = (MouseEvent) event;
			robot.mouseMove(mevent.getX(), mevent.getY());
			mousebuttonmask = getMouseClick(mevent.getButton());// 取得鼠标按键
			if (mousebuttonmask != -100)
				robot.mouseRelease(mousebuttonmask);
			break;
		case MouseEvent.MOUSE_WHEEL: // 鼠标滚动
			mwevent = (MouseWheelEvent) event;
			robot.mouseWheel(mwevent.getWheelRotation());
			break;
		case MouseEvent.MOUSE_DRAGGED: // 鼠标拖拽
			mevent = (MouseEvent) event;
			robot.mouseMove(mevent.getX(), mevent.getY());
			break;
		case KeyEvent.KEY_PRESSED: // 按键
			kevent = (KeyEvent) event;
			robot.keyPress(kevent.getKeyCode());
			break;
		case KeyEvent.KEY_RELEASED: // 松键
			kevent = (KeyEvent) event;
			robot.keyRelease(kevent.getKeyCode());
			break;
		default:
			break;

		}

	}

	private static int getMouseClick(int button) { // 取得鼠标按键
		if (button == MouseEvent.BUTTON1) // 左键 ,中间键为BUTTON2
			return InputEvent.BUTTON1_MASK;
		if (button == MouseEvent.BUTTON3) // 右键
			return InputEvent.BUTTON3_MASK;
		return -100;
	}

	// 取得一张屏幕图片,转成字节数组返回
	private static byte[] createImage(Robot robot,Rectangle rt) throws Exception {
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
		javax.imageio.ImageIO.write(image, "jpeg", temB);
		// 做为字节数组返回
		byte[] data = temB.toByteArray();
		return data;
	}

}
