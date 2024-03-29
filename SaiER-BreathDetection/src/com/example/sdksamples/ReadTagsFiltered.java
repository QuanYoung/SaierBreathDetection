package com.example.sdksamples;

import java.util.Scanner;

import com.impinj.octane.FilterSettings;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.MemoryBank;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.ReaderMode;
import com.impinj.octane.ReportMode;
import com.impinj.octane.Settings;
import com.impinj.octane.TagFilter;
import com.impinj.octane.TagFilterMode;
import com.impinj.octane.TagFilterOp;


public class ReadTagsFiltered {

    public static void main(String[] args) {
        try {
            String hostname = "192.168.1.81";

//            if (hostname == null) {
//                throw new Exception("Must specify the '"
//                        + SampleProperties.hostname + "' property");
//            }

            ImpinjReader reader = new ImpinjReader();

            // Connect
            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();

            settings.getReport().setIncludeAntennaPortNumber(true);
            settings.getReport().setMode(ReportMode.BatchAfterStop);

            settings.setReaderMode(ReaderMode.AutoSetDenseReader);

          //  String targetEpc = System.getProperty(SampleProperties.targetTag);
          //  String targetEpc ="E2004074870202290950BACB";
            //����Filter
            FilterSettings fs=new FilterSettings();
            TagFilter Filter1=settings.getFilters().getTagFilter1();
            Filter1.setBitCount(2);
            Filter1.setBitPointer(94);
            Filter1.setMemoryBank(MemoryBank.User);
            Filter1.setFilterOp(TagFilterOp.Match);
          //  Filter1.setFilterOp(TagFilterOp.valueOf("A"));
//            String b="02";
//            Integer it=new Integer(b);
//            System.out.println(it.toHexString());
            Filter1.setTagMask("80");
            
            fs.setTagFilter1(Filter1);
            settings.getFilters().setMode(TagFilterMode.OnlyFilter1);
            // this will match the first 16 bits of the target EPC. 
//            if (targetEpc != null) {
//                TagFilter t1 = settings.getFilters().getTagFilter1();
//                t1.setBitCount(16);
//                t1.setBitPointer(BitPointers.Epc);
//                t1.setMemoryBank(MemoryBank.Epc);
//                t1.setFilterOp(TagFilterOp.Match);
//                t1.setTagMask(targetEpc);
//                settings.getFilters().setMode(TagFilterMode.OnlyFilter1);
//                System.out.println("Matching 1st 16 bits of epc "
//                        + targetEpc);
//           }
//
//            String targetUser = System.getProperty(SampleProperties.targetUser);
//            
//
//            // this will match the first 8 bits of user memory 
//            if (targetUser != null) {
//                TagFilter t2 = settings.getFilters().getTagFilter1();
//                t2.setBitCount(8);
//                t2.setBitPointer(0);
//                t2.setMemoryBank(MemoryBank.User);
//                t2.setFilterOp(TagFilterOp.Match);
//                t2.setTagMask(targetUser);
//                settings.getFilters().setMode(TagFilterMode.OnlyFilter2);
//                System.out.println("Matching 1st 16 bits of user "
//                        + targetUser);
//            }
//
//            // enable both filters if they are set 
//            if ((targetEpc != null) && (targetUser != null)) {
//                settings.getFilters().setMode(TagFilterMode.Filter1AndFilter2);
//            }

            // Apply the new settings
            reader.applySettings(settings);

            reader.setTagReportListener(new TagReportListenerImplementation());

            // Start the reader
            reader.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            System.out.println("Stopping  " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();

            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
