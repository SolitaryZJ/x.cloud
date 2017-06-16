package webserviceTest.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.apache.jasper.tagplugins.jstl.core.Url;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yaml.snakeyaml.reader.StreamReader;

import webserviceTest.entity.User;

public class TestSoap {

	public static String ns = "http://imp.service.webserviceTest/";
	public static String wsdlUrl = "http://localhost:8888/ns?wsdl";
	
	@Test
	public void test01() throws SOAPException, IOException{
		//1、创建消息工厂
		MessageFactory newInstance = MessageFactory.newInstance();
		//2、根据消息工厂创建SoapMessage
		SOAPMessage createMessage = newInstance.createMessage();
		//3、创建soapPart
		SOAPPart soapPart = createMessage.getSOAPPart();
		//4、获得soapEnvelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		//5、可以通过soapEnvelope有效的获取响应的body和header等信息
		SOAPBody body = envelope.getBody();
		//6、根据QName创建相应的节点，QName就是一个带有命名空间的<zj:add xmlns="http://www.jie.com/webservice">
		QName qName = new QName("http://www.jie.com/webservice","add","zj");
		//如果使用以下方式进行设置，出现<>转化未&lt;和&gt
		//body.addBodyElement(qName).setValue("<a>2</a><b>34</b>");
		//body.addBodyElement(qName).setValue("123123");
		SOAPBodyElement element = body.addBodyElement(qName);
		element.addChildElement("a").setValue("12");
		element.addChildElement("b").setValue("456");
		//打印内容
		createMessage.writeTo(System.out);
	}
	
	@Test
	public void test02() throws Exception{
		try {
			//1、创建服务
			URL url = new URL(wsdlUrl);
			QName qName = new QName(ns,"IMathServiceImpService");
			Service service = Service.create(url,qName);
			
			//2.创建dispatch
			Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(ns,"IMathServiceImpPort"), SOAPMessage.class,Service.Mode.MESSAGE);
			
			//3、创建soapmessage
			SOAPMessage msg = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
			SOAPBody body = envelope.getBody();
			
			//4、创建QName来指定传递的数据
			QName qName2 = new QName("http://service.webserviceTest/","add","zj"); //<zj:add xmlns="xxx">
			SOAPBodyElement bodyElement = body.addBodyElement(qName2);
			bodyElement.addChildElement("a").setValue("2333");
			bodyElement.addChildElement("b").setValue("321");
			
			msg.writeTo(System.out);
			System.out.println("\n使用dispatch传递消息开始、、、、");
			//5、通过dispatch传递消息
			SOAPMessage invoke = dispatch.invoke(msg);
			invoke.writeTo(System.out);
			
			invoke.getSOAPPart().getDocumentElement().getElementsByTagName("addResult");
			Document document = invoke.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
			String textContent = document.getElementsByTagName("addResult").item(0).getTextContent();
			System.out.println(textContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03(){
		try {
			//1、创建服务
			URL url = new URL(wsdlUrl);
			QName qName = new QName(ns,"IMathServiceImpService");
			Service service = Service.create(url,qName);
			
			//2.创建dispatch(通过源数据的方式传递)
			Dispatch<Source> dispatch = service.createDispatch(new QName(ns,"IMathServiceImpPort"), Source.class,Service.Mode.PAYLOAD);
			
			//3、根据用户对象创建响应的xml
			User user = new User("zhangs", "nv", 90);
			
			JAXBContext ctx = JAXBContext.newInstance(User.class);
			Marshaller mar = ctx.createMarshaller();
			StringWriter writer = new StringWriter();
			mar.setProperty(Marshaller.JAXB_FRAGMENT, true);
			mar.marshal(user, writer);
			System.out.println(writer);
			
			//4、封装相应的part
			String payload = "<nn:addUser xmlns:nn=\""+ns+"\">"+writer.toString()+"</nn:addUser>";
			System.out.println(payload);
			StreamSource source = new StreamSource(new StringReader(payload));
			
			//5、通过dispatch传递payload
			Source response = dispatch.invoke(source);
			
			//6、处理响应信息
			System.out.println(response instanceof DOMSource);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test04() throws Exception{
		try {
			//1、创建服务
			URL url = new URL(wsdlUrl);
			QName qName = new QName(ns,"IMathServiceImpService");
			Service service = Service.create(url,qName);
			
			//2.创建dispatch
			Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(ns,"IMathServiceImpPort"), SOAPMessage.class,Service.Mode.MESSAGE);
			
			//3、创建soapmessage
			SOAPMessage msg = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
			SOAPBody body = envelope.getBody();
			
			//4、创建QName来指定传递的数据
			QName qName2 = new QName("http://service.webserviceTest/","list","zj"); //<zj:add xmlns="xxx">
			SOAPBodyElement bodyElement = body.addBodyElement(qName2);
			msg.writeTo(System.out);
			System.out.println("\n使用dispatch传递消息开始、、、、");
			
			//5、通过dispatch传递消息
			SOAPMessage invoke = dispatch.invoke(msg);
			invoke.writeTo(System.out);
			System.out.println();
			
			//6、将响应结果转为dom对象
			Document doc = invoke.getSOAPBody().extractContentAsDocument();
			NodeList nodeList = doc.getElementsByTagName("user");
			JAXBContext ctx = JAXBContext.newInstance(User.class);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				User u = (User)ctx.createUnmarshaller().unmarshal(node);
				System.out.println(u.getUsername());
			}
			
			
			String textContent = doc.getElementsByTagName("addResult").item(0).getTextContent();
			System.out.println(textContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
