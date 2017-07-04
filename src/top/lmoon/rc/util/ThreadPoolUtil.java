/**
 * 
 */
package top.lmoon.rc.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author LMoon
 * @date 2017年7月4日
 *
 */
public class ThreadPoolUtil {
	
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	public static ExecutorService getPool(){
		return threadPool;
	}

}
