package br.com.softblue.android.webservice;

import android.content.ContentUris;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.softblue.android.http.HttpCall;
import br.com.softblue.android.http.HttpResponse;
import br.com.softblue.android.model.Cidade;
import br.com.softblue.android.model.Temperatura;

// Proxy para acesso ao web service
public class WebServiceProxy {
	private static final Uri WEB_SERVICE_CONTENT = Uri.parse("http://code.softblue.com.br:8080/web/rest/weather");

	// Obtém a lista de cidades
	public List<Cidade> listCidades() throws IOException, WebServiceException {
		try {
			// http://code.softblue.com.br:8080/web/rest/weather
			HttpCall http = new HttpCall(WEB_SERVICE_CONTENT.toString());

			// Executa uma operação GET
			HttpResponse response = http.execute(HttpCall.Method.GET);

			String responseStr = getResponseAsString(response);

			// Cria um JSONArray com base na resposta
			JSONArray array = new JSONArray(responseStr);

			List<Cidade> cidades = new ArrayList<>();

			// Itera sobre os elementos do array, criando as cidades
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObj = array.getJSONObject(i);
				cidades.add(Cidade.createFromJSON(jsonObj));
			}

			return cidades;
		} catch (JSONException e) {
			throw new WebServiceException("Erro ao processar JSON", e);
		}
	}
	
	// Obtém a temperatura de uma cidade específica
	public Temperatura obterTemperatura(int cidadeId) throws IOException, WebServiceException {
		try {
			// Coloca o ID no final da URL do web service
			// http://code.softblue.com.br:8080/web/rest/weather/#
			Uri uri = ContentUris.withAppendedId(WEB_SERVICE_CONTENT, cidadeId);
			HttpCall http = new HttpCall(uri.toString());

			// Executa uma operação GET
			HttpResponse response = http.execute(HttpCall.Method.GET);
			
			String responseStr = getResponseAsString(response);

			// Cria um JSONObject com base na resposta
			JSONObject jsonObj = new JSONObject(responseStr);
			
			// Cria um objeto temperatura com base no retorno
			return Temperatura.createFromJSON(jsonObj);
		
		} catch (JSONException e) {
			throw new WebServiceException("Erro ao processar JSON", e);
		}
	}

	// Transforma a resposta do HttpClient em uma String
	private String getResponseAsString(HttpResponse response) throws WebServiceException, IOException {
		// Lê o código de retorno do HTTP
		int code = response.getResponseCode();

		if (code != 200) {
			// Se o código não for de sucesso (200), lança exceção
			throw new WebServiceException("O HTTP retornou o código de erro " + code);
		}

		// Extrai a string da resposta
		return response.extractDataAsString("ISO-8859-1");
	}
}
