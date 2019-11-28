package yq;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	 public static void main(String[] args) throws IOException {
		 ServerSocket server=new ServerSocket(8006);
		 
		 System.out.println();
		 System.out.println("服务器准备就绪");
		 System.out.println("服务器信息： "+server.getInetAddress()+" 端口："+server.getLocalPort());
		 
		 
		//读取文件 用户ID-->IPv6地址-->阅读器地址和两个标签EPC
		 String arr1[][]=Utils.File2ArrayIDtoIPv6(new File("E:\\Person-IPv6Address.txt"));
		 String arr2[][]=Utils.File2ArrayIpv6toUserInfo(new File("E:\\IPv6Address-ReaderAddressAndTagEPC.txt"));
		 //IPv6地址-->阅读器地址和两个标签EPC
		 ConcurrentHashMap<String, UserInfo> chmAddress=new ConcurrentHashMap<String, UserInfo>();
		 //用户ID与IPv6地址键值对
		 ConcurrentHashMap<String, String> chmUser=new ConcurrentHashMap<String, String>();
		 
		 for(int i=0;i<arr1.length;i++) {
			 chmUser.put(arr1[i][0], arr1[i][1]);
		 }
		 for(int i=0;i<arr2.length;i++) {
			 chmAddress.put(arr2[i][0], new UserInfo(arr2[i][1],arr2[i][2],arr2[i][3]));
		 }
		 
//		 for(int i=0;i<arr1.length;i++) {
//			 System.out.println(arr1[i][0]+" "+arr1[i][1]);
//			 System.out.println(chmUser.get(arr1[i][0]));;
//		 }
//		 for(int i=0;i<arr2.length;i++) {
//			 System.out.println(arr2[i][0]+" "+arr2[i][1]+" "+arr2[i][2]);
//			 System.out.println(chmAddress.get(arr2[i][0]).getReaderAddress()+chmAddress.get(arr2[i][0]).getTag1EPC()+chmAddress.get(arr2[i][0]).getTag2EPC());;
//		 }
		 //等待客户端连接
		 for(;;) {
			 //得到客户端
			 Socket client=server.accept();
			 //客户端构建异步线程
			 ClientHandler clientHandler=new ClientHandler(client,chmAddress,chmUser);
			 //启动线程
			 clientHandler.run();
		 }
	 }
	private static class ClientHandler {
		private Socket socket;
		private ConcurrentHashMap<String, UserInfo> chmAddress;
		private ConcurrentHashMap<String, String> chmUser;
		private boolean flag=true;
		ClientHandler(Socket socket, ConcurrentHashMap<String, UserInfo> chmAddress, ConcurrentHashMap<String, String> chmUser){
			this.socket=socket;
			this.chmAddress=chmAddress;
			this.chmUser=chmUser;
		}
		
		public void run() {
//			super.run();
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
					if(chmUser.containsKey(str)) {
						String ipv6Address=chmUser.get(str);
						UserInfo userInfo=chmAddress.get(ipv6Address);
						//开启阅读器，打印到屏幕，并回送数据
						System.out.println(str);
						//反馈数据
						socketOutput.println("开启阅读器，开始读取数据 ");
						//开启阅读器
						ReadTagsMessage.readTags(socketOutput,userInfo.getReaderAddress(),userInfo.getTag1EPC(),userInfo.getTag2EPC());
					}else {
						socketOutput.println("该用户不存在");
					}
//					if("bye".equalsIgnoreCase(str)) {
//						flag=false;
//						//回送
//						socketOutput.println("bye");
//						
//					}else if("start".equalsIgnoreCase(str)){//开启阅读器
//						//开启阅读器，打印到屏幕，并回送数据
//						System.out.println(str);
//						//反馈数据
//						socketOutput.println("开启阅读器，开始读取数据 ");
//						//开启阅读器
//						ReadTagsMessage.readTags(socketOutput);
//					}
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
