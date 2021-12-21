package jp.example.businesscard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

public class BusinessCardGenerator {

	public static byte[] Generate(String templatePath, Map<String, String> parameter, String contentsBaseUrl) {
		
		StringWriter writer = new StringWriter();
		MustacheFactory mf = new DefaultMustacheFactory();
		try {
			mf.compile(templatePath).execute(writer, parameter).flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		var htmlDocument = writer.toString();
		return ConvertHtmlToPdf(htmlDocument, contentsBaseUrl);
	}

	public static byte[] Generate(StringReader templateReader,String templateName, Map<String, String> parameter, String contentsBaseUrl) {
		
		StringWriter writer = new StringWriter();
		MustacheFactory mf = new DefaultMustacheFactory();
		try {
			mf.compile(templateReader, templateName).execute(writer, parameter).flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		var htmlDocument = writer.toString();
		return ConvertHtmlToPdf(htmlDocument, contentsBaseUrl);
	}

	public static byte[] ConvertHtmlToPdf(String htmlDocument, String contentsBaseUrl) {
		ITextRenderer renderer = new ITextRenderer();
		try {
			renderer.getFontResolver().addFont("fonts/ipaexg.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    	renderer.setDocumentFromString(htmlDocument, contentsBaseUrl);
    	renderer.layout();
    	ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	    try {
			renderer.createPDF(byteStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	    return byteStream.toByteArray();
	}
}
