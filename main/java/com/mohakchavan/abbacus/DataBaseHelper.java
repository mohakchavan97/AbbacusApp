package com.mohakchavan.abbacus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "database.sqlite";
    private static int DB_VER = 1;
    private final String DB_FILE;
    private final Context mContext;

    private static String TABLE_NAME = "quiz";

    public static final String questionId = "q_id";
    public static final String questionText = "question";
    public static final String option1 = "opt1";
    public static final String option2 = "opt2";
    public static final String option3 = "opt3";
    public static final String option4 = "opt4";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        DB_FILE = context.getDatabasePath(DB_NAME).getPath();
        mContext = context;
//        openDataBase();
        if (!checkExistenceOfDB()) {
            copyDataBase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        openDataBase();
//        sqLiteDatabase.execSQL("CREATE TABLE \"quiz\" (\"q_id\" INTEGER PRIMARY KEY  NOT NULL ,\"question\" VARCHAR,\"opt1\" VARCHAR,\"opt2\" VARCHAR,\"opt3\" VARCHAR,\"opt4\" VARCHAR)");

//        final SQLiteDatabase db = getReadableDatabase();
//        db.close();
//        copyDataBase();

    }

    private boolean openDataBase() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(String.valueOf(DB_FILE), null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return db != null;
    }

    public void copyDataBase() {

        try {
            InputStream inputStream = mContext.getResources().openRawResource(R.raw.database);
//                InputStream inputStream = mContext.getAssets().open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(DB_FILE);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkExistenceOfDB() {
        File file = new File(DB_FILE);
        if (file.exists()) return true;
        File parent = new File(file.getParent());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        return false;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<QuestionModel> getAllData() {
        final SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.query(TABLE_NAME, new String[]{questionId, questionText, option1, option2, option3, option4}, null, null, null, null, questionId);
        List<QuestionModel> questions = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                questions.add(new QuestionModel(
                        cursor.getInt(cursor.getColumnIndex(questionId)),
                        cursor.getString(cursor.getColumnIndex(questionText)),
                        cursor.getString(cursor.getColumnIndex(option1)),
                        cursor.getString(cursor.getColumnIndex(option2)),
                        cursor.getString(cursor.getColumnIndex(option3)),
                        cursor.getString(cursor.getColumnIndex(option4))
                ));
            } while (cursor.moveToNext());
        }
        db.close();
        return questions;
    }
}
