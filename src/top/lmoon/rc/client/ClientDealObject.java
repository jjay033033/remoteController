/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.rc.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import top.lmoon.rc.util.ErrorHandler;
import top.lmoon.rc.util.MyException;
import top.lmoon.rc.util.ThreadPoolUtil;

/**
 * 
 * @author LMoon
 * @date 2017年7月4日
 *
 */
public class ClientDealObject extends Thread {
//	public int x, y;
	private DataInputStream dis;
	private ObjectOutputStream oos;

	private JFrame frame;
	private JLabel la_image = new JLabel();

	public void showUI() {
		frame = new JFrame("远程控制");
		frame.setSize(800, 600);
		// frame.setResizable(false);
		// la_image.setSize(800, 600);

		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout());
		pane.add(la_image);
		JScrollPane scrollPane = new JScrollPane(pane);
		frame.add(scrollPane);

		// frame.setLayeredPane(jlp);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
		frame.setAlwaysOnTop(true);

		frame.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				sendEventObject(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				sendEventObject(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

		});

		frame.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				sendEventObject(e);

			}

		});
		la_image.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {

				sendEventObject(e);
			}

			public void mouseMoved(MouseEvent e) {
				if (Math.random() > 0.5)
					sendEventObject(e);

			}

		});
		la_image.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

				sendEventObject(e);

			}

			public void mouseEntered(MouseEvent e) {

				sendEventObject(e);
			}

			public void mouseExited(MouseEvent e) {

				sendEventObject(e);
			}

			public void mousePressed(MouseEvent e) {

				sendEventObject(e);
			}

			public void mouseReleased(MouseEvent e) {

				sendEventObject(e);
			}

		});

	}

	private void conn2Server(String ip, int port) throws MyException, Exception {
		Socket sc = new Socket(ip, port);
		dis = new DataInputStream(sc.getInputStream());
		oos = new ObjectOutputStream(sc.getOutputStream());

		if (dis == null || oos == null)
			throw new MyException(null, "远程控制不接受或未接受被控制。。。");
	}

	// 发送事件对象到被控制端
	private void sendEventObject(java.awt.event.InputEvent event) {

		try {
			oos.writeObject(event);
		} catch (Exception ef) {
			ErrorHandler.error(ef);
		}

	}

	@SuppressWarnings("deprecation")
	public void run() {
		try {
			double serverWidth = dis.readDouble();
			double serverHeight = dis.readDouble();
			updateSize(serverWidth, serverHeight);
			while (true) {
				int len = dis.readInt();
				byte[] data = new byte[len];
				dis.readFully(data);
				// ByteArrayInputStream bins = new ByteArrayInputStream(data);
				// BufferedImage image = ImageIO.read(bins);
				// ImageIcon ic = new ImageIcon(image);

				// Image img = ic.getImage();
				// BufferedImage bi = resize(img, la_image.getWidth(),
				// la_image.getHeight());

				la_image.setIcon(new ImageIcon(data));
				frame.repaint();// 销掉以前画的背景

			}
		} catch (Exception ef) {
			ErrorHandler.error(ef,"服务器已关闭或网络故障：无法读出远程图片数据!",frame);
			System.exit(0);;
		}

	}

	// private static BufferedImage resize(Image img, int newW, int newH) {
	// int w = img.getWidth(null);
	// int h = img.getHeight(null);
	// BufferedImage dimg = new BufferedImage(newW, newH,
	// BufferedImage.TYPE_INT_BGR);
	// Graphics2D g = dimg.createGraphics();
	// g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	// g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
	// g.dispose();
	// return dimg;
	// }

	public static void connect(String ip, int port) throws Exception {
		ClientDealObject ct = new ClientDealObject();
		ct.showUI();
		ct.conn2Server(ip, port);
		ThreadPoolUtil.getPool().submit(ct);
	}

	public void updateSize(double serverWidth, double serverHeight) {
		Dimension clientSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = clientSize.getWidth() > (serverWidth+30)?(serverWidth+30):clientSize.getWidth();
		double height = clientSize.getHeight() > (serverHeight+55)?(serverHeight+55):clientSize.getHeight();
		frame.setSize((int) width, (int) height);
	}

}
