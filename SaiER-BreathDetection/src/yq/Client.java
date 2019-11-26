package yq;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Client {
	
	private double timeWindow;
	private Socket socket;
	 //阻塞队列
    private ArrayBlockingQueue<Double>  bt1Time;
    private ArrayBlockingQueue<Double>  bt2Time;
    private ArrayBlockingQueue<Double>  bt1Phase;
    private ArrayBlockingQueue<Double>  bt2Phase;
    
	public Client() {}
	public Client(double timeWindow,Socket socket,ArrayBlockingQueue<Double>  bt1Time, ArrayBlockingQueue<Double>  bt1Phase, ArrayBlockingQueue<Double>  bt2Time, ArrayBlockingQueue<Double>  bt2Phase) {
		this.bt1Phase=bt1Phase;
		this.bt1Time=bt1Time;
		this.bt2Phase=bt2Phase;
		this.bt2Time=bt2Time;
		this.socket=socket;
	}
	
	/*生产者消费者模式*/
	public static void main(String[] args) throws UnknownHostException, IOException {
		//画图界面
		PlaintTest jz = new PlaintTest();
        JFrame frame = new JFrame();
        frame.setSize(900, 500);
      
        frame.getContentPane().add(jz.getCPUJFreeChart(), BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // 窗口居于屏幕正中央
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		/*画图界面*/
        
        //阻塞队列
        ArrayBlockingQueue<Double>  bt1Time=new ArrayBlockingQueue<>(5000);
        ArrayBlockingQueue<Double>  bt2Time=new ArrayBlockingQueue<>(5000);
        ArrayBlockingQueue<Double>  bt1Phase=new ArrayBlockingQueue<Double>(5000);
        ArrayBlockingQueue<Double>  bt2Phase=new ArrayBlockingQueue<>(5000);
        

		/*Socket  */
		Socket socket=new Socket();
		//设置超时时间
		socket.setSoTimeout(1000000);
		//连接服务器，端口8008，超时时间10000
//		socket.connect(new InetSocketAddress(InetAddress.getByName("2001:da8:270:2021:0:0:0:88"),8008), 50000);
//		System.setProperty("java.net.preferIPv6Addresses","true");
		socket.connect(new InetSocketAddress(Inet6Address.getLocalHost(),8008), 1000000);

		System.out.println("已经发送服务器连接，并进入后续流程");
		System.out.println("客户端信息： "+socket.getLocalAddress()+" 端口："+socket.getLocalPort());
		System.out.println("服务器信息： "+socket.getInetAddress()+" 端口： "+socket.getPort());
		
		double timeWindow=4;//处理数据的时间窗口
		
		try {
			//发送数据
//			 new Thread(new Client1111(timeWindow,socket,bt1Time,bt1Phase,bt2Time,bt2Phase)).start();
			todo(timeWindow,socket,bt1Time,bt1Phase,bt2Time,bt2Phase);
//			new Thread(new DataDispose(timeWindow,bt1Time,bt1Phase,bt2Time,bt2Phase)).start();
			
//			cl.run(socket, timeWindow, datadispose, bt1Time, bt1Phase, bt2Time, bt2Phase);
//			datadispose.run(timeWindow,bt1Time, bt1Phase, bt2Time, bt2Phase);
		}catch(Exception e){
			System.out.println("异常关闭");
		}
		//释放资源
		socket.close();
		System.out.println("客户端已退出");
		
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			todo(timeWindow,socket,bt1Time,bt1Phase,bt2Time,bt2Phase);
		} catch (NumberFormatException e) {
			System.out.println("数据转换失败");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOEXCEPTION in todo()");
			e.printStackTrace();
		}
	}
	
	private static void todo(double timeWindow,Socket socket,ArrayBlockingQueue<Double> bt1Time, ArrayBlockingQueue<Double> bt1Phase, ArrayBlockingQueue<Double> bt2Time, ArrayBlockingQueue<Double> bt2Phase) throws NumberFormatException, IOException  {
		

		//构建键盘输入流
		InputStream in=System.in;
		BufferedReader input=new BufferedReader(new InputStreamReader(in));
		
		//得到的Socket输出流，并转换为打印流
		OutputStream outputStream = null;
		try {
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("出错1： ");
		}
		PrintStream socketPrintStream=new PrintStream(outputStream);
		
		//得到socket输入流，并转换为BufferedReader
		InputStream inputStream = null;
		try {
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			System.out.println("出错2");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader socketBufferedReader=new BufferedReader(new InputStreamReader(inputStream));
		
		boolean flag=true;
		do {
		//键盘读取一行
		String str = null;
		try {
			str = input.readLine();
		} catch (IOException e) {
			System.out.println("出错3");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//发送到服务器
		socketPrintStream.println(str);
		
		//从服务器读取一行
//		String echo=socketBufferedReader.readLine();
//		if("bye".equalsIgnoreCase(echo)) {
//			flag=false;
//		}else {
//			
//			System.out.println(echo);
//		}
		System.out.println("---------");
		//接收第一句废话
		String tempstr="";
		if((tempstr=socketBufferedReader.readLine())!="bye") {
			System.out.println(tempstr);
		}
		String echo="";
		int k=1;//记录达到时间处理窗口的次数
		int count1=0;
		int count2=0;
		while((echo=socketBufferedReader.readLine())!="bye"){
			
			String[] temp=echo.split(" ");
			int tempAntanna=Integer.valueOf(temp[0]);
			double tempTime=Double.valueOf(temp[1]);
			double tempPhase=Double.valueOf(temp[2]);
//			 synchronized(bt1Time){
//                 //给队列加锁,保证线程安全
			//数据分组
			if(tempAntanna==1) {
				count1+=1;
				bt1Time.add(tempTime);
				bt1Phase.add(tempPhase);

			}else {
				count2+=1;
				bt2Time.add(tempTime);
				bt2Phase.add(tempPhase);
//				
			}
			if(tempTime/timeWindow>k) {//一次处理
				System.out.println("todo 一次处理");
				k+=1;
				//数据交换线程
//				try {
//					bt1Time.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				System.out.println("count: "+count1+" size: "+bt1Time.size());

				new DataChange(count1,count2,bt1Time,bt1Phase,bt2Time,bt2Phase);
				 count1=0;
				 count2=0;
			}
			 }
//				
//			System.out.println(echo);
//		}
		flag=false;
		
		}while(flag);
	}

}
