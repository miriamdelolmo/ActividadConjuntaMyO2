package com.example.actividadconjuntamyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    static Helper helper;
    SQLiteDatabase db;

    EditText nombre, peso, buscarNombre;
    Spinner tipo;
    CheckBox rotten;
    ListView lv;
    TextView txtView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new Helper(this);
        txtView2=findViewById((R.id.textView2));
        buscarNombre=findViewById(R.id.busquedaNombre);
        nombre=findViewById(R.id.nombrefruta);
        peso=findViewById(R.id.peso);
        tipo=findViewById(R.id.tipo);
        rotten=findViewById(R.id.rotten);
        lv = findViewById(R.id.lv);
    }


    public void insertar(View view) {
        if (nombre.getText().toString().isEmpty() || peso.getText().toString().isEmpty()){
            Toast.makeText(this,"Faltan huecos por rellenar",Toast.LENGTH_SHORT).show();
            return;

        } else{

            if (rotten.isSelected()){
                db=helper.getWritableDatabase();
                ContentValues values= new ContentValues();
                values.put("nombre", nombre.getText().toString());
                values.put("peso", peso.getText().toString());
                values.put("tipo", peso.getText().toString());
                values.put("rotten", "rotten");
                db.insert("fruitis", null, values);

                nombre.setText("");
                peso.setText("");
                rotten.setChecked(false);

                db.close();

                Toast.makeText(this,"Se han insertado los datos",Toast.LENGTH_SHORT).show();

            }else{
                db=helper.getWritableDatabase();
                ContentValues values= new ContentValues();
                values.put("nombre", nombre.getText().toString());
                values.put("peso", peso.getText().toString());
                values.put("tipo", peso.getText().toString());
                values.put("rotten", "");
                db.insert("fruitis", null, values);

                nombre.setText("");
                peso.setText("");


                db.close();

                Toast.makeText(this,"Se han insertado los datos",Toast.LENGTH_SHORT).show();

            }


        }
    }


    public void metodoMostrar(View view) {
        mostrarDatos();
    }

    public void mostrarDatos(){
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("fruitis", null, null, null, null, null, null);

        //adaptamos el cursor a nuestro ListView
        String[] from = {"nombre", "peso", "tipo", "rotten"};
        int[] to = {R.id.nombrefruta, R.id.peso,R.id.tipo, R.id.rotten};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();

    }


    public void mostrarUltimo(View view) {
        lv.getEmptyView();
        mostrarUlt();
    }

    public void mostrarUlt(){

        db=helper.getReadableDatabase();
        String[] columns={"nombre","peso", "tipo", "rotten"};
        String selection="";
        String[] selectionArgs=null;
        String groupBy=null;
        String having=null;
        String orderBy="_ID";
        Cursor cursor = db.query("fruitis", columns, selection, selectionArgs, groupBy, having, orderBy);
        //mostrar contenido
        mostrarTabla(cursor);

    }



    public void metodoMostrarPorNombre(View view) {
        lv.getEmptyView();
        String nombre = buscarNombre.getText().toString();
        mostrarNombre(nombre);
    }

    public void mostrarNombre(String nombre){

        db=helper.getReadableDatabase();
        String[] columns={"nombre","peso", "tipo", "rotten"};
        String selection="nombre="+nombre;
        String[] selectionArgs=null;
        String groupBy=null;
        String having=null;
        String orderBy="_ID";
        Cursor cursor = db.query("fruitis", columns, selection, selectionArgs, groupBy, having, orderBy);
        //mostrar contenido
        mostrarTabla(cursor);

    }

    private void mostrarTabla(Cursor c) {
        //los mostramos en el cuadro de texto que tenemos en el layout
        txtView2.append("\n Tabla operas \n-----------");
        c.moveToFirst();
        int nfilas=c.getCount();
        int ncolumnas=c.getColumnCount();
        String fila="\n";
        for (int i = 0; i < nfilas; i++) {
            fila="\n";
            for(int j=0;j<ncolumnas;j++){
                fila=fila+c.getString(j)+" ";
            }
            txtView2.append(fila);
            c.moveToNext();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opc1:
                pag1();
            case R.id.opc2:
               // pag2();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void pag1() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

   /* public void pag2() {
        Intent i = new Intent(this,LayoutMostrar.class);
        startActivity(i);
    }

*/





}