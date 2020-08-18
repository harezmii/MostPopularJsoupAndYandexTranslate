package com.example.myproject;
import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


public class TranslateYandex  extends AsyncTask<String,Void,String> {
    String yandex_api_key = "trnsl.1.1.20130922T110455Z.4a9208e68c61a760.f819c1db302ba637c2bea1befa4db9f784e9fbb8";
    String request_url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";


    @Override
    protected String doInBackground(String... strings) {
        String parametre  = strings[0];
        try{
            // Kullanılacak URL oluşturulması
            URL url = new URL(request_url+yandex_api_key+"&text="+parametre+"&lang="+"en"+"-"+"tr");

            // URL bağlantısı yapıldı.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));

            // Gelen json verisini parse etme
            String jsonVerisi;
            StringBuilder stringBuilder = new StringBuilder();

            while ((jsonVerisi = bufferRead.readLine())!= null){
                    stringBuilder.append(jsonVerisi).append("\n");
            }

            bufferRead.close();
            stream.close();
            connection.disconnect();

            // json parse işlemi
            JSONObject obj = new JSONObject(stringBuilder.toString().trim());

            if(obj.getString("code").equals("200")) {

                String resultString = obj.getString("text");

                resultString = resultString.replaceAll("[,\"\\[\\]]", "");

                return resultString;
            }
            else return "";

        }catch (Exception e){
            System.out.println("Hata Var");
            return "";
        }


    }
}
