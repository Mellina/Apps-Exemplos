package br.com.softblue.android.dao;

import android.content.ContentResolver;
import android.content.Context;

// Superclasse de todos os DAOs da aplicação
public abstract class DAO {

    private Context context;
    private ContentResolver cr;

    DAO() {
    }

    void init(Context context) {
        this.context = context;
        this.cr = context.getContentResolver();
    }

    protected Context context() {
        return context;
    }

    protected ContentResolver contentResolver() {
        return cr;
    }
}
