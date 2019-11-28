package yq;

public class UserInfo {
	private String ReaderAddress;
	private String tag1EPC;
	private String tag2EPC;
	
	public UserInfo() {}
	
	public UserInfo(String readerAddress, String tag1epc, String tag2epc) {
		super();
		ReaderAddress = readerAddress;
		tag1EPC = tag1epc;
		tag2EPC = tag2epc;
	}

	public String getReaderAddress() {
		return ReaderAddress;
	}
	
	public String getTag1EPC() {
		return tag1EPC;
	}

	public String getTag2EPC() {
		return tag2EPC;
	}

}
