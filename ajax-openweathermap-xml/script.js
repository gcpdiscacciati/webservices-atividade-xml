var form = document.getElementById("formPrevisao");

var inputTexto = document.getElementById("cidade");

inputTexto.addEventListener('keyup', function(e){
	if(e.key === 'Enter'){
		handleSubmit();
	}
});

// Retorna os dados para o HTML
function viewResult(resposta){
	var divPrincipal = document.getElementById("principal");
	divPrincipal.style.display = "block";
	divPrincipal.innerHTML = resposta;
}

// Cria o mapa, posicionando-o nas coordenadas recebidas na requisição
function createMap(divMapa, latitude, longitude){
	
	
	map = new google.maps.Map(divMapa, {
		center: { 
			lat: latitude,
			lng: longitude, 
		},
		zoom: 12,
	  });

	new google.maps.Marker({
		position: {
			lat: latitude,
			lng: longitude,
		},
		map,
	});

	
}

//Gera a requisição baseada no nome da cidade informada pelo usuário e trata os dados da resposta.
function handleSubmit(){
	var cidade = document.getElementById("cidade").value;
	var url = 'http://api.openweathermap.org/data/2.5/weather?q='+cidade+'&units=metric&lang=pt_br&appid=658fd70109ca8add32d1c140ceb3729d&mode=xml'

	var req = new XMLHttpRequest();

	req.onreadystatechange = function (){
		
		if(req.readyState == XMLHttpRequest.DONE) {
			if (req.status == 0 || (req.status >= 200 && req.status < 400)) {
				respXML = req.responseXML;
				var nome = respXML.getElementsByTagName('city')[0].getAttribute('name');
				var temperatura_atual = respXML.getElementsByTagName('temperature')[0].getAttribute('value');
				var temperatura_min = respXML.getElementsByTagName('temperature')[0].getAttribute('min');
				var temperatura_max = respXML.getElementsByTagName('temperature')[0].getAttribute('max');
				var condicao_icon = respXML.getElementsByTagName('weather')[0].getAttribute('icon');
				var nascer = new Date(respXML.getElementsByTagName('sun')[0].getAttribute('rise') + 'Z');
				var por = new Date(respXML.getElementsByTagName('sun')[0].getAttribute('set') + 'Z');
				var latitude = parseFloat(respXML.getElementsByTagName('coord')[0].getAttribute('lat'));
				var longitude = parseFloat(respXML.getElementsByTagName('coord')[0].getAttribute('lon'));
				var previsao = '<div>'+
									'<div id="nome">'+nome+'</div>'+
									'<div id="temperatura">'+
										'<div id="imagem"><img src="http://openweathermap.org/img/wn/'+condicao_icon+'@2x.png"></div>'+
										'<div id="temp_atual">'+temperatura_atual+'°C</div>'+
									'</div>'+
									'<div id="info">'+
										'<div id="max_min">'+
											'<p>Máxima: '+temperatura_max+'°C</p>' +
											'<p>Mínima: '+ temperatura_min+'°C</p>' +
										'</div>'+
										'<div id="hora_sol">'+
											'<p id="hora_nascer">Nascer do Sol: '+nascer.toLocaleTimeString('pt-BR')+
											'<p id="hora_por">Pôr do Sol: '+por.toLocaleTimeString('pt-BR')+
										'</div>'+
									'</div>';

				viewResult(previsao);
				var divMapa = document.getElementById("mapa");
				divMapa.style.display = "block";
				createMap(divMapa, latitude, longitude);

				
			} else {
			  alert("Cidade não encontrada");
			}
		}
	}

	req.open("GET", url, true);
	req.send();
	
};
