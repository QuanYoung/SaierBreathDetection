package yq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	 public static void main(String[] args) throws IOException {
		 ServerSocket server=new ServerSocket(8008);
		 
		 System.out.println();
		 System.out.println("服务器准备就绪");
		 System.out.println("服务器信息： "+server.getInetAddress()+" 端口："+server.getLocalPort());
		 
		 //等待客户端连接
		 for(;;) {
			 //得到客户端
			 Socket client=server.accept();
			 //客户端构建异步线程
			 ClientHandler clientHandler=new ClientHandler(client);
			 //启动线程
			 clientHandler.start();
		 }
	 }
	private static class ClientHandler extends Thread{
		private Socket socket;
		private boolean flag=true;
		ClientHandler(Socket socket){
			this.socket=socket;
		}
		@Override
		public void run() {
			super.run();
			System.out.println("新客户端连接： "+socket.getInetAddress()
								+"端口： "+socket.getPort());
			
			try {
				//得到打印流
				PrintStream socketOutput=new PrintStream(socket.getOutputStream());
				//得到输入流，用于接收数据
						BufferedReader socketInput=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				do {
					//客户端拿到一条数据
					String str=socketInput.readLine();
					if("bye".equalsIgnoreCase(str)) {
						flag=false;
						//回送
						socketOutput.println("bye");
						
					}else if("start".equalsIgnoreCase(str)){//开启阅读器
						//开启阅读器，打印到屏幕，并回送数据
						System.out.println(str);
						//反馈数据
						socketOutput.println("开启阅读器，开始读取数据 ");
						//开启阅读器
						ReadTagsMessage.readTags(socketOutput);
					}
				}while(flag);
				
				socketInput.close();
				socketOutput.close();
				
			}catch(Exception e) {
				System.out.println("连接异常断开");
			}finally {
				
				//连接关闭
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("客户端已退出： "+socket.getInetAddress()
			+"端口： "+socket.getPort());
		}
	}
}
