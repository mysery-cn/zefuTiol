package com.zefu.tiol.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtils {

	/**
	 * xml转换为对象
	 * @param xmlStr
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String xmlStr, Class<T> c) {
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			T t = (T) unmarshaller.unmarshal(new StringReader(xmlStr));
			return t;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <T> T xmlToBean(File xmlFile, Class<T> c)throws Exception {
		JAXBContext context = JAXBContext.newInstance(c);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		T t = (T) unmarshaller.unmarshal(xmlFile);
		return t;
	}
	
	public static synchronized void beanToXml(Object bean,String outPath) throws Exception{
		JAXBContext context = JAXBContext.newInstance(bean.getClass());
		Marshaller marshaller = context.createMarshaller(); 
		//编码格式 
		marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
		//是否格式化生成的xml串    
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//是否省略xml头信息（<?xml version="1.0" encoding="gb2312" standalone="yes"?>）
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
		OutputStream out=null;
		try{
			out=new FileOutputStream(new File(outPath));
			marshaller.marshal(bean, out);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}

	public static void main(String[] args) throws Exception{
		//File file=new File("D:/Data/szyd/meeting_type.xml");
		//ToilCatalogBean toilRootBean=XmlToBean.xmlToBean(file, ToilCatalogBean.class);
//		ToilMeetingTypeBean toilMeetingTypeBean=XmlUtils.xmlToBean(file, ToilMeetingTypeBean.class);
		System.out.println(1);
	}
}
