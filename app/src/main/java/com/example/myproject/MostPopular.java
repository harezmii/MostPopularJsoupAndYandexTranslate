package com.example.myproject;

import android.os.AsyncTask;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MostPopular extends AsyncTask<Void,Void, List<ArrayList<String>>> {

    static ArrayList<String> authorList = new ArrayList<String>();
    static ArrayList<String> bodyList = new ArrayList<String>();
    static ArrayList<String> timeList = new ArrayList<String>();
    static ArrayList<String> photoList = new ArrayList<String>();
    static ArrayList<String> headerList = new ArrayList<String>();
    List<ArrayList<String>> liste = new ArrayList<>();

    @Override
    protected List<ArrayList<String>> doInBackground(Void... voids) {


        try {
            Document document = Jsoup.connect("https://www.wired.com/most-popular").get();
            // li:lt(5) 5 tane eleman çekmek için limit uygulandı.
            Elements items = document.select("ul.archive-list-component__items li:lt(5)");

            Elements time = items.select("div.archive-item-component__byline time");
            Elements link = items.select("li.archive-item-component a.archive-item-component__link");
            Elements author = items.select("a.byline-component__link");
            Elements header = items.select("h2.archive-item-component__title");
            Elements photo = items.select("div.image-group-component img");

            // Author List
            int i = 0;
            for (Element element : author) {
                if (i == 1) {
                    break;
                }
                authorList.add(element.text());
            }
            liste.add(authorList);


            // Time List
            for (Element element : time) {
                i = 0;
                if (i == 1) {
                    break;
                }
                timeList.add(element.text());
            }
            liste.add(timeList);

            // Header List
            for (Element element : header) {
                headerList.add(element.text());
            }
            liste.add(headerList);

            // Photo List
            for (Element element : photo) {
                photoList.add(element.attr("src"));

            }
            liste.add(photoList);

            // Article Detail List
            for (Element element : link) {
                String ccc = element.attr("abs:to");

                Document document1 = Jsoup.connect(ccc.toString()).get();
                Elements items1 = document1.select("div.article__chunks p");
                bodyList.add(items1.text());

            }
            // Body verisi 2 defa tekrar ettiği için böyle bir yola başvurdum.
            ArrayList<String> tempBody = new ArrayList<String>();
            for(int ii=0;ii<10;ii++)
            {
                if(ii % 2 == 0){
                    tempBody.add(bodyList.get(ii));

                }

            }
            liste.add(tempBody);

        } catch (HttpStatusException ex) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        return liste;
    }
}
