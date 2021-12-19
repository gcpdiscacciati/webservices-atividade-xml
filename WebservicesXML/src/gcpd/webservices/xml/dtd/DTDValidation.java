package gcpd.webservices.xml.dtd;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DTDValidation {
	
	private boolean erro = false;

	public boolean isErro() {
		return erro;
	}

	public void setErro(boolean erro) {
		this.erro = erro;
	}

	public static void start() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DTDValidation dtdValidation = new DTDValidation();	
		dbf.setValidating(true);
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		docBuilder.setErrorHandler(new ErrorHandler() {
		    @Override
		    public void error(SAXParseException exception) throws SAXException {
				dtdValidation.setErro(true);
		    }
		    @Override
		    public void fatalError(SAXParseException exception) throws SAXException {
		    	dtdValidation.setErro(true);
		    }
		    @Override
		    public void warning(SAXParseException exception) throws SAXException {
		    	dtdValidation.setErro(true);
		    }
		});
		docBuilder.parse("src/pessoa.xml");
		if(dtdValidation.isErro())
			System.out.println("XML Inválido");
		else
			System.out.println("XML Válido");
			
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		start();
	}

	

}
