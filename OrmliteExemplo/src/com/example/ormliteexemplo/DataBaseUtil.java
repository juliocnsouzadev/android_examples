package com.example.ormliteexemplo;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

//classe utilitaria responsavel pela criação do banco fornecimento de conexoes com bd
public class DataBaseUtil extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "ihomecare.db";
	private static final int DATABASE_VERSION = 1;

	public DataBaseUtil(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static ConnectionSource getConnectionSource(Context context) {
		return new DataBaseUtil(context).getConnectionSource();
	}

	// metodo responsavel pela criacao das tabelas
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Pessoa.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// metodo responsavel pelos drops tables
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldDbVersion, int newDbVersion) {
		try {
			TableUtils.dropTable(connectionSource, Pessoa.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
