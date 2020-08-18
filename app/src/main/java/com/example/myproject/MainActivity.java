package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    private static final int INTERNET_STORAGE_PERMISSION_CODE = 100 ;
    List<ArticleModel> list = new ArrayList<>();
    ListView listView;

    static ArrayList<String> authorList;
    static ArrayList<String> timeList;
    static ArrayList<String> headerList;
    static ArrayList<String> photoList;
    static ArrayList<String> bodyList;
    List<ArrayList<String>> liste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission(Manifest.permission.INTERNET,INTERNET_STORAGE_PERMISSION_CODE);

        try {
            MostPopular mostPopular = new MostPopular();
            liste = mostPopular.execute().get();

            authorList = liste.get(0);
            timeList = liste.get(1);
            headerList = liste.get(2);
            photoList = liste.get(3);
            bodyList = liste.get(4);


        } catch (Exception e) {

        }

        for (int i = 0; i < 5; i++) {
            list.add(new ArticleModel(authorList.get(i), photoList.get(i), headerList.get(i), timeList.get(i), bodyList.get(i)));
        }


        listView = findViewById(R.id.listview);
        ArticleAdapter articleAdapter = new ArticleAdapter(getApplicationContext(), list);
        listView.setAdapter(articleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Object data = adapterView.getAdapter().getItem(position);
                ArticleModel articleModelData = (ArticleModel) data;


                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("header", articleModelData.getHeader());
                intent.putExtra("photo", articleModelData.getPhoto());
                intent.putExtra("body", String.valueOf(position));

                startActivity(intent);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == INTERNET_STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"İnternet İzni Verildi",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"İnternet İzni Verilmedi",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void permission(String permission, int requestcode){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) ==PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[] { permission},requestcode);
        }
        else {
            Toast.makeText(getApplicationContext(),"İzin Zaten Var",Toast.LENGTH_SHORT);
        }
    }

    public static String cevir(String s) {
        String result = null;
        TranslateYandex translateYandex = new TranslateYandex();
        try {
            result = translateYandex.execute(s).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void run(){

    }

}
