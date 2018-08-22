package datasyncTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ssitcloud.business.common.util.ResourcesUtil;
import com.ssitcloud.business.datasync.service.DataSyncService;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;

public class DataSyncTest extends BasicTest {

	@Resource
	private DataSyncService dataSyncService;

	@Test
	public void test() throws Exception {
		// selDeviceTest();
		test01();
		//alterXML();
	}

	public void selDeviceTest() {

		DeviceEntity d = dataSyncService.selDevice("www");
		System.out.println(d.getDevice_code());
		System.out.println(d.getDevice_desc());
		System.out.println(d.getDevice_id());
		System.out.println(d.getDevice_idx());
		System.out.println(d.getDevice_name());
		System.out.println(d.getLibrary_idx());
	}

	public void testXMLParse() throws JAXBException, IOException {
		JAXBContext context = JAXBContext
				.newInstance(RequestURLListEntity.class);
		Unmarshaller um = context.createUnmarshaller();
		RequestURLListEntity requestURLList = (RequestURLListEntity) um
				.unmarshal(ResourcesUtil.getInputStream("RequestURL.xml"));
		Assert.assertNotNull("requestURLList is null", requestURLList);
		// RequestURLEntity url=
		// requestURLList.getRequestURLList().get("querymethod");
		// System.out.println(url.getHost());
	}

	public void test01() throws Exception {
		InputStream is = ResourcesUtil.getInputStream("RequestURL.xml");
		XPath xpath = XPathFactory.newInstance().newXPath();// 创建Xpath对象
															// 通过Xpath工厂
		DocumentBuilder db = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();// 获取文档对象构建工厂
		Document doc = db.parse(is); // 创建文档对象
		// 获取节点集合 第一个参数 表达式 第二个文档 第三个Xpath

		NodeList nodeList = (NodeList) xpath.evaluate(
				"//RequestUrl[@name='querymethod']", doc,
				XPathConstants.NODESET);
		// book节点中的属性category值为WEB
		//for (int i = 0; i < nodeList.getLength(); i++) {// 遍历
			Element element = (Element) nodeList.item(0); // 将节点对象转换为Element对象
			System.out.println(element.getAttribute("host"));
			System.out.println(element.getAttribute("port"));
			System.out.println(element.getAttribute("requestMapping"));
		//}
	}

	public void alterXML() throws Exception {
		// 创建输入流
		InputStream is = ResourcesUtil.getInputStream("RequestURL.xml");
		DocumentBuilder db = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();// 创建DocBuilder对象
		Document doc = db.parse(is);// 获取document对象
		XPath xpath = XPathFactory.newInstance().newXPath();// 创建Xpath对象
		// 创建transFormer对象
		Transformer tran = TransformerFactory.newInstance().newTransformer();
		tran.setOutputProperty(OutputKeys.ENCODING, "utf-8");// 设置编码
		tran.setOutputProperty(OutputKeys.INDENT, "yes");// 支付插入
		tran.setOutputProperty(OutputKeys.VERSION, "1.0");// 版本
		NodeList nodes = (NodeList) xpath.evaluate(
				"//RequestUrl[@id='querymethod']", doc,
				XPathConstants.NODESET); // 获取指定的节点集合
		Element e = (Element) nodes.item(0);// 获取第一个
		e.setAttribute("host", "555555");
		Result result = new StreamResult(new File(System.getProperty("user.dir")+ "/src/main/resources/RequestURL.xml"));
		tran.transform(new DOMSource(doc), result);
	}
}
