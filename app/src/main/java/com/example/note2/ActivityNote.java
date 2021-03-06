package com.example.note2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityNote extends AppCompatActivity {
    ListView lv;
    TextView textView;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FloatingActionButton buttonADD;
    public static ArrayList<Note> arrNote;
    NoteAdapter noteAdapter;
    String stringTD;
    String stringND;
    public static final String USERNAME = "USERNAME";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Anhxa();
        AcctionBar();
       // intent = getIntent();
        //USER = intent.getStringExtra(ActiLogin.USERNAME); // l???y username ??c g???i t??? acti login(c?? th??? c?? ho???c kh??ng)

        sharedPreferences = getSharedPreferences("HisLog", MODE_PRIVATE);
        String user = sharedPreferences.getString("username", ""); //l???y username trong b??? nh???(khi ???? ????ng nh???p th??nh c??ng)
        //v?? khi ????ng nh???p th??nh c??ng, username s??? ??c l??u, v?? 1 l??c ch??? c?? 1 username n??n c?? th??? get t??? b??? nh??? thay v?? ??c truy???n t??? trang login

        // s??? ki???n nav bar
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch ((item.getItemId()))
                {
                    case R.id.mSetting:
                    {
                        Intent intent = new Intent(ActivityNote.this, ActivitySetting.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.mLogout:
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); //clear b??? nh???(ch???a d??? li???u ????ng nh???p)
                        editor.commit();

                        Intent intent = new Intent(ActivityNote.this, ActiLogin.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.mSupport:
                    {
                        Toast.makeText(ActivityNote.this, "Ch???c n??ng ??ang ho??n thi???n", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.mAll:
                    {
                        noteAdapter.fillterNhan("");
                        break;
                    }
                    case R.id.mBlue:
                    {
                        noteAdapter.fillterNhan("1"); //t??m ki???m theo nh??n c???a note
                        break;
                    }
                    case R.id.mGreen:
                    {
                        noteAdapter.fillterNhan("2");
                        break;
                    }
                    case R.id.mOrange:
                    {
                        noteAdapter.fillterNhan("3");
                        break;
                    }
                    case R.id.mGrey:
                    {
                        noteAdapter.fillterNhan("4");
                        break;
                    }
                }
                return false;
            }
        });

        //Load listview note
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadLV(user).execute("http://192.168.1.2/msNote/JSONnote.php");
            }
        });

        buttonADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNote.this, ActivityADD.class);
                intent.putExtra(USERNAME, user); //truy???n username sang acti th??m(ho???c sang ???? get d??? li???u t??? b??? nh???)
                startActivity(intent);
            }
        });
        //acti xem chi ti???t v?? ch???nh s???a
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentDetail = new Intent(ActivityNote.this, ActivityNoteDetail.class);
                Bundle bundle = new Bundle(); //truy???n d??? li???u sang b??n actiNoteDetail ????? hi???n th???
                bundle.putInt("ID", arrNote.get(position).ID); //ID d??ng ????? s???a ho???c xo??
                bundle.putString("TD", arrNote.get(position).TieuDe);
                bundle.putString("ND", arrNote.get(position).NoiDung);
                bundle.putString("CL", arrNote.get(position).Nhan);
                intentDetail.putExtras(bundle);
                startActivity(intentDetail);
            }
        });

    }

    private void Anhxa()
    {
        lv = findViewById(R.id.listNote);
        buttonADD = findViewById(R.id.btnThem);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.layoutNote);
        navigationView = findViewById(R.id.nav_note);


        //c??u l???nh quan tr???ng, cho ph??p icon hi???n th??? m??u g???c c???a n??, kh??ng b??? ???? b???i m??u h??? th???ng
        navigationView.setItemIconTintList(null);

        textView = findViewById(R.id.textView2);
       // imageView = findViewById(R.id.imageViewNote);
    }
    private void AcctionBar()
    {
        setSupportActionBar(toolbar); //set toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //hi???n th??? 3 d???u g???ch ????? m??? navigation
        actionBar.setHomeAsUpIndicator(R.drawable.ic_draw); //icon 3 d???u g???ch
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START); //m??? navi
            }
        });
    }

    //search t??m ki???m
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusearch, menu); //set menu

         SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) //s??? ki???n khi nh???t submit
            {
                Toast.makeText(ActivityNote.this, query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) //t??m ngay l??c c?? thay ?????i n???i dung t??m ki???m
            {
                Log.d("Search", newText);
                noteAdapter.fillter(newText.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    class  LoadLV extends AsyncTask<String, String, String>
    {
        OkHttpClient client = new OkHttpClient();
        String user;
        public LoadLV(String user) {
            this.user = user;
        } //load list note theo username ??ang login
        @Override
        protected String doInBackground(String... strings)
        {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("username", user)
                    .setType(MultipartBody.FORM)
                    .build();

            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //textView.setText(s);
            arrNote = new ArrayList<Note>();
            try{
                JSONArray jsonArray = new JSONArray(s);
                for(int i = 0 ; i < jsonArray.length(); i++)
                {
                    JSONObject note = jsonArray.getJSONObject(i);
                    Note n = new Note(){
                    };
                    int id = note.getInt("id");
                    String us = note.getString("username");
                    String TieuDe = note.getString("TieuDe");
                    String NoiDung = note.getString("NoiDung");
                    String nhan = note.getString("Nhan");

                    stringTD = TieuDe;
                    stringND = NoiDung;

                    n.ID = id;
                    n.username=us;
                    n.TieuDe=TieuDe;
                    n.NoiDung=NoiDung;
                    n.Nhan=nhan;
                    arrNote.add(n);
                }
//                ArrayAdapter adapter = new ArrayAdapter(
//                        ActivityNote.this, android.R.layout.simple_list_item_1,arrNote
//                );
                noteAdapter = new NoteAdapter(
                        ActivityNote.this,
                        R.layout.row_note,
                        arrNote
                        )
                {
                    //hi???n th??? m??u backgroud t???ng note
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        switch (arrNote.get(position).Nhan)
                        {
                            case "1":
                                view.setBackgroundColor(getResources().getColor(
                                        android.R.color.holo_blue_light
                                ));
                                break;
                            case "2":
                                view.setBackgroundColor(getResources().getColor(
                                        android.R.color.holo_green_dark
                                ));
                                break;
                            case "3":
                                view.setBackgroundColor(getResources().getColor(
                                        android.R.color.holo_orange_light
                                ));
                                break;
                            case "4":
                                view.setBackgroundColor(getResources().getColor(
                                        android.R.color.darker_gray
                                ));
                                break;
                        }
                        return view;
                    }
                };
                lv.setAdapter(noteAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

}