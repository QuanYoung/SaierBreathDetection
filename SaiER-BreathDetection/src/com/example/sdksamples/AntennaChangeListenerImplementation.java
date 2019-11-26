package com.example.sdksamples;

import com.impinj.octane.AntennaChangeListener;
import com.impinj.octane.AntennaEvent;
import com.impinj.octane.ImpinjReader;

public class AntennaChangeListenerImplementation implements
        AntennaChangeListener {
	//AntennaChangeListener()�ӿ�  ����Ӧ�ó������ʵ�ִ˽ӿ����������߸����¼��� ÿ��������ϵͳ���ӻ�Ͽ�ʱ�������������߱仯�¼�������ʱ����
    @Override
    public void onAntennaChanged(ImpinjReader reader, AntennaEvent e) {//reader - - ���лص���ImpinjReaderʵ��
    	                                                               //e - - ������Ϣ�ı仯
        System.out.println("Antenna Change--port: " + e.getPortNumber()
                + " state: " + e.getState().toString());
    }
}
