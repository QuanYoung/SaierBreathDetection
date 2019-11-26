package com.example.sdksamples;

import com.impinj.octane.*;

import java.io.File;
import java.util.Scanner;


public class ReadTags {

    public static void main(String[] args) {

        try {
            String hostname ="192.168.1.81";

//            if (hostname == null) {
//                throw new Exception("Must specify the '"
//                        + SampleProperties.hostname + "' property");
//            }

            ImpinjReader reader = new ImpinjReader();

            System.out.println("Connecting");
            reader.connect(hostname);

            Settings settings = reader.queryDefaultSettings();

            ReportConfig report = settings.getReport();
            report.setIncludeAntennaPortNumber(true);
            report.setIncludeFirstSeenTime(true);
            report.setIncludePeakRssi(true);
            report.setIncludePhaseAngle(true);
            report.setMode(ReportMode.Individual);

            // The reader can be set into various modes in which reader
            // dynamics are optimized for specific regions and environments.
            // The following mode, AutoSetDenseReader, monitors RF noise and interference and then automatically
            // and continuously optimizes the reader’s configuration
            settings.setReaderMode(ReaderMode.AutoSetDenseReader);
//            settings.setSearchMode(SearchMode.SingleTarget);
//            settings.setSession(4);
//            settings.setTagPopulationEstimate(1);//���ñ�ǩ����


            // set some special settings for antenna 1
            AntennaConfigGroup antennas = settings.getAntennas();
            antennas.disableAll();
            antennas.enableById(new short[]{1});
            antennas.getAntenna((short) 1).setIsMaxRxSensitivity(true);
            antennas.getAntenna((short) 1).setIsMaxTxPower(false);
           
            antennas.getAntenna((short) 1).setTxPowerinDbm(12.0);
            //antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70);
             reader.setTagReportListener(new TagReportListenerImplementation());
        //       reader.setTagReportListener(new getTagMessage2((new File("E:\\RFIDBreath\\optread1.txt")),"E20040748702023414807FB7"));

            System.out.println("Applying Settings");
            
            reader.applySettings(settings);

            System.out.println("Starting");
            reader.start();
            double starttime=System.currentTimeMillis();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();
            
            reader.stop();
            reader.disconnect();
            System.out.println("time: "+(System.currentTimeMillis()-starttime));

        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
