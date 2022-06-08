package com.example.trabalhoavaliativopdm2.connection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MyConnection {

    private static MyConnection instance;
    private InputStream inputStream;

    private MyConnection(){
    }

    public static MyConnection getInstance(){
        if(instance == null){
            instance = new MyConnection();
        }
        return instance;
    }

    public InputStream getResponseHTTPS(String address) {
        inputStream = null;
            URL url;
                try {
                    url = new URL(address);
                    HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                    conection.setRequestMethod("GET");
                    inputStream = new BufferedInputStream(conection.getInputStream());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
        return inputStream;
    }

    public String getStringResponseHTTPS(String address){
        instance.getResponseHTTPS(address);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder  = new StringBuilder();
        String content = "";
        try{
            while((content = bufferedReader.readLine())!=null){
                stringBuilder.append(content).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
