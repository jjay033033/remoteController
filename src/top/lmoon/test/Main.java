package top.lmoon.test;

import top.lmoon.rc.client.Client;
import top.lmoon.rc.server.Server;

public class Main {
	
	public static void main(String[] args) {
		if(args.length>0&&"1".equals(args[0])){
			Client.main(args);
		}else{
			Server.main(args);
		}
	}

}
