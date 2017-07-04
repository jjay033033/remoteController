package top.lmoon.rc.test;

public final class U {
    
    public static String f(String str, Object ...os) {
        return String.format(str, os);
    }
    public static void debug(Object message) {
        System.out.println("DEBUG:"+message.toString());
    }
    public static void info(Object message) {
        System.out.println("INFO :"+message.toString());
    }
    public static void error(Object message) {
        System.err.println("ERROR:"+message.toString());
    }
}
