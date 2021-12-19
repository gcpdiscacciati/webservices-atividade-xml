package gcpd.webservices.xml.xsd;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class XSDValidation {

	public static boolean validateXSD(String xsd, String xml){

		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xml)));
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static void start() {
		if(validateXSD("src/pessoa_schema.xml", "src/pessoa.xml"))
			System.out.println("XML Válido");
		else
			System.out.println("XML Inválido");

	}

	public static void main(String[] args) {
		start();

	}


}
