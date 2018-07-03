package br.com.softblue.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;

public class Regioes {
	private static List<Regiao> regioes;

	public static List<Regiao> getRegioes(Context context, int xmlResource) {
		if (regioes == null) {
			try {
				regioes = createFromXML(context, xmlResource);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				return null;

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return regioes;
	}

	private static List<Regiao> createFromXML(Context context, int xmlResource) throws XmlPullParserException, IOException {
		XmlResourceParser parser = context.getResources().getXml(xmlResource);

		List<Regiao> regioes = new ArrayList<>();
		Regiao regiao = null;

		int eventType = parser.getEventType();

		while (eventType != XmlResourceParser.END_DOCUMENT) {
			if (eventType == XmlResourceParser.START_TAG && parser.getName().equals("regiao")) {
				String nome = parser.getAttributeValue(null, "nome");
				regiao = new Regiao(nome);
				regioes.add(regiao);

			} else if (eventType == XmlResourceParser.START_TAG && parser.getName().equals("estado")) {
				String nome = parser.getAttributeValue(null, "nome");
				String sigla = parser.getAttributeValue(null, "sigla");
				
				Estado estado = new Estado(nome, sigla);
                if (regiao != null) {
                    regiao.getEstados().add(estado);
                }
			}
			
			eventType = parser.next();
		}

		return regioes;
	}
}
