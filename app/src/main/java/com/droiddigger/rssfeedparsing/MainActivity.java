package com.droiddigger.rssfeedparsing;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {
   ArrayList<NewsItems>myData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadRss readRss = new ReadRss(this,myData);
        readRss.execute();
    }



    public void getData(ArrayList<NewsItems>items){
        for (int i=0; i<items.size(); i++){
            myData.add(items.get(i));

        }
    }
    class ReadRss extends AsyncTask<Void, Void, Void>{

         ArrayList<NewsItems>feedItems;// = new ArrayList<>();
        Context context;
        String address = "http://www.thedailystar.net/frontpage/rss.xml";
        ProgressDialog progressDialog;
        URL url;

        public ReadRss(Context context,ArrayList<NewsItems>items) {
            this.context = context;
            feedItems=items;
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected void onPreExecute() {
            if(progressDialog!=null){
                if (!progressDialog.isShowing()){
                    progressDialog.show();
                }
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog!=null){
                if (progressDialog.isShowing()){
                    progressDialog.hide();
                }
            }
            getData(feedItems);

            Log.d("TAG", String.valueOf(feedItems.size()));
        }

        @Override
        protected Void doInBackground(Void... params) {
            ProcessXml(Getdata());
            return null;
        }

        private void ProcessXml(Document data) {

            if (data != null) {

                Element root = data.getDocumentElement();
                Node channel = root.getChildNodes().item(1);
                NodeList items = channel.getChildNodes();
                for (int i = 0; i < items.getLength(); i++) {
                    Node currentchild = items.item(i);
                    if (currentchild.getNodeName().equalsIgnoreCase("item")) {
                        NewsItems item=new NewsItems();
                        NodeList itemchilds = currentchild.getChildNodes();
                        for (int j = 0; j < itemchilds.getLength(); j++) {
                            Node current = itemchilds.item(j);
                            if (current.getNodeName().equalsIgnoreCase("title")){
                                item.setTitle(current.getTextContent());
                            }else if (current.getNodeName().equalsIgnoreCase("description")){
                                item.setDescription(current.getTextContent());
                            }else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")){
                                item.setMedia(current.getAttributes().getNamedItem("url").getTextContent());
                            }else if (current.getNodeName().equalsIgnoreCase("link")){
                                item.setUrl(current.getTextContent());
                            }
                        }
                        feedItems.add(item);
                        Log.d("itemTitle", item.getTitle());
                        Log.d("itemDescription",item.getDescription());
                        Log.d("itemMediaLink",item.getMedia());
                        Log.d("itemLink",item.getUrl());

                    }
                }
            }

        }



        public Document Getdata() {
            try {
                url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document xmlDoc = builder.parse(inputStream);
                return xmlDoc;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }


}
