package yq;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class DataChange implements Runnable{
	 //阻塞队列
    private ArrayBlockingQueue<Double>  bt1Time;
    private ArrayBlockingQueue<Double>  bt2Time;
    private ArrayBlockingQueue<Double>  bt1Phase;
    private ArrayBlockingQueue<Double>  bt2Phase;
    
    //转存数组
	private ArrayList<Double> t1Time;
	private ArrayList<Double> t1Phase;
	private ArrayList<Double> t2Time;
	private ArrayList<Double> t2Phase;
	
	//数据计数
	private int count1;
	private int count2;
	public DataChange() {}
	
	public DataChange(int count1,int count2,ArrayBlockingQueue<Double> bt1Time, ArrayBlockingQueue<Double> bt1Phase,ArrayBlockingQueue<Double> bt2Time, ArrayBlockingQueue<Double> bt2Phase) {
		this.count1=count1;
		this.count2=count2;
		this.bt1Time=bt1Time;
		this.bt1Phase=bt1Phase;
		this.bt2Time=bt2Time;
		this.bt2Phase=bt2Phase;
		t1Time=new ArrayList<Double>(count1);
		t1Phase=new ArrayList<Double>(count1);
		t2Time=new ArrayList<Double>(count2);
		t2Phase=new ArrayList<Double>(count2);
		fun();
	}
	
	@Override
	public void run() {
		System.out.println("oh ho; size: "+bt1Time.size());
		for(int i=0;i<count1;i++) {
			if(bt1Time.size()!=0) {
				try {
					t1Time.add(bt1Time.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bt1Phase.size()!=0) {
				try {
					t1Phase.add(bt1Phase.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(int i=0;i<count2;i++) {
			if(bt2Time.size()!=0) {
				try {
					t2Time.add(bt2Time.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bt2Phase.size()!=0) {
				try {
					t2Phase.add(bt2Phase.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(int i=0;i<t1Time.size();i++) {
			System.out.print(" "+i+" "+t1Time.get(i)+" ");
		}
		new Thread(new DataDispose(count1,count2,t1Time,t1Phase,t2Time,t2Phase)).start();
//		notifyAll();
		
	}
	
	public  void fun() {
		System.out.println("oh ho; size: "+bt1Time.size());
		for(int i=0;i<count1;i++) {
			if(bt1Time.size()!=0) {
				try {
					t1Time.add(bt1Time.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bt1Phase.size()!=0) {
				try {
					t1Phase.add(bt1Phase.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(int i=0;i<count2;i++) {
			if(bt2Time.size()!=0) {
				try {
					t2Time.add(bt2Time.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bt2Phase.size()!=0) {
				try {
					t2Phase.add(bt2Phase.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(int i=0;i<t1Time.size();i++) {
			System.out.print(" "+i+" "+t1Time.get(i)+" ");
		}
		new DataDispose(count1,count2,t1Time,t1Phase,t2Time,t2Phase,1);
//		new Thread(new DataDispose(count1,count2,t1Time,t1Phase,t2Time,t2Phase)).start();
//		notifyAll();
		
	}
}
