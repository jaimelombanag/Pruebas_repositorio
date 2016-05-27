package com.encuesta.rightway.clases;

/**
 * Created by jalombanag on 28/04/2016.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class database {

    public static final String MYDATABASE_NAME = "MY_DATABASE";
    public static final int MYDATABASE_VERSION = 1;


    public static final String MYDATABASE_TABLE = "HOGAR";
    public static final String KEY_ID = "_id";
    public static final String KEY_IDHOGAR = "idhogar";
    public static final String FECHAHORA = "fechahora";
    public static final String DIRECCION = "direccion";
    public static final String ESTRATO = "estrato";
    public static final String NFAMILIAS = "nfamilias";

    public static final String MYDATABASE_TABLE1 = "FAMILIA";
    public static final String KEY_ID1 = "_id";
    public static final String KEY_IDHOGARF= "idhogarf";
    public static final String KEY_IDFAMILIA= "idfamilia";
    public static final String NVEHICULOS= "nvehiculos";
    public static final String NINTEGRANTES= "nintegrantes";

    public static final String MYDATABASE_TABLE2 = "VEHICULOS";
    public static final String KEY_ID2 = "_id";
    public static final String KEY_IDHOGARV= "idhogarv";
    public static final String KEY_IDFAMILIAV= "idfamiliav";
    public static final String TIPO= "tipo";
    public static final String MATRICULA= "matricula";
    public static final String PARQUEO= "parqueo";
    public static final String PROPIEDAD= "propiedad";
    public static final String COMBUSTIBLE= "combustible";
    public static final String CILINDRAJE= "cilindrale";


    public static final String MYDATABASE_TABLE3 = "INTEGRANTES";
    public static final String KEY_ID3 = "_id";
    public static final String KEY_IDHOGARI= "idhogari";
    public static final String KEY_IDFAMILIAI= "idfamiliai";
    public static final String SEXO= "sexo";
    public static final String EDAD= "edad";
    public static final String NIVEL_ESTUDIO= "nivel_estudio";
    public static final String OCUPACION= "ocupacion";
    public static final String SECTOR_ECONOMICO= "sector_economico";
    public static final String DIR_TRABAJO= "dir_trabajo";
    public static final String DIR_ORIGEN= "dir_origen";
    public static final String HORA_IN= "hora_in";
    public static final String DIR_DESTINO= "dir_destino";
    public static final String HORA_FIN= "hora_fin";
    public static final String MOTIVO_VIAJE= "motivo_viaje";
    public static final String MODO_TRANSPORTE= "modo_transporte";
    public static final String DIAS= "dias";
    public static final String TIPO_TRANSPORTE= "tipo_transporte";
    public static final String DATOS_TRANSPORTE= "datos_transporte";
    public static final String NECESITA_AYUDA= "necesita_ayuda";
    public static final String COSTO_VIAJE= "costo_viaje";





    //create table MY_DATABASE (ID integer primary key, Content text not null);
    private static final String SCRIPT_CREATE_DATABASE =
            "create table " + MYDATABASE_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_IDHOGAR + " text not null, "
                    + FECHAHORA + " text not null, "
                    + DIRECCION+ " text not null, "
                    + ESTRATO + " text not null,"
                    + NFAMILIAS+ " text not null);";

    private static final String SCRIPT_CREATE_DATABASE1 =
            "create table " + MYDATABASE_TABLE1 + " ("
                    + KEY_ID1 + " integer primary key autoincrement, "
                    + KEY_IDHOGARF+ " text not null, "
                    + KEY_IDFAMILIA + " text not null,"
                    + NVEHICULOS + " text not null,"
                    + NINTEGRANTES+ " text not null);";

    private static final String SCRIPT_CREATE_DATABASE2 =
            "create table " + MYDATABASE_TABLE2 + " ("
                    + KEY_ID2 + " integer primary key autoincrement, "
                    + KEY_IDHOGARV+ " text not null, "
                    + KEY_IDFAMILIAV + " text not null,"
                    + TIPO + " text not null,"
                    + MATRICULA + " text not null,"
                    + PARQUEO + " text not null,"
                    + PROPIEDAD + " text not null,"
                    + COMBUSTIBLE + " text not null,"
                    + CILINDRAJE+ " text not null);";


    private static final String SCRIPT_CREATE_DATABASE3 =
            "create table " + MYDATABASE_TABLE3 + " ("
                    + KEY_ID3 + " integer primary key autoincrement, "
                    + KEY_IDHOGARI+ " text not null, "
                    + KEY_IDFAMILIAI + " text not null,"
                    + SEXO + " text not null,"
                    + EDAD + " text not null,"
                    + NIVEL_ESTUDIO + " text not null,"
                    + OCUPACION + " text not null,"
                    + SECTOR_ECONOMICO + " text not null,"
                    + DIR_TRABAJO + " text not null,"
                    + DIR_ORIGEN + " text not null,"
                    + HORA_IN + " text not null,"
                    + DIR_DESTINO + " text not null,"
                    + HORA_FIN + " text not null,"
                    + MOTIVO_VIAJE + " text not null,"
                    + MODO_TRANSPORTE + " text not null,"
                    + DIAS + " text not null,"
                    + TIPO_TRANSPORTE + " text not null,"
                    + DATOS_TRANSPORTE + " text not null,"
                    + NECESITA_AYUDA + " text not null,"
                    + COSTO_VIAJE+ " text not null);";




    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public database(Context c){
        context = c;
    }

    public database openToRead() throws android.database.SQLException {

        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public database openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqLiteHelper.close();
    }

    public long insertHogar(String idhogar,String fechahora, String direccion, String estrato,String nfamilias){

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IDHOGAR, idhogar);
        contentValues.put(FECHAHORA, fechahora);
        contentValues.put(DIRECCION, direccion);
        contentValues.put(ESTRATO,estrato);
        contentValues.put(NFAMILIAS, nfamilias);
        return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);

    }
    public long insertFamilia(String idhogarf, String idfamilia ,String nvehiculos,String nintegrantes){

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(KEY_IDHOGARF, idhogarf);
        contentValues1.put(KEY_IDFAMILIA,idfamilia);
        contentValues1.put(NVEHICULOS,nvehiculos);
        contentValues1.put(NINTEGRANTES,nintegrantes);
        return sqLiteDatabase.insert(MYDATABASE_TABLE1, null, contentValues1);


    }

    public long insertVehiculo(String idhogarv, String idfamiliav ,String tipo,String matricula, String parqueo,
                        String propiedad, String combustible, String cilindraje){

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(KEY_IDHOGARV, idhogarv);
        contentValues2.put(KEY_IDFAMILIAV,idfamiliav);
        contentValues2.put(TIPO,tipo);
        contentValues2.put(MATRICULA,matricula);
        contentValues2.put(PARQUEO,parqueo);
        contentValues2.put(PROPIEDAD,propiedad);
        contentValues2.put(COMBUSTIBLE,combustible);
        contentValues2.put(CILINDRAJE,cilindraje);
        return sqLiteDatabase.insert(MYDATABASE_TABLE2, null, contentValues2);

    }


    public long insertIntegrante(String idhogari, String idfamiliai ,String sexo,String edad, String nivel_estudio,
                        String ocupacion, String sector_economico, String dir_trabajo, String dir_origen,
                        String hora_in, String dir_destino, String hora_fin, String motivo_viaje, String modo_transporte,
                        String dias, String tipo_transporte,String datos_transporte, String necesita_ayuda, String costo_viaje){

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(KEY_IDHOGARI, idhogari);
        contentValues3.put(KEY_IDFAMILIAI,idfamiliai);
        contentValues3.put(SEXO,sexo);
        contentValues3.put(EDAD,edad);
        contentValues3.put(NIVEL_ESTUDIO,nivel_estudio);
        contentValues3.put(OCUPACION,ocupacion);
        contentValues3.put(SECTOR_ECONOMICO,sector_economico);
        contentValues3.put(DIR_TRABAJO,dir_trabajo);
        contentValues3.put(DIR_ORIGEN,dir_origen);
        contentValues3.put(HORA_IN,hora_in);
        contentValues3.put(DIR_DESTINO,dir_destino);
        contentValues3.put(HORA_FIN,hora_fin);
        contentValues3.put(MOTIVO_VIAJE,motivo_viaje);
        contentValues3.put(MODO_TRANSPORTE,modo_transporte);
        contentValues3.put(DIAS,dias);
        contentValues3.put(TIPO_TRANSPORTE,tipo_transporte);
        contentValues3.put(DATOS_TRANSPORTE,datos_transporte);
        contentValues3.put(NECESITA_AYUDA,necesita_ayuda);
        contentValues3.put(COSTO_VIAJE,costo_viaje);
        return sqLiteDatabase.insert(MYDATABASE_TABLE3, null, contentValues3);

    }




    //public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
    public long update(String idhogar, String fechahora, String direccion, String estrato,String nfamilias,int id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IDHOGAR, idhogar);
        contentValues.put(FECHAHORA, fechahora);
        contentValues.put(DIRECCION, direccion);
        contentValues.put(ESTRATO,estrato);
        contentValues.put(NFAMILIAS,nfamilias);
        System.out.println(id);

        return sqLiteDatabase.update(MYDATABASE_TABLE, contentValues, KEY_ID + "=" + id, null);
    }

    public int deleteAll(){
        return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
    }

    public void delete_byID(int id){
        sqLiteDatabase.delete(MYDATABASE_TABLE, KEY_ID+"="+id, null);
    }
    public void delete_byID1(int id){
        sqLiteDatabase.delete(MYDATABASE_TABLE1, KEY_ID+"="+id, null);
    }
    public void delete_byID2(int id){
        sqLiteDatabase.delete(MYDATABASE_TABLE2, KEY_ID+"="+id, null);
    }

    public void delete_byID3(int id){
        sqLiteDatabase.delete(MYDATABASE_TABLE3, KEY_ID+"="+id, null);
    }



    public Cursor integrantesAll(){
        String[] columns3 = new String[]{KEY_ID2, KEY_IDHOGARI,KEY_IDFAMILIAI,SEXO,EDAD, NIVEL_ESTUDIO, OCUPACION,SECTOR_ECONOMICO,
                DIR_TRABAJO, DIR_ORIGEN, HORA_IN, DIR_DESTINO, HORA_FIN, MOTIVO_VIAJE, MODO_TRANSPORTE, DIAS, TIPO_TRANSPORTE, DATOS_TRANSPORTE, NECESITA_AYUDA, COSTO_VIAJE};
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE3, columns3,
                null, null, null, null, null, null);

        return cursor;
    }


    public Cursor vehiculosAll(){
        String[] columns2 = new String[]{KEY_ID2, KEY_IDHOGARV,KEY_IDFAMILIAV,TIPO,MATRICULA, PARQUEO, PROPIEDAD,COMBUSTIBLE,CILINDRAJE};
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE2, columns2,
                null, null, null, null, null, null);

        return cursor;
    }


    public Cursor FamiliasAll(){
        String[] columns1 = new String[]{KEY_ID1, KEY_IDHOGARF,KEY_IDFAMILIA,NVEHICULOS,NINTEGRANTES};
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE1, columns1,
                null, null, null, null, null);

        return cursor;
    }
    public Cursor HogaresAll(){
        String[] columns = new String[]{KEY_ID, KEY_IDHOGAR, FECHAHORA, DIRECCION, ESTRATO, NFAMILIAS};
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
                null, null, null, null, null, null);

        return cursor;
    }
    public Cursor queueAll2(int id){

        String a="SELECT * FROM ST WHERE _id = "+id;

        Cursor cursor=sqLiteDatabase.rawQuery(a, null);

        return cursor;
    }
    public Cursor queueAll3(int id){
        //String[] columns = new String[]{KEY_ID, SID,SNAME,SROLL};
        String a="SELECT * FROM MT,ST WHERE _id = "+id;
        //Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
        // null, null, null, null, null);
        Cursor cursor=sqLiteDatabase.rawQuery(a, null);

        return cursor;
    }


    public void exportDB() {


        final String inFileName = "/data/data/com.encuesta.rightway.encuesta/databases/"+MYDATABASE_NAME;
        File dbFile = new File(inFileName);
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(dbFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String directorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        File d = new File(directorio);
        if (!d.exists()) {
            d.mkdir();
        }
        String outFileName = directorio  +MYDATABASE_NAME;

        OutputStream output = null;
        try {
            output = new FileOutputStream(outFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[1024];
        int length;

        try {
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.flush();
            output.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(SCRIPT_CREATE_DATABASE);
            db.execSQL(SCRIPT_CREATE_DATABASE1);
            db.execSQL(SCRIPT_CREATE_DATABASE2);
            db.execSQL(SCRIPT_CREATE_DATABASE3);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }

}