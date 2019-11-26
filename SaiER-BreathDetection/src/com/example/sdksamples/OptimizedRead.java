package com.example.sdksamples;

import com.impinj.octane.*;

import java.io.File;
import java.util.Scanner;


public class OptimizedRead {

    public static void main(String[] args) {

        try {
            String hostname = "192.168.1.81";

//            if (hostname == null) {
//                throw new Exception("Must specify the '"
//                        + SampleProperties.hostname + "' property");
//            }
//

            ImpinjReader reader = new ImpinjReader();

            // Connect
            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();

            settings.getReport().setIncludeAntennaPortNumber(true);
            settings.getReport().setIncludePhaseAngle(true);
            settings.getReport().setIncludeFirstSeenTime(true);
        
            // read two words from the start of user memory on all tags 
            TagReadOp readUser = new TagReadOp();
            readUser.setMemoryBank(MemoryBank.Epc);
            readUser.setWordCount((short) 32);
            readUser.setWordPointer((short) 0);
            readUser.Id = 222;

            // reader the non-serialzed part of the TID (first 2 words)
            TagReadOp readTid = new TagReadOp();
            readTid.setMemoryBank(MemoryBank.Tid);
            readTid.setWordPointer((short) 0);
            readTid.setWordCount((short) 2);
            readTid.Id = 333;

            // add to the optimized read operations
            settings.getReport().getOptimizedReadOps().add(readUser);
            settings.getReport().getOptimizedReadOps().add(readTid);
//            settings.setTagPopulationEstimate(2);
//System.out.println(settings.getReaderMode());//AutoSetDenseReader
            // set up listeners to hear stuff back from SDK
            reader.setTagReportListener(
                    new TagReportListenerImplementation());
            
//          reader.setTagReportListener(new getTagMessage2((new File("E:\\RFIDBreath\\optread2.txt")),"E2004074870202400870C322"));//E2000016640102060880C253

//            reader.setTagOpCompleteListener(
//                    new TagOpCompleteListenerImplementation());

            // Apply the new settings
            reader.applySettings(settings);

            // Start the reader
            reader.start();
            double starttime=System.currentTimeMillis();
            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            System.out.println("Stopping  " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();
            System.out.println("time: "+(System.currentTimeMillis()-starttime)+"count: "+global.count);
            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
