package top.lmoon.test;

public class Main {
	
	public static void main(String[] args) {
		if(args.length>0&&"1".equals(args[0])){
			Client.main(args);
		}else{
			Server.main(args);
		}
	}

}
