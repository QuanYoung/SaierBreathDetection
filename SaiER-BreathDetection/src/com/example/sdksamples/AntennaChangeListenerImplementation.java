package com.example.sdksamples;

import com.impinj.octane.AntennaChangeListener;
import com.impinj.octane.AntennaEvent;
import com.impinj.octane.ImpinjReader;

public class AntennaChangeListenerImplementation implements
        AntennaChangeListener {
	//AntennaChangeListener()接口  您的应用程序可以实现此接口来接收天线更改事件。 每当天线与系统连接或断开时，都会生成天线变化事件（配置时）。
    @Override
    public void onAntennaChanged(ImpinjReader reader, AntennaEvent e) {//reader - - 进行回调的ImpinjReader实例
    	                                                               //e - - 天线信息的变化
        System.out.println("Antenna Change--port: " + e.getPortNumber()
                + " state: " + e.getState().toString());
    }
}
