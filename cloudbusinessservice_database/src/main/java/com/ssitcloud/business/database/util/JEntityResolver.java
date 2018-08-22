package com.ssitcloud.business.database.util;

import org.xml.sax.*;

public class JEntityResolver implements EntityResolver {
  
  public InputSource resolveEntity(String publicId,String systemId) {
    if(publicId!=null && publicId.equals("-//Jmyadmin//DTD Jmyadmin Configuration 1.0//EN")) {
      InputSource is = new InputSource(this.getClass().getClassLoader().getResourceAsStream("jmyadmin-config_1_0.dtd"));
      System.out.println("is: " + is);
      return is;
    }
    return null;
  }

}
