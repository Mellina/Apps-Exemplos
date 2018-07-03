package br.com.softblue.android.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import br.com.softblue.android.data.DBHelper;
import br.com.softblue.android.data.Produto;
import br.com.softblue.android.data.ProdutosContract;

public class ProdutoProvider extends ContentProvider {

    private static final String AUTHORITY = "br.com.softblue.android.provider.ProdutoProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/produtos");

    private static final int MATCH_ALL = 1;
    private static final int MATCH_ONE = 2;

    private SQLiteDatabase db;
    private UriMatcher matcher;

    @Override
    public boolean onCreate() {
        DBHelper dbHelper = DBHelper.getInstance(getContext());
        db = dbHelper.getWritableDatabase();

        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "/produtos", MATCH_ALL);
        matcher.addURI(AUTHORITY, "/produtos/#", MATCH_ONE);

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (matcher.match(uri) == MATCH_ONE) {
            String id = uri.getLastPathSegment();

            int count = db.delete(ProdutosContract.TABLE_NAME, ProdutosContract.Columns._ID + " = ?", new String[]{id});
            getContext().getContentResolver().notifyChange(uri, null);

            return count;
        }

        throw new IllegalArgumentException("A URI não é suportada");
    }

    @Override
    public String getType(Uri uri) {
       int type = matcher.match(uri);

        if (type == MATCH_ONE) {
            return "vnd.android.cursor.item/vnd.br.com.softblue.android";
        } else if (type == MATCH_ALL) {
            return "vnd.android.cursor.dir/vnd.br.com.softblue.android";
        }

        throw new IllegalArgumentException("A URI não é suportada");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = db.insert(ProdutosContract.TABLE_NAME, null, values);
        Uri newUri = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int type = matcher.match(uri);
        Cursor c;

        if (projection == null) {
            projection = new String[] {
                    ProdutosContract.Columns._ID,
                    ProdutosContract.Columns.NOME,
                    ProdutosContract.Columns.VALOR
            };
        }

        if (type == MATCH_ALL) {
            c = db.query(ProdutosContract.TABLE_NAME, projection, null, null, null, null, sortOrder);

        } else if (type == MATCH_ONE) {
            String id = uri.getLastPathSegment();
            c = db.query(ProdutosContract.TABLE_NAME, projection, ProdutosContract.Columns._ID + " = ?", new String[] { id }, null, null, sortOrder);

        } else {
            throw new IllegalArgumentException("A URI não é suportada");
        }

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (matcher.match(uri) == MATCH_ONE) {
            String id = uri.getLastPathSegment();

            int count = db.update(ProdutosContract.TABLE_NAME, values, ProdutosContract.Columns._ID + " = ?", new String[]{id});
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }

        throw new IllegalArgumentException("A URI não é suportada");
    }
}
