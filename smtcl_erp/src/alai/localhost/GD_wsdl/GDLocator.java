/**
 * GDLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package alai.localhost.GD_wsdl;

public class GDLocator extends org.apache.axis.client.Service implements alai.localhost.GD_wsdl.GD {

/**
 * gSOAP 2.8.39 generated service definition
 */

    public GDLocator() {
    }


    public GDLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GDLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GD
    private java.lang.String GD_address = "http://192.168.1.222:9005/GD?cgi";

    public java.lang.String getGDAddress() {
        return GD_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GDWSDDServiceName = "GD";

    public java.lang.String getGDWSDDServiceName() {
        return GDWSDDServiceName;
    }

    public void setGDWSDDServiceName(java.lang.String name) {
        GDWSDDServiceName = name;
    }

    public alai.localhost.GD_wsdl.GDPortType getGD() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GD_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGD(endpoint);
    }

    public alai.localhost.GD_wsdl.GDPortType getGD(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            alai.localhost.GD_wsdl.GDStub _stub = new alai.localhost.GD_wsdl.GDStub(portAddress, this);
            _stub.setPortName(getGDWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGDEndpointAddress(java.lang.String address) {
        GD_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (alai.localhost.GD_wsdl.GDPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                alai.localhost.GD_wsdl.GDStub _stub = new alai.localhost.GD_wsdl.GDStub(new java.net.URL(GD_address), this);
                _stub.setPortName(getGDWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GD".equals(inputPortName)) {
            return getGD();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.1.222:9005/GD?wsdl", "GD");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.1.222:9005/GD?wsdl", "GD"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GD".equals(portName)) {
            setGDEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
