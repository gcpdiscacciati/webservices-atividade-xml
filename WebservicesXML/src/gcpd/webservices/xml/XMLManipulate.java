package gcpd.webservices.xml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLManipulate {
	
	//Imprime uma lista com todos os elementos do documento XML
	public static void listElements(Document xml) {
		NodeList nl = xml.getElementsByTagName("*");
		System.out.println("Listando Elementos:");
		for (int i = 0; i < nl.getLength(); i++) {
			Element element = (Element) nl.item(i);
			System.out.println("Elemento " + element.getNodeName());
		}
	}
	
	//Retorna a raiz do documento passado como parâmetro
	public static Node getRoot(Document xml) {
		return xml.getDocumentElement();
	}
	
	//Retorna o elemento filho de nome childName
	public static Node getChildByTagName(Node root, String childName) {
		Element rootElement = (Element) root;
		return rootElement.getElementsByTagName(childName).item(0);
	}
	
	//Retorna o indice de um elemento filho
	public static int getChildNodeIndex(Node root, Node child) {
		NodeList children = root.getChildNodes();
		int cont = 0;
		for(int i = 0; i < children.getLength(); i++) {
			if(child != null && root != null) {
				Node newChild = children.item(i);
				if(newChild.getNodeType() != Node.ELEMENT_NODE)
					cont++;
				if(newChild == child)
					return i - cont;
			}
		}
		return -1;
	}
	
	//Retorna o número total de livros
	public static int getTotalBooks(Node books) {
		int total = 0;
		for(int i = 0; i < books.getChildNodes().getLength(); i++) {
			Node node = books.getChildNodes().item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap atributos = node.getAttributes();
				for(int j=0; j<atributos.getLength(); j++) {
					Node atributo = atributos.item(j);
					if(atributo.getNodeName().equalsIgnoreCase("quantidade"))
						total += Integer.parseInt(atributo.getNodeValue());
				}
			}
		}
		return total;
	}
	
	//Imprime uma lista com o nome dos livros
	public static void listBookNames(Node books) {
		for(int i = 0; i < books.getChildNodes().getLength(); i++) {
			Node book = books.getChildNodes().item(i);
			if(book.getNodeType() == Node.ELEMENT_NODE) {
				NodeList elements = ((Element)book).getElementsByTagName("titulo");
				for(int j = 0; j<elements.getLength(); j++) {
					System.out.println("\t" + elements.item(j).getTextContent());
				}
			}
		}
		
	}
	
	//Imprime o endereço da biblioteca, a partir do CEP, atributo da tag "endereco", e uma pesquisa à API ViaCep para a busca dos demais dados.
	public static void printAddress(Node root) throws ParserConfigurationException, MalformedURLException, SAXException, IOException {
		Node address = getChildByTagName(root, "endereco");
		String cep = null;
		NamedNodeMap atributos = address.getAttributes();
		for(int i=0; i<atributos.getLength(); i++) {
			if(atributos.item(i).getNodeName().equalsIgnoreCase("cep")) {
				cep = atributos.item(i).getNodeValue();
				break;
			}
		}
		String url = new String("https://viacep.com.br/ws/"+cep+"/xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document xml = db.parse(new URL(url).openStream());
		Node rootResponse = getRoot(xml);
		System.out.println();
		System.out.println("Endereço");
		System.out.println("Logradouro: " + getChildByTagName(rootResponse, "logradouro").getTextContent());
		System.out.println("Número: " + getChildByTagName(root, "numero").getTextContent());
		System.out.println("Complemento: " + getChildByTagName(root, "complemento").getTextContent());
		System.out.println("Bairro: " + getChildByTagName(rootResponse, "bairro").getTextContent());
		System.out.println("Cidade: " + getChildByTagName(rootResponse, "localidade").getTextContent());
		System.out.println("Estado: " + getChildByTagName(rootResponse, "uf").getTextContent());
	}
	
	public static void start() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document xml = db.parse("src/biblioteca.xml");
		listElements(xml);
		Node root = getRoot(xml);
		System.out.println();
		System.out.println("Raiz: " + root);
		System.out.println("Conteúdo textual da raiz: " + root.getNodeName());
		NodeList children = root.getChildNodes();
		System.out.println("Elementos filhos da raiz:");
		for(int i=0; i<children.getLength(); i++) {
			if(children.item(i).getNodeType() == Node.ELEMENT_NODE)
				System.out.printf(" %s", children.item(i));
		}
		Node child = getChildByTagName(root, "livros");
		if(child!=null) {
			System.out.println();
			System.out.println("Busca elemento filho específico: " + child);
			
		}
		int index = getChildNodeIndex(root, child);
		if(index != -1)
			System.out.println("Índice do elemento " + child.getNodeName() + ": " + index);
		Node book = child.getChildNodes().item(1);
		if (book != null) {
			NamedNodeMap atributos = book.getAttributes();
			System.out.println("Atributos do elemento " + book.getNodeName() + ": ");
			for(int i=0; i<atributos.getLength(); i++) {
				Node atributo = atributos.item(i);
				System.out.printf("%s : %s   ", atributo.getNodeName(), atributo.getNodeValue());
			}
			
		}
		int totalBooks = getTotalBooks(child);
		System.out.println();
		System.out.println("Total de livros: " + totalBooks);
		System.out.println("Nome dos Livros:");
		listBookNames(child);
		printAddress(root);
		
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		start();
	}

	
}
