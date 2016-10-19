package main.android_database.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import main.android_database.R;
import main.android_database.db.SchoolContract;
import main.android_database.db.SchoolDbHelper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btInserir = (Button) findViewById(R.id.bt_inserir);
        btInserir.setOnClickListener(onButtonInsertClick());
        Button btAtualizar = (Button) findViewById(R.id.bt_atualizar);
        btAtualizar.setOnClickListener(onButtonUpdateClick());
        Button btRemover = (Button) findViewById(R.id.bt_remover);
        btRemover.setOnClickListener(onButtonDeleteClick());
        Button btConsultar = (Button) findViewById(R.id.bt_consultar);
        btConsultar.setOnClickListener(onButtonQueryClick());
    }

    public Context getContext() {
        return this;
    }

    public View.OnClickListener onButtonInsertClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.edit_name);
                EditText editGrade = (EditText) findViewById(R.id.edit_grade);

                String name = editName.getText().toString();
                int grade = Integer.parseInt(editGrade.getText().toString());

                SchoolDbHelper dbHelper = new SchoolDbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(SchoolContract.Student.COLUMN_NAME_STUDENT_NAME, name);
                values.put(SchoolContract.Student.COLUMN_NAME_STUDENT_GRADE, grade);

                long newId = db.insert(SchoolContract.Student.TABLE_NAME, null, values);

                Toast toast = Toast.makeText(getContext(), "Registro Adicionado. ID = " + newId,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        };
    }

    public View.OnClickListener onButtonUpdateClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.edit_name);
                EditText editGrade = (EditText) findViewById(R.id.edit_grade);

                String name = editName.getText().toString();
                int grade = Integer.parseInt(editGrade.getText().toString());

                SchoolDbHelper dbHelper = new SchoolDbHelper(getContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                ContentValues values = new ContentValues();

                values.put(SchoolContract.Student.COLUMN_NAME_STUDENT_GRADE, grade);

                String selection = SchoolContract.Student.COLUMN_NAME_STUDENT_NAME + " LIKE ?";
                String[] selectionArgs = {name};

                int count = db.update(SchoolContract.Student.TABLE_NAME, values, selection, selectionArgs);

                Toast toast = Toast.makeText(getContext(), "Registros atualizados: " + count,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        };
    }

    public View.OnClickListener onButtonDeleteClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.edit_name);

                String name = editName.getText().toString();

                SchoolDbHelper dbHelper = new SchoolDbHelper(getContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String selection = SchoolContract.Student.COLUMN_NAME_STUDENT_NAME + " LIKE ?";
                String[] selectionArgs = {name};

                int count = db.delete(SchoolContract.Student.TABLE_NAME, selection, selectionArgs);

                Toast toast = Toast.makeText(getContext(), "Registros deletados: " + count,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        };
    }

    public View.OnClickListener onButtonQueryClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.edit_name);

                String name = editName.getText().toString();

                SchoolDbHelper dbHelper = new SchoolDbHelper(getContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String[] projection = {SchoolContract.Student._ID,
                        SchoolContract.Student.COLUMN_NAME_STUDENT_NAME,
                        SchoolContract.Student.COLUMN_NAME_STUDENT_GRADE};
                String selection = SchoolContract.Student.COLUMN_NAME_STUDENT_NAME + " LIKE ?";
                String[] selectionArgs = {name + "%"};
                String sortOrder = SchoolContract.Student.COLUMN_NAME_STUDENT_GRADE + " DESC";

                Cursor cursor = db.query(SchoolContract.Student.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);

                ArrayList<CharSequence> data = new ArrayList<>();

                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    String entry = cursor.getInt(
                            cursor.getColumnIndex(SchoolContract.Student._ID)) + ": ";
                    entry += cursor.getString(
                            cursor.getColumnIndex(SchoolContract.Student.COLUMN_NAME_STUDENT_NAME));
                    entry += " = ";
                    entry += cursor.getInt(
                            cursor.getColumnIndex(SchoolContract.Student.COLUMN_NAME_STUDENT_GRADE));
                    data.add(entry);
                    cursor.moveToNext();
                }

                Intent intent = new Intent(getContext(), QueryResultActivity.class);
                intent.putCharSequenceArrayListExtra("data", data);
                startActivity(intent);
            }
        };
    }
}
