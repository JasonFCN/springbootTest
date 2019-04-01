package com.cwj.springbootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

public class ATest {
	public static void main(String[] args) {
		FileInputStream psStream = null;
		try {
			psStream = new FileInputStream("C:\\Users\\Chenwujie\\Desktop\\js\\a.html");
		} catch (FileNotFoundException ffne) {
			ffne.printStackTrace();
		}
		if (psStream == null) {
			return;
		}
		// 创建打印数据
		// DocAttributeSet docAttr = new HashDocAttributeSet();//设置文档属性
		// Doc myDoc = new SimpleDoc(psStream, psInFormat, docAttr);
		Doc myDoc = new SimpleDoc(psStream, DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_8, null);

		// 设置打印属性
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(new Copies(1));// 打印份数，1份

		// 查找所有打印服务
		PrintService[] services = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE, aset);

		// this step is necessary because I have several printers configured
		// 将所有查找出来的打印机与自己想要的打印机进行匹配，找出自己想要的打印机
		PrintService myPrinter = null;
		for (int i = 0; i < services.length; i++) {
			System.out.println("service found: " + services[i]);
			String svcName = services[i].toString();
			if (svcName.contains("EPSONL550 Series")) {
				myPrinter = services[i];
				System.out.println("my printer found: " + svcName);
				System.out.println("my printer found: " + myPrinter);
				break;
			}
		}

		if (myPrinter != null) {
			// 可以输出打印机的各项属性
			/*AttributeSet att = myPrinter.getAttributes();

			for (Attribute a : att.toArray()) {

				String attributeName;
				String attributeValue;

				attributeName = a.getName();
				attributeValue = att.get(a.getClass()).toString();

				System.out.println(attributeName + " : " + attributeValue);
			}*/
			
			System.out.println("开始打印");
			/*DocPrintJob job = myPrinter.createPrintJob();// 创建文档打印作业
			try {
				job.print(myDoc, aset);// 打印文档

			} catch (Exception pe) {
				pe.printStackTrace();
			}*/
		} else {
			System.out.println("no printer services found");
		}
	}

}
