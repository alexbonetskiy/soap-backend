//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.09.26 at 01:36:52 AM MSK 
//


package com.backend.soap.web.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="userTO" type="{http://backend.com/soap/web/users}userTO"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userTO"
})
@XmlRootElement(name = "addUserRequest")
public class AddUserRequest {

    @XmlElement(required = true)
    protected UserTO userTO;

    /**
     * Gets the value of the userTO property.
     * 
     * @return
     *     possible object is
     *     {@link UserTO }
     *     
     */
    public UserTO getUserTO() {
        return userTO;
    }

    /**
     * Sets the value of the userTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserTO }
     *     
     */
    public void setUserTO(UserTO value) {
        this.userTO = value;
    }

}
