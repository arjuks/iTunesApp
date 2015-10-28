package com.example.arjun.inclass7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Note> apps = new ArrayList<>();
    ArrayList<Note> list = new ArrayList<>();
    AppAdapter adapter;
    static final String APPNAME = "appname";
    static final String IMG = "image";
    static final String STAR ="star";
    DataManager dm ;
    int flag =0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm =  new DataManager(this);


        final String url = "https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/json";

        Log.d("demo", url);
        new ProgressTask().execute(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.switchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
        if (id == R.id.fav) {
            Log.d("demo", "fav clicked");
            ArrayList<Note> listfav = new ArrayList<>();
            listfav.addAll(dm.getAllNotes());
            ListView listview = (ListView) findViewById(R.id.listView);
//            ImageView iv = (ImageView) listview.findViewById(R.id.startimg);
//            Drawable drawable = MainActivity.this.getResources().getDrawable(R.drawable.goldstar);
//            iv.setImageDrawable(drawable);
            adapter = new AppAdapter(MainActivity.this, R.layout.itemlayout, listfav);
            listview.setAdapter(adapter);
            adapter.setNotifyOnChange(true);

            return true;
        }
        if(id == R.id.showall){
            Log.d("demo", "showall clicked");
            final String url = "https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/json";

            Log.d("demo", url);
            new ProgressTask().execute(url);

        }

        return super.onOptionsItemSelected(item);
    }

    private class ProgressTask extends AsyncTask<String , Void , ArrayList<Note>> {


        @Override
        protected ArrayList<Note> doInBackground(String... params) {

            JSONParser jParser = new JSONParser();

            try {
                publishProgress();
                JSONObject root =  jParser.getJSONFromUrl(params[0]);
                JSONObject feed2 = root.getJSONObject("feed");
                JSONArray entry = feed2.getJSONArray("entry");

                Log.d("demo","the entry is"+entry);


                for(int i = 0; i < entry.length(); i++) {
                    JSONObject price = entry.getJSONObject(i);
                    JSONObject p = price.getJSONObject("im:price");
                    JSONObject attr = p.getJSONObject("attributes");
                    String amount = attr.getString("amount");
                    JSONObject category = entry.getJSONObject(i);
                    JSONObject c = category.getJSONObject("category");
                    JSONObject atc = c.getJSONObject("attributes");
                    String term = atc.getString("term");
                    JSONObject date = entry.getJSONObject(i);
                    JSONObject d = date.getJSONObject("im:releaseDate");
                    JSONObject a = d.getJSONObject("attributes");
                    String label = a.getString("label");
                    JSONObject developer = entry.getJSONObject(i);
                    JSONObject dev = developer.getJSONObject("im:artist");
                    String de = dev.getString("label");
                    JSONObject appname = entry.getJSONObject(i);
                    JSONObject appn = appname.getJSONObject("im:name");
                    String app = appn.getString("label");
                    JSONObject i2 = entry.getJSONObject(i);
                    JSONArray image = i2.getJSONArray("im:image");
                    JSONObject im= image.getJSONObject(1);
                    String li = im.getString("label");
                    Log.d("demo","image"+li);

                    Note n = new Note();
                    n.setAppName(app);
                    n.setCategory(term);
                    n.setDate(label);
                    n.setDevName(de);
                    n.setPrice(amount);
                    n.setImgurl(li);
                    n.setIsfav(app);

                    list.add(i,n);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Results ...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Note> result) {
            super.onPostExecute(result);
            if(result != null){
                Log.d("demo", "result" + result);

                progressDialog.dismiss();
                apps.clear();
                apps.addAll(result);
                ListView listview = (ListView) findViewById(R.id.listView);
                adapter = new AppAdapter(MainActivity.this, R.layout.itemlayout, apps);
                listview.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String appname = apps.get(position).getAppName();
                        String imgurl = apps.get(position).getImgurl();
                        Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                        intent.putExtra(APPNAME, appname);
                        intent.putExtra(IMG, imgurl);

                        ArrayList<Note> list = new ArrayList<Note>();
                        list = (ArrayList<Note>) dm.getAllNotes();
                        if (list.size() == 0) {
                            String star = "grey";
                            Log.d("demo","set as grey star");
                            intent.putExtra(STAR, star);
                            startActivity(intent);
                        }
                        else if(list.size() != 0) {
                            for (int i = 0; i <= list.size() - 1; i++) {
                                if (list.get(i).getAppName().toString().trim().equals(appname.toString().trim())) {
                                    String star = "gold";
                                    Log.d("demo","set as gold star");
                                    intent.putExtra(STAR, star);
                                    startActivity(intent);
                                    flag = 0;
                                }
                                else {
                                    flag =1;
                                }
                            }
                            if(flag ==1) {
                                String star = "grey";
                                Log.d("demo","set as grey star");
                                intent.putExtra(STAR, star);
                                startActivity(intent);
                            }
                        }


                        Log.d("demo", "Position " + position);
                    }
                });

                listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        ImageView image = (ImageView) view.findViewById(R.id.starimg);
                        Log.d("demo", "clicked" + " " + position);
                        String appname = apps.get(position).getAppName();
                        Log.d("demo", "appname clicked" + " " + appname);
                        ArrayList<Note> list = new ArrayList<Note>();
                        list = (ArrayList<Note>) dm.getAllNotes();
                        Log.d("demo","size is"+list.size());
                        String isfav ="isfav";
                        if (list.size() == 0) {
                            Log.d("demo", "inside if");
                            dm.saveNote(new Note(apps.get(position).getAppName(),
                                    apps.get(position).getDevName(),
                                    apps.get(position).getDate(),
                                    apps.get(position).getPrice(),
                                    apps.get(position).getCategory(),
                                    apps.get(position).getImgurl(),
                                    isfav));
                            Toast.makeText(MainActivity.this, "Saved item", Toast.LENGTH_SHORT).show();
                            Drawable drawable = MainActivity.this.getResources().getDrawable(R.drawable.goldstar);
                            image.setImageDrawable(drawable);
                        }
                        else if(list.size() !=0) {
                            Log.d("demo", "inside else if");
                            for (int i = 0; i <=list.size() -1; i++) {
                                System.out.println("index"+ i);
                                if (list.get(i).getAppName().toString().trim().equals(appname.toString().trim())) {
                                    Log.d("demo", "inside second if stat");
                                    dm.deleteNote(list.get(i));
                                    Drawable drawable = MainActivity.this.getResources().getDrawable(R.drawable.greystar);
                                    image.setImageDrawable(drawable);
                                    Log.d("demo", "deleted note");
                                    Toast.makeText(MainActivity.this, "Deleted item", Toast.LENGTH_SHORT).show();
                                    flag = 0;
                                }
                                else {
                                    flag =1;
                                    Log.d("demo", "flag set as 1");
                                }
                            }
                        }

                        if(flag ==1) {
                            Log.d("demo", "inside flag");
                            dm.saveNote(new Note(apps.get(position).getAppName(),
                                    apps.get(position).getDevName(),
                                    apps.get(position).getDate(),
                                    apps.get(position).getPrice(),
                                    apps.get(position).getCategory(),
                                    apps.get(position).getImgurl(),
                                    isfav));
                            Drawable drawable = MainActivity.this.getResources().getDrawable(R.drawable.goldstar);
                            image.setImageDrawable(drawable);
                            Toast.makeText(MainActivity.this, "Saved item", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
            }
        }
    }
    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }
}
