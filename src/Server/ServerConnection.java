package Server;

import BasicMethod.*;
import java.io.*;
import java.net.*;

public class ServerConnection implements Runnable{
	BasicWriteAndRead bwr=new BasicWriteAndRead();
	public static String warning="Password is incorrect";
	Socket s;
	ServerConnection(Socket s){
		this.s=s;
	}
	
		@Override
	public void run() {
			try {
				boolean flag=true;
				
				while(flag){
					bwr.sendMessage(s,"what's your username?");
					String un=bwr.receiveMessage(s);
					System.out.println("the username is:  "+un);
					if(ServerTest.ul.hasKey(un)){
						
						
							bwr.sendMessage(s,"username is correct,what's your password?");
							String pw=bwr.receiveMessage(s);
							if(ServerTest.ul.passwordRight(un,pw)){
								if(ServerTest.ul.m.get(un).status==1){
									bwr.sendMessage(s, "you are online already");
									
								}
								else{
									bwr.sendMessage(s,"welcome !!!! \n");
									ServerTest.ul.m.get(un).status=1;
									synchronized(this){
										ServerTest.count++;
										ServerTest.allUser++;
									}
									
									ServerQuery sq =new ServerQuery(s);
									sq.respondToQuery(un);
									flag=false;
								}
							}
							else{
								bwr.sendMessage(s,warning);
								
								
							}
						
					}
					else{
						bwr.sendMessage(s,"the username doesn't exist");
					}
				}
				
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
		
}
