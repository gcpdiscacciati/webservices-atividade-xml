# webservices-atividade-xml
Este repositório contém diferentes atividades e projetos.
O arquivo `atividade1.xml` contém a representação XML do seguinte objeto JSON:
```
{
  "tipo": "produto",
  "id": 42,
  "nome": "Smartphone Moto G",
  "preco": {
    "unidade": "R$",
    "a_vista": 519,
    "a_prazo": 630
  },
  "cameras": [
    {"tipo": "frontal", "resolução": 5, "unidade": "Mp"},
    {"tipo": "traseira", "resolução": 1.3, "unidade": "Mp"}
  ]
}
```
O diretório `WebServicesXML` contém 3 classes principais, `DTDValidation.java`, `XSDValidation.java` e `XMLManipulate.java`. Cada uma pode ser executada em um ambiente com o Java instalado (como a IDE Eclipse).

Já o diretório `ajax-openweathermap-xml` contém a mesma atividade do repositório https://github.com/gcpdiscacciati/webservices-ajax-previsao-tempo, porém utilizando a resposta XML da API para gerar a página de pesquisa abaixo.

![Captura de tela de uma página de consulta à previsão do tempo](https://github.com/gcpdiscacciati/webservices-atividade-xml/blob/main/ajax-openweathermap-xml/screenshot.png?raw=true)
