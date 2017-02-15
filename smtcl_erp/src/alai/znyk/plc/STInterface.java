package alai.znyk.plc;

public interface STInterface {
	
	    public int getLength();
	    public String getStartAddress();
	    public String writeToPLC();
	    public String updataFromPLC();
	    public int getBoolContent();
}
