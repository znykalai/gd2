package alai.localhost.GD_wsdl;

public class GDPortTypeProxy implements alai.localhost.GD_wsdl.GDPortType {
  private String _endpoint = null;
  private alai.localhost.GD_wsdl.GDPortType gDPortType = null;
  
  public GDPortTypeProxy() {
    _initGDPortTypeProxy();
  }
  
  public GDPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initGDPortTypeProxy();
  }
  
  private void _initGDPortTypeProxy() {
    try {
      gDPortType = (new alai.localhost.GD_wsdl.GDLocator()).getGD();
      if (gDPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)gDPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)gDPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (gDPortType != null)
      ((javax.xml.rpc.Stub)gDPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public alai.localhost.GD_wsdl.GDPortType getGDPortType() {
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType;
  }
  
  public alai.GDT.Resint[] arrtest(alai.GDT.Instr[] into, int nPosition) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.arrtest(into, nPosition);
  }
  
  public alai.GDT.Resint[] downFile(java.lang.String pcFileName, int nPosition) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.downFile(pcFileName, nPosition);
  }
  
  public int getValueFromCTR(int type1, java.lang.String address, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.getValueFromCTR(type1, address, machineID);
  }
  
  public alai.GDT.Resint[] getValuesFromCTR(int type1, alai.GDT.Instr[] inaddress, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.getValuesFromCTR(type1, inaddress, machineID);
  }
  
  public alai.GDT.Resint[] getSirIntValuesFromCTR(String startAddress, int nums, int valuseLen, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.getSirIntValuesFromCTR(startAddress, nums, valuseLen, machineID);
  }
  
  public int writeValueToCTR(int type1, java.lang.String address, int value) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.writeValueToCTR(type1, address, value);
  }
  
  public int writeValuesToCTR(int type1, alai.GDT.Instr[] inaddress, alai.GDT.Inint[] inValues, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.writeValuesToCTR(type1, inaddress, inValues, machineID);
  }
  
  public int writeSirIntToCTR(String strAddress, int valuseLeng, alai.GDT.Inint[] invalues, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.writeSirIntToCTR(strAddress, valuseLeng, invalues, machineID);
  }
  
  public java.lang.String readFromRffid(java.lang.String message, int id) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.readFromRffid(message, id);
  }
  
  public int getPallet(int idEvent, java.lang.String fromLocID, int toLocID, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.getPallet(idEvent, fromLocID, toLocID, machineID);
  }
  
  public int upPallet(int idEvent, int fromID, int toLocID, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.upPallet(idEvent, fromID, toLocID, machineID);
  }
  
  public int swapPallet(int idEvent, int fromLocID, int toLocID, int machineID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.swapPallet(idEvent, fromLocID, toLocID, machineID);
  }
  
  public int toBackBuffer(int idEvent, int fromID, int toLocID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.toBackBuffer(idEvent, fromID, toLocID);
  }
  
  public int toBackHome(int idEvent, int fromID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.toBackHome(idEvent, fromID);
  }
  
  public alai.GDT.Resdouble[] getXYZ(int t) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.getXYZ(t);
  }
  
  public String getState(int t) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.getState(t);
  }
  
  public int sendCommentToRobot(java.lang.String comment, int machineID, int toRobotID) throws java.rmi.RemoteException{
    if (gDPortType == null)
      _initGDPortTypeProxy();
    return gDPortType.sendCommentToRobot(comment, machineID, toRobotID);
  }
  
  
}