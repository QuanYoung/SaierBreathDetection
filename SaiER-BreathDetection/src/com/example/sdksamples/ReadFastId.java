package com.example.sdksamples;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Settings;

import java.util.Scanner;


public class ReadFastId {

    public static void main(String[] args) {
        try {
            String hostname = "192.168.1.81";
//
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
            settings.getReport().setIncludeFastId(true);

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
