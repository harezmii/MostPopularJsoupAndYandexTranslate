package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {
    TextView detail,words;
    ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detail = findViewById(R.id.detail);
        photo = findViewById(R.id.photo);
        words = findViewById(R.id.words);

        detail.setMovementMethod(new ScrollingMovementMethod());
        words.setMovementMethod(new ScrollingMovementMethod());

        ArrayList<String> list = MainActivity.bodyList;

        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("photo")).placeholder(R.drawable.giphy).into(photo);
        detail.setText(list.get(Integer.parseInt(intent.getStringExtra("body"))));

        // tıklanılan elemanın body kısmında ki String de  en çok tekrar eden 10 kelimeyi bulma
        String[] translateWord = Repeat(list.get(Integer.parseInt(intent.getStringExtra("body"))));

        // türkçe çevirisi yapılmış kelimeler
        String[] backTranslateWorld = new String[10];

        try {
            for (int i = 0; i < 10; i++) {
                backTranslateWorld[i] = ceviri(translateWord[i]);
            }

        } catch (Exception e) {

        }

        String temp = "";
        for(int i=0;i<10;i++){

            temp += translateWord[i]+" <===> "+backTranslateWorld[i]+"  ,";
            if(i%2 == 0){
                if(i ==0){
                    continue;
                }
                temp +="\n";
            }
        }

        words.setText(temp);


    }

    public static String[] Repeat(String backData) {
        String[] listem = new String[10];

        String[] list = backData.split(" ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();

        // kelimelerden kaç defa tekrar edilmesi
        for (String i : list) {
            if (result.containsKey(i)) result.put(i, result.get(i) + 1);
            else result.put(i, 1);
        }
        Map sortedMap = sortByValue(result);

        // kelimlerin sort edilmesi
        int count = 0;
        for (Object key : sortedMap.keySet()) {
            if (count < 10) {
                listem[count] = key.toString();
                count++;
            }
        }


        return listem;

    }
    // yandex api translate kullanımı
    public static String ceviri(String liste) {
        String result = null;
        TranslateYandex translateYandex = new TranslateYandex();
        try {

            result = translateYandex.execute(liste).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    // en çok kullanılan 10 kelime için sıralama
    public static Map sortByValue(Map unsortedMap) {
        Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }




}
// en çok kullanılan 10 kelime için karşılaştırma
class ValueComparator implements Comparator {
    Map map;

    public ValueComparator(Map map) {
        this.map = map;
    }

    public int compare(Object keyA, Object keyB) {
        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);
        return valueB.compareTo(valueA);
    }
}