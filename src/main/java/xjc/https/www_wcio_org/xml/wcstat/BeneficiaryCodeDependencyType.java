//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.08.23 at 07:45:34 PM CDT 
//


package https.www_wcio_org.xml.wcstat;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BeneficiaryCodeDependency_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BeneficiaryCodeDependency_Type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="T"/&gt;
 *     &lt;enumeration value="P"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BeneficiaryCodeDependency_Type")
@XmlEnum
public enum BeneficiaryCodeDependencyType {

    T,
    P;

    public String value() {
        return name();
    }

    public static BeneficiaryCodeDependencyType fromValue(String v) {
        return valueOf(v);
    }

}
