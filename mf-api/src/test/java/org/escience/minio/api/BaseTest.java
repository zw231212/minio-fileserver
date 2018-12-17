package org.escience.minio.api;

import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;

public class BaseTest {

  @Test
  public void test(){
    Properties properties = System.getProperties();
    Enumeration<?> names = properties.propertyNames();
    while (names.hasMoreElements()){
      Object o = names.nextElement();
      System.out.println(o+">>>"+properties.get(o));
    }
  }

  @Test
  public void testMathCeil(){
    System.out.println(Math.ceil(5.2/2));
  }

  @Test
  public void testGetProtocol(){
    String url = "http://ltx.buaa.edu.cn/../djylz/zzsz.htm";
    int contains = url.indexOf("..");
    System.out.println(contains);
    if(contains != -1){
      System.out.println(url.replaceAll("\\.\\.",""));
    }
  }

  @Test
  public void testUUID(){
    String s = UUID.randomUUID().toString();
    System.out.println(s);
    String escience = UUID.nameUUIDFromBytes("escience".getBytes()).toString()
            .replaceAll("-","").toUpperCase();
    System.out.println(escience);
    String nstr = UUID.nameUUIDFromBytes(("escience" + "nstr").getBytes()).toString()
            .replaceAll("-","").toUpperCase();
    System.out.println(nstr);
  }

}
