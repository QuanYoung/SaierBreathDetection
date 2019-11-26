package com.example.sdksamples;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

public class getTagMessage1 implements TagReportListener{
	String tag1;
	String tag2;
	File file;
	public getTagMessage1(){
	}
	public getTagMessage1(String tag1){//带参构造方法用于传参tag1
		this.tag1=tag1;
	}
	public getTagMessage1(File file){//带参构造方法用于传参
		this.file=file;
	}
public getTagMessage1(File file, String tag1) {
		this.file=file;
		this.tag1=tag1;
	}
	//	public getTagMessage1(String tag1,String tag2){//带参构造方法用于传参tag1与tag2
//		this.tag1=tag1;
//		this.tag2=tag2;
//	}
	@Override
	public void onTagReported(ImpinjReader reader, TagReport report) {
//		System.out.println(tag1+"--------------"+tag2);
		FileWriter fw1 = null;
		List<Tag> tags = report.getTags();
					try{
				
//					fw1 = new FileWriter(file1,true);
//				int count=0;
					fw1 = new FileWriter(file,true);//是否附加写入
					for(Tag t:tags){//1号天线只读1号标签
//						if(t.getEpc().toString().replace(" ", "").equals(tag1)&&t.getAntennaPortNumber()==1&&count<5)
//							count++;
//									
						if(t.getEpc().toString().replace(" ", "").equals(tag1)&&t.getAntennaPortNumber()==1){
								fw1.write(t.getEpc().toString().replace(" ", "")+" "+t.getFirstSeenTime().ToString()+" "+t.getPhaseAngleInRadians());
								fw1.write("\r\n");
								System.out.println(t.getEpc()+" "+t.getFirstSeenTime().ToString()+" "+t.getPhaseAngleInRadians()+" antenna:"+t.getAntennaPortNumber());
								System.out.println("count: "+global.count);
						global.count+=1;
						}
								
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					fw1.close();
				} catch (IOException e) {
					System.out.println("文件关闭失败！");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
