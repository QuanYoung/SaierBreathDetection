package yq;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import com.mathworks.toolbox.javabuilder.MWException;

public class getTagMessage implements TagReportListener{
	String EPC1;
	String EPC2;
	PrintStream socketOutput;
	boolean flag;
	double initialtime;

	 ArrayList<Double> t1Time;
	 ArrayList<Double> t1Phase;
	 ArrayList<Double> t2Time;
	 ArrayList<Double> t2Phase;
	 ArrayList<Double> tTime;
	 ArrayList<Double> tPhase;
	 double tWindow;
	
	public getTagMessage(){
	}
	public getTagMessage(String EPC1,String EPC2,double ini,boolean fl,PrintStream socketOutput){
		this.EPC1=EPC1;
		this.EPC2=EPC2;
		this.initialtime=ini;
		this.flag=fl;
		this.socketOutput=socketOutput;
	}
	public getTagMessage(String EPC1,String EPC2,boolean fl,double ini,double tWindow, ArrayList<Double> t1Time, ArrayList<Double> t1Phase, ArrayList<Double> t2Time, ArrayList<Double> t2Phase, ArrayList<Double> tTime, ArrayList<Double> tPhase){//���ι��췽�����ڴ���tag1��tag2
		this.EPC1=EPC1;
		this.EPC2=EPC2;
		this.flag=fl;

		this.initialtime=ini;
		this.tWindow=tWindow;
		this.t1Phase=t1Phase;
		this.t1Time=t1Time;
		this.t2Phase=t2Phase;
		this.t2Time=t2Time;
//		this.tPhase=t1Phase;
//		this.tTime=t1Time;
	}
	
	@Override
	public void onTagReported(ImpinjReader reader, TagReport report) {

		List<Tag> tags = report.getTags();//是否就以这个List作为一次处理的标准
//		double twindow=0.5;//每0.5s处理一次数据
//		int Wlen=10;//这里或者可以用t=0.5来处理
//		double[][] arrMesTag1=new double[Wlen][2];
//		double[][] arrMesTag2=new double[Wlen][2];
//		int i=0,j=0;
//		int tempt
			
			for(Tag t:tags){//标签遍历
				
				if(flag){
					initialtime=Double.valueOf(t.getFirstSeenTime().ToString());//
					flag=false;
				}
				String tempTagEPC=t.getEpc().toString().replace(" ", "");//标签EPC
				double tempTime=(Double.valueOf(t.getFirstSeenTime().ToString())-initialtime)/Math.pow(10, 6);//标签规格化时间
				double tempPhase=t.getPhaseAngleInRadians();//标签相位值
//				tTime.add(tempTime);
//				tPhase.add(tempPhase);
				if(tempTime>=64) {//60秒后停止读取数据
					return;
				}
				if(tempTagEPC.equals(EPC1)&&t.getAntennaPortNumber()==1){//天线1接收到的标签1的数据
//					System.out.println(EPC1+" "+tempTime+" "+tempPhase);
					System.out.println("1 "+tempTime+" "+tempPhase);
					socketOutput.println("1 "+tempTime+" "+tempPhase);
//					t1Time.add(tempTime);
//					t1Phase.add(tempPhase);
//					global.t1k+=1;
//					Utils.saveRowdataofTag(tempTime+" "+tempPhase, "E:\\RFIDBreath\\breathDetection\\tag1.txt");//原始数据存储
				}else if(tempTagEPC.equals(EPC2)&&t.getAntennaPortNumber()==2) {//天线2接收到的标签2的数据
//					System.out.println(EPC2+" "+tempTime+" "+tempPhase+" "+t2Phase.size());
					System.out.println("2 "+tempTime+" "+tempPhase);
					socketOutput.println("2 "+tempTime+" "+tempPhase);
//					t2Time.add(tempTime);
//					t2Phase.add(tempPhase);
//					global.t2k+=1;
//					Utils.saveRowdataofTag(tempTime+" "+tempPhase, "E:\\RFIDBreath\\breathDetection\\tag2.txt");
				}
				//数据每达到twindow个进行一次处理和画图
//				if(tempTime-global.lastTime>=tWindow) {//||(global.t2k>=20&&global.t1k>=20)
//					global.lastTime=tempTime;
//					
//						DataDispose.dataDispose( t1Time,t1Phase,t2Time,t2Phase);
//					
//					//重置计数
//					global.t1k=0;
//					global.t2k=0;
//				}
				
				
				//将时间和相位数据插入数据库
//				System.out.println("time： "+(Double.valueOf(t.getFirstSeenTime().ToString())-initialtime)/Math.pow(10, 6)+" phase:"+
//						(2*Math.PI-t.getPhaseAngleInRadians()));
//	        	insert.add((Double.valueOf(t.getFirstSeenTime().ToString())-initialtime)/Math.pow(10, 6),
//						(2*Math.PI-t.getPhaseAngleInRadians()));
			}
			
		}

}
