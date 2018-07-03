package br.com.softblue.android.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.InputStream;
import java.util.Scanner;

import br.com.softblue.android.R;
import br.com.softblue.android.util.StringUtils;

// Classe para acesso ao banco de dados SQLite
// Usa o padrão singleton
public class DBHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "moneydb";
	public static final int DB_VERSION = 1;

	private static DBHelper instance;
	
	private Context context;

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context.getApplicationContext());
		}
		return instance;
	}

	private DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// O script de criação do banco de dados está armazenado em res/raw/db_script.txt
		InputStream in = context.getResources().openRawResource(R.raw.db_script);
		Scanner scanner = new Scanner(in);
		scanner.useDelimiter(";");
		try {
			while (scanner.hasNext()) {
				String sql = scanner.next().trim();
				
				if (!StringUtils.isEmptyOrWhiteSpaces(sql)) {
					db.execSQL(sql);
				}
			}
		} finally {
			scanner.close();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Não faz nada
	}

	@Override
	// Chamado pelo Android quando o banco de dados é aberto
	public void onOpen(SQLiteDatabase db) {
	    super.onOpen(db);
	    
	    // Habilita as chaves estrangeiras. O SQLite exige que isto seja feito a cada
	    // abertura do banco de dados
	    if (!db.isReadOnly()) {
	    	db.execSQL("PRAGMA foreign_keys=1;");
	    }
	}
}
