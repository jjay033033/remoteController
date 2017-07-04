/**
 * 
 */
package top.lmoon.rc.util;

import javax.swing.JOptionPane;

/**
 * @author LMoon
 * @date 2017年7月4日
 * 
 */
public class ErrorHandler {

	public static void error(Exception e) {
		if (e != null) {
			e.printStackTrace();
		}
	}

	public static void errorDialog(String info) {
		if (info != null) {
			System.out.println(info);
			JOptionPane.showMessageDialog(null, info, "提示", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void error(Exception e, String info) {
		errorDialog(info);
		error(e);
	}

}
