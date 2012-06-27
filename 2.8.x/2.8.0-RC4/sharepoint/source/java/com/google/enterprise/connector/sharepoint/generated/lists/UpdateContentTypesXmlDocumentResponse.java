/**
 * UpdateContentTypesXmlDocumentResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.google.enterprise.connector.sharepoint.generated.lists;

public class UpdateContentTypesXmlDocumentResponse  implements java.io.Serializable {
    private com.google.enterprise.connector.sharepoint.generated.lists.UpdateContentTypesXmlDocumentResponseUpdateContentTypesXmlDocumentResult updateContentTypesXmlDocumentResult;

    public UpdateContentTypesXmlDocumentResponse() {
    }

    public UpdateContentTypesXmlDocumentResponse(
           com.google.enterprise.connector.sharepoint.generated.lists.UpdateContentTypesXmlDocumentResponseUpdateContentTypesXmlDocumentResult updateContentTypesXmlDocumentResult) {
           this.updateContentTypesXmlDocumentResult = updateContentTypesXmlDocumentResult;
    }


    /**
     * Gets the updateContentTypesXmlDocumentResult value for this UpdateContentTypesXmlDocumentResponse.
     *
     * @return updateContentTypesXmlDocumentResult
     */
    public com.google.enterprise.connector.sharepoint.generated.lists.UpdateContentTypesXmlDocumentResponseUpdateContentTypesXmlDocumentResult getUpdateContentTypesXmlDocumentResult() {
        return updateContentTypesXmlDocumentResult;
    }


    /**
     * Sets the updateContentTypesXmlDocumentResult value for this UpdateContentTypesXmlDocumentResponse.
     *
     * @param updateContentTypesXmlDocumentResult
     */
    public void setUpdateContentTypesXmlDocumentResult(com.google.enterprise.connector.sharepoint.generated.lists.UpdateContentTypesXmlDocumentResponseUpdateContentTypesXmlDocumentResult updateContentTypesXmlDocumentResult) {
        this.updateContentTypesXmlDocumentResult = updateContentTypesXmlDocumentResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateContentTypesXmlDocumentResponse)) return false;
        UpdateContentTypesXmlDocumentResponse other = (UpdateContentTypesXmlDocumentResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.updateContentTypesXmlDocumentResult==null && other.getUpdateContentTypesXmlDocumentResult()==null) ||
             (this.updateContentTypesXmlDocumentResult!=null &&
              this.updateContentTypesXmlDocumentResult.equals(other.getUpdateContentTypesXmlDocumentResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUpdateContentTypesXmlDocumentResult() != null) {
            _hashCode += getUpdateContentTypesXmlDocumentResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateContentTypesXmlDocumentResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">UpdateContentTypesXmlDocumentResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateContentTypesXmlDocumentResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", "UpdateContentTypesXmlDocumentResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/sharepoint/soap/", ">>UpdateContentTypesXmlDocumentResponse>UpdateContentTypesXmlDocumentResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType,
           java.lang.Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType,
           java.lang.Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}