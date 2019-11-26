package yq;
import java.io.PrintStream;
import java.util.Scanner;

import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.FeatureSet;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.ReaderMode;
import com.impinj.octane.ReportConfig;
import com.impinj.octane.ReportMode;
import com.impinj.octane.Settings;

public class ReadTagsMessage {
//	public static void main(String[] args) {
		
//	}
	  public static void readTags(PrintStream socketOutput) {

//		 ArrayList<Double> t1Time=null;
//         ArrayList<Double> t2Time=null;
//         ArrayList<Double> t1Phase=null;
//         ArrayList<Double> t2Phase=null;
//         ArrayList<Double> tTime=null;
//         ArrayList<Double> tPhase=null;
		

	        try {
	            String hostname = "192.168.1.81";


	            ImpinjReader reader = new ImpinjReader();

	            System.out.println("Connecting");
	            reader.connect(hostname);
	            FeatureSet features = reader.queryFeatureSet();
	            Settings settings = reader.queryDefaultSettings();

	            ReportConfig report = settings.getReport();
	           
	            report.setIncludeAntennaPortNumber(true);
	            report.setIncludeChannel(true);
	            report.setIncludePeakRssi(true);
	            report.setIncludePhaseAngle(true);
	            report.setIncludeFirstSeenTime(true);
	            report.setMode(ReportMode.Individual);

	            settings.setReaderMode(ReaderMode.MaxThroughput);
	            

	            AntennaConfigGroup antennas = settings.getAntennas();
	            antennas.disableAll();
	            antennas.enableById(new short[]{1,2});
//	            antennas.getAntenna((short) 1).setIsMaxTxPower(false);
//	            antennas.getAntenna((short) 1).setTxPowerinDbm(28);
	            //��ĳЩ����
	            antennas.getAntenna((short) 1).setIsMaxRxSensitivity(true);
	            antennas.getAntenna((short) 1).setIsMaxTxPower(true);
	            //antennas.getAntenna((short) 1).setTxPowerinDbm(30.0);
	            //antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70);
//	            
				antennas.getAntenna((short) 2).setIsMaxRxSensitivity(true);
				antennas.getAntenna((short) 2).setIsMaxTxPower(true);
	
//				
	           // reader.setTagReportListener(new TagReportListenerImplementation());
				//E20050110418017524901855  E2006000620D013328300243  
//					reader.setTagReportListener(new getTagMessage("E20040748702023814108685","E20040748702023814108685"));//E20040748702024021803629 E2004074870202300380EEA9 E2004074870202330960B8D5 E20040748702023222602E3B
	            boolean flag=true;//��������Ƿ��¼�µ�һ�ζ�����ǩ��ʱ��
	            double initialtime=0;//������ԭʼ�ĳ�ʼʱ�������Ժ��epc����׼
//	            double tWindow=5;
//	          
//	           t1Time=new ArrayList<Double>();
//	            t2Time=new ArrayList<Double>();
//	            t1Phase=new ArrayList<Double>();
//	             t2Phase=new ArrayList<Double>();
//	             tTime=new ArrayList<Double>();
//	           tPhase=new ArrayList<Double>();
				
//	            reader.setTagReportListener(new getTagMessage("1012","E200407487020230140088B5",flag,initialtime,tWindow,t1Time,t1Phase,t2Time,t2Phase,tTime,tPhase));//E20040748702024021803629 E2004074870202300380EEA9 E2004074870202330960B8D5 E20040748702023222602E3B
//	            reader.setTagReportListener(new getTagMessage("E20050110418017524901855","E20050110418024325001737",socketOutput));//中南标签E20040748702024021803629 E2004074870202300380EEA9 E2004074870202330960B8D5 E20040748702023222602E3B
	            reader.setTagReportListener(new getTagMessage("E20040748702023117606060","E2004074870202400980B8D9",initialtime,flag,socketOutput));//湖大标签E20040748702024021803629 E2004074870202300380EEA9 E2004074870202330960B8D5 E20040748702023222602E3B

	           //				reader.setTagReportListener(new getTagMessage("E2005011041802062490188D","E2005011041802062490188D"));E20050110418017524901855
					System.out.println("Applying Settings");
	            reader.applySettings(settings);
	            
	         //   System.out.println("wait for 5 seconds");
				//Thread.sleep(5000);

	            System.out.println("Starting");
	            reader.start();
	         //   Thread.sleep(8000);
	            
	            
	            System.out.println("Press Enter to exit.");
	            Scanner s = new Scanner(System.in);
	            s.nextLine();
	          
	            reader.stop();
	            reader.disconnect();
	            
	      
		       
	        } catch (OctaneSdkException ex) {
	            System.out.println(ex.getMessage());
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	            ex.printStackTrace(System.out);
	        }finally {

			}
	    }

}

	