/**
 * 
 */
package top.lmoon.rc.server;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

import top.lmoon.rc.util.ErrorHandler;

/**
 * 
 * @author LMoon
 * @date 2017年7月4日
 *
 */
public class InputStreamHandleThread extends Thread {

	private ObjectInputStream ois;

	public InputStreamHandleThread(ObjectInputStream ois) {
		this.ois = ois;
	}

	@Override
	public void run() {
		try {
			Robot robot = new Robot();
			while (true) {
				handleEvents(robot);
			}

		} catch (ClassNotFoundException e) {
			ErrorHandler.error(e);
		} catch (IOException e) {
			ErrorHandler.error(e);
		} catch (AWTException e) {
			ErrorHandler.error(e);
		}
	}

	private void handleEvents(Robot robot) throws ClassNotFoundException, IOException {
		InputEvent event = (InputEvent) ois.readObject();
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
			// robot.mouseMove(mevent.getX(), mevent.getY());
			mousebuttonmask = getMouseClick(mevent.getButton());
			if (mousebuttonmask != -100)
				robot.mousePress(mousebuttonmask);
			break;
		case MouseEvent.MOUSE_RELEASED: // 鼠标键松开
			mevent = (MouseEvent) event;
			// robot.mouseMove(mevent.getX(), mevent.getY());
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

}
