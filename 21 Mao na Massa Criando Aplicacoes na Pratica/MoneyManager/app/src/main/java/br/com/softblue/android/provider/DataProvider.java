package br.com.softblue.android.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

// Content provider para acessar o banco de dados SQLite
public class DataProvider extends ContentProvider {

	private static final String AUTHORITY = "br.com.softblue.android.provider.DataProvider";

	public static final Uri CONTENT_DESPESAS_URI = Uri.parse("content://" + AUTHORITY + "/despesas");
	public static final Uri CONTENT_CATEGORIAS_URI = Uri.parse("content://" + AUTHORITY + "/categorias");
	public static final Uri CONTENT_RELATORIO_URI = Uri.parse("content://" + AUTHORITY + "/relatorio");

	static final int CODE_DESPESA = 1;
	static final int CODE_DESPESA_ID = 2;
	static final int CODE_CATEGORIA = 3;
	static final int CODE_CATEGORIA_ID = 4;
	static final int CODE_RELATORIO = 5;

	private UriMatcher matcher;

	// Os handlers são usados para tratar requisições, de acordo com o padrão da URI utilizada
	private DataHandler despesaHandler;
	private DataHandler categoriaHandler;
	private DataHandler relatorioHandler;

	@Override
	public boolean onCreate() {
		// Cria os handlers
		despesaHandler = new DespesaHandler(getContext());
		categoriaHandler = new CategoriaHandler(getContext());
		relatorioHandler = new RelatorioHandler(getContext());

		// Configura o URI matcher
		matcher = new UriMatcher(UriMatcher.NO_MATCH);

		matcher.addURI(AUTHORITY, "despesas", CODE_DESPESA);
		matcher.addURI(AUTHORITY, "despesas/#", CODE_DESPESA_ID);

		matcher.addURI(AUTHORITY, "categorias", CODE_CATEGORIA);
		matcher.addURI(AUTHORITY, "categorias/#", CODE_CATEGORIA_ID);

		matcher.addURI(AUTHORITY, "relatorio", CODE_RELATORIO);

		return true;
	}

	// Executa uma query, delegando a operação ao handler correspondente
	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		int code = matcher.match(uri);
		DataHandler handler = getHandler(code);
		Cursor c = handler.query(code, uri, projection, selection, selectionArgs, sortOrder);
		handler.setNotificationUri(c, uri);
		return c;
	}

	// Insere um dado, delegando a operação ao handler correspondente
	@Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
		int code = matcher.match(uri);
		DataHandler handler = getHandler(code);
		Uri newUri = handler.insert(code, uri, values);
		handler.notifyChange(newUri);
		return newUri;
	}
	
	// Atualiza um dado, delegando a operação ao handler correspondente
	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int code = matcher.match(uri);
		DataHandler handler = getHandler(code);
		int count = handler.update(code, uri, values, selection, selectionArgs);
		handler.notifyChange(uri);
		return count;
	}

	// Exclui um dado
	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
		int code = matcher.match(uri);
		DataHandler handler = getHandler(code);
		int count = handler.delete(code, uri, selection, selectionArgs);
		handler.notifyChange(uri);
		return count;
	}

	@Override
	public String getType(@NonNull Uri uri) {
		int code = matcher.match(uri);

		if (code == CODE_DESPESA_ID || code == CODE_CATEGORIA_ID || code == CODE_RELATORIO) {
			return "vnd.android.cursor.item/vnd.br.com.softblue.android";

		} else if (code == CODE_DESPESA || code == CODE_CATEGORIA) {
			return "vnd.android.cursor.dir/vnd.br.com.softblue.android";
		}

		throw new IllegalArgumentException("A URI não é suportada: " + uri);
	}

	// Obtém o handler associado a um código
	private DataHandler getHandler(int code) {
		if (code == CODE_DESPESA || code == CODE_DESPESA_ID) {
			return despesaHandler;

		} else if (code == CODE_CATEGORIA || code == CODE_CATEGORIA_ID) {
			return categoriaHandler;

		} else if (code == CODE_RELATORIO) {
			return relatorioHandler;
		}

		return null;
	}
}
