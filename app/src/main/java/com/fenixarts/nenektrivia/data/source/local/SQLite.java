package com.fenixarts.nenektrivia.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.game.domain.models.Questions;
import com.fenixarts.nenektrivia.utils.Print;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 24/01/18 - 12:30.
 */

@SuppressWarnings("unused")
public class SQLite {

    private static final String TAG = "SQLite";
    private SQLiteDbHelper sqLiteHelper;
    private SQLiteDatabase db;

    SQLite(Context context) {
        sqLiteHelper = new SQLiteDbHelper(context);
    }

    public void open() {
        db = sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteHelper.close();
    }

    /*
     * INSERT
     * */

    /**
     * Inserta datos de los mejores jugadores
     * @param item BestPlayer Data
     * @return numero de registros insertados en la tabla
     */
    long insertBestPlayers(BestPlayersItem item){
        checkNotNull(item, "BestPlayerItem Can't be null");

        ContentValues values = new ContentValues();
        values.put(PersistenceContract.BestPlayer.COLUMN_NAME_ID,item.getId());
        values.put(PersistenceContract.BestPlayer.COLUMN_NAME_IMAGE,item.getImage());
        values.put(PersistenceContract.BestPlayer.COLUMN_NAME_USERNAME,item.getUsername());
        values.put(PersistenceContract.BestPlayer.COLUMN_NAME_POINTS,item.getPoints());

        open();
        long insert = db.insert(PersistenceContract.BestPlayer.TABLE_NAME, null, values);
        close();

        return insert;
    }

    long insertQuestion(Questions question) {
        checkNotNull(question, "Questions Can't be null");

        ContentValues values = new ContentValues();
        values.put(PersistenceContract.Questions.COLUMN_NAME_ID,question.getId());
        values.put(PersistenceContract.Questions.COLUMN_NAME_CATEGORY,question.getCategory());
        values.put(PersistenceContract.Questions.COLUMN_NAME_ANSWER_BAD_01,question.getAnswerbad01());
        values.put(PersistenceContract.Questions.COLUMN_NAME_ANSWER_BAD_02,question.getAnswerbad02());
        values.put(PersistenceContract.Questions.COLUMN_NAME_ANSWER_BAD_03,question.getAnswerbad03());
        values.put(PersistenceContract.Questions.COLUMN_NAME_ANSWER_GOOD,question.getAnswergood());
        values.put(PersistenceContract.Questions.COLUMN_NAME_QUESTION,question.getQuestion());

        open();
        long insert = db.insert(PersistenceContract.Questions.TABLE_NAME, null, values);
        close();

        return insert;
    }

    /*
     * SELECT
     * */

    /**
     * Selecciona datos de los mejores jugadores
     * @param id llave de datos a mostrar
     * @return datos almacenados
     */
    public Cursor selectBestPlayers(@Nonnull String id){
        String[] columns = new String[]{
                PersistenceContract.BestPlayer.COLUMN_NAME_ID,
                PersistenceContract.BestPlayer.COLUMN_NAME_IMAGE,
                PersistenceContract.BestPlayer.COLUMN_NAME_USERNAME,
                PersistenceContract.BestPlayer.COLUMN_NAME_POINTS };
        String where = PersistenceContract.BestPlayer.COLUMN_NAME_ID +" = ?";
        String[] args = new String[]{ id };
        return db.query(PersistenceContract.BestPlayer.TABLE_NAME, columns, where, args, null, null, null);
    }

    Cursor selectAllQuestions() {
        return db.query(PersistenceContract.Questions.TABLE_NAME, null, null, null, null, null, null);
    }

    /**
     * Selecciona todos los datos de los mejores jugadores
     * @return datos almacenados
     */
    Cursor selectAllBestPlayers(){
        String[] columns = new String[]{
                PersistenceContract.BestPlayer.COLUMN_NAME_ID,
                PersistenceContract.BestPlayer.COLUMN_NAME_IMAGE,
                PersistenceContract.BestPlayer.COLUMN_NAME_USERNAME,
                PersistenceContract.BestPlayer.COLUMN_NAME_POINTS };

        return db.query(PersistenceContract.BestPlayer.TABLE_NAME, columns, null, null, null, null, null);
    }

    /*
     * PRINT QUERY
     * */

    /**
     * Imprime los datos en una lista
     * @param cursor datos almacenados
     * @param n numero de columnas
     * @return lista de datos almacenados
     */
    List<String> printList(Cursor cursor, int n){
        checkNotNull(cursor, "Cursor Can't be null");
        List<String> lista = new ArrayList<>();
        try {
            if( cursor.moveToFirst() ){
                do{
                    for(int num = 0 ; num < n ; num++){
                        lista.add(cursor.getString(num));
                    }
                }while( cursor.moveToNext() );
            }
            cursor.close();
        }catch (IllegalStateException e){
            Print.e(TAG,e.getMessage(), e.getCause());
        }
        return lista;
    }

    /**
     * Imprime los datos en un JSONArray
     * @param cursor datos almacenados
     * @return JSONArray con datos almacenados
     * @throws JSONException excepcion de json
     */
    JSONArray printList(Cursor cursor) throws JSONException {
        checkNotNull(cursor, "Cursor Can't be null");

        JSONArray resultSet = new JSONArray();
        if( cursor.moveToFirst() ){
            do{
                JSONObject rowObject = processRows(cursor);
                resultSet.put(rowObject);
            }while( cursor.moveToNext() );
        }
        return resultSet;
    }

    /**
     * Procesa columnas en JSONObject
     * @param cursor datos almacenados
     * @return JSONObject con datos almacenados
     * @throws JSONException excepcion de json
     */
    private JSONObject processRows(Cursor cursor) throws JSONException {
        int totalColumn = cursor.getColumnCount();
        JSONObject rowObject = new JSONObject();
        for( int i=0 ;  i< totalColumn ; i++ ){
            if( cursor.getColumnName(i) != null ){
                if( cursor.getString(i) != null ){
                    String content = cursor.getString(i).replace("&","%26");
                    rowObject.put(cursor.getColumnName(i) ,  content );
                }else{
                    rowObject.put( cursor.getColumnName(i) ,  "" );
                }
            }
        }
        return rowObject;
    }

    /*
     * UPDATE
     * */

    /**
     * Actualiza datos de los mejores jugadores
     * @param id identificador del usuario
     * @param image imagen del usuario
     * @param username nombre del usuario
     * @param points puntos
     * @return numero de actualizaciones en la tabla
     */
    public int updateBestPlayers(@Nonnull String id, @Nonnull String image, @Nonnull String username, int points){
        ContentValues values = new ContentValues();
        values.put(PersistenceContract.BestPlayer.COLUMN_NAME_IMAGE, image);
        values.put(PersistenceContract.BestPlayer.COLUMN_NAME_USERNAME, username);
        values.put(PersistenceContract.BestPlayer.COLUMN_NAME_POINTS, points);

        String where = PersistenceContract.BestPlayer.COLUMN_NAME_ID + " = ?";
        String[] args = new String[]{ id };
        return db.update(PersistenceContract.BestPlayer.TABLE_NAME, values, where, args);
    }

    /*
     * DELETE
     * */

    /**
     * Borrar datos de la tabla de datos genericos
     * @return numero de datos eliminados
     */
    int deleteBestPlayers(){
        open();
        int delete = db.delete(PersistenceContract.BestPlayer.TABLE_NAME, null, null);
        close();

        return delete;
    }

    /**
     * Borrar datos de la tabla de datos genericos
     * @param id llave de los datos a borrar
     * @return numero de datos eliminados
     */
    public int deleteBestPlayers(@Nonnull String id){
        String where = PersistenceContract.BestPlayer.COLUMN_NAME_ID +" = ?";
        String[] args = new String[]{ id };
        return db.delete(PersistenceContract.BestPlayer.TABLE_NAME, where, args);
    }

    int deleteAllQuestions() {
        open();
        int delete = db.delete(PersistenceContract.Questions.TABLE_NAME, null, null);
        close();

        return delete;
    }

}
