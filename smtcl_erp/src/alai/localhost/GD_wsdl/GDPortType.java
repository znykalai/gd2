/**
 * GDPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package alai.localhost.GD_wsdl;

public interface GDPortType extends java.rmi.Remote {

    /**
     * Service definition of function ns__Arrtest
     */
    public alai.GDT.Resint[] arrtest(alai.GDT.Instr[] into, int nPosition) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__DownFile
     */
    public alai.GDT.Resint[] downFile(java.lang.String pcFileName, int nPosition) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getValueFromCTR
     */
    public int getValueFromCTR(int type1, java.lang.String address, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getValuesFromCTR
     */
    public alai.GDT.Resint[] getValuesFromCTR(int type1, alai.GDT.Instr[] inaddress, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getSirIntValuesFromCTR
     */
    public alai.GDT.Resint[] getSirIntValuesFromCTR(String startAddress, int nums, int valuseLen, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__WriteValueToCTR
     */
    public int writeValueToCTR(int type1, java.lang.String address, int value) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__WriteValuesToCTR
     */
    public int writeValuesToCTR(int type1, alai.GDT.Instr[] inaddress, alai.GDT.Inint[] inValues, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__WriteSirIntToCTR
     */
    public int writeSirIntToCTR(String strAddress, int valuseLeng, alai.GDT.Inint[] invalues, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__ReadFromRffid
     */
    public java.lang.String readFromRffid(java.lang.String message, int id) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getPallet
     */
    public int getPallet(int idEvent, java.lang.String fromLocID, int toLocID, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__upPallet
     */
    public int upPallet(int idEvent, int fromID, int toLocID, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__swapPallet
     */
    public int swapPallet(int idEvent, int fromLocID, int toLocID, int machineID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__toBackBuffer
     */
    public int toBackBuffer(int idEvent, int fromID, int toLocID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__toBackHome
     */
    public int toBackHome(int idEvent, int fromID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getXYZ
     */
    public alai.GDT.Resdouble[] getXYZ(int t) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getState
     */
    public String getState(int t) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__sendCommentToRobot
     */
    public int sendCommentToRobot(java.lang.String comment, int machineID, int toRobotID) throws java.rmi.RemoteException;
}
