/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author guozy
 * @date 2017-6-30
 * 
 */
public class ClientDealObject extends Thread {
	public int x, y;
	private java.io.DataInputStream dins;
	private java.io.ObjectOutputStream ous;
	private javax.swing.JLabel la_image = new javax.swing.JLabel();
	
	private static int widthRate = 1;
	private static int heightRate = 1;

	public void showUI() {
		javax.swing.JFrame frame = new javax.swing.JFrame("远程控制");
		frame.setSize(800, 600);
		frame.setResizable(false);
//		la_image.setSize(800, 600);

//		JLayeredPane jlp = new JLayeredPane();
		frame.add(la_image);

//		frame.setLayeredPane(jlp);

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
		frame.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {

				sendEventObject(e);
			}

			public void mouseMoved(MouseEvent e) {

				sendEventObject(e);

			}

		});
		frame.addMouseListener(new MouseListener() {

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
		java.net.Socket sc = new java.net.Socket(ip, port);
		dins = new DataInputStream(sc.getInputStream());

		ous = new ObjectOutputStream(sc.getOutputStream());

		if (dins == null || ous == null)
			throw new MyException(null,"远程控制不接受或未接受被控制。。。");
	}

	// 发送事件对象到被控制端
	private void sendEventObject(java.awt.event.InputEvent event) {

		try {
//			if(event instanceof MouseEvent){
//				Point p = ((MouseEvent) event).getPoint();
//				p.x = p.x/widthRate;
//				p.y = p.y/heightRate;
//			}
//			ous.write(widthRate);
//			ous.write(heightRate);
			ous.writeInt(widthRate);
			ous.writeInt(heightRate);
			ous.writeObject(event);
		} catch (Exception ef) {
			ef.printStackTrace();
		}

	}

	public void run() {
		try {

			while (true) {
				int len = dins.readInt();
				byte[] data = new byte[len];
				dins.readFully(data);
				ByteArrayInputStream bins = new ByteArrayInputStream(data);
				BufferedImage image = ImageIO.read(bins);
				javax.swing.ImageIcon ic = new ImageIcon(image);

				Image img = ic.getImage();
//				Toolkit tk = Toolkit.getDefaultToolkit();
//				Dimension d = tk.getScreenSize();
//
//				int w = d.width;
//				int h = d.height;
				BufferedImage bi = resize(img, la_image.getWidth(), la_image.getHeight());

				la_image.setIcon(new ImageIcon(bi));
				la_image.repaint();// 销掉以前画的背景

			}
		} catch (Exception ef) {
			System.out.println("网络故障：无法读出远程图片数据。。。");
			ef.printStackTrace();
		}

	}

	private static BufferedImage resize(Image img, int newW, int newH) {
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		widthRate = w/newW;
		heightRate = h/newH;
		BufferedImage dimg = new BufferedImage(newW, newH,
				BufferedImage.TYPE_INT_BGR);
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	public static void main(String[] args) throws Exception {

	}

	public static void connect(String ip, int port) throws Exception {
		ClientDealObject ct = new ClientDealObject();
		ct.showUI();
		ct.conn2Server(ip, port);

		ct.start();
	}

}
