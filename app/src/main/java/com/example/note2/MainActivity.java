package com.example.note2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.note2.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerlayout;
    NavigationView navigationview;
    //@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        anhXa();
//        actionToolBar();
//
//        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                switch ((item.getItemId()))
//        {
//            case R.id.mSetting:
//            {
//                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_LONG).show();
//                break;
//            }
//
//            case R.id.mLogout:
//            {
//                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case R.id.mSupport:
//            {
//                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }
//                return false;
//            }
//        });
//    }
//    private void anhXa() {
//        toolbar = (Toolbar) findViewById(R.id.toolBar);
//        drawerlayout = (DrawerLayout) findViewById (R.id.drawerLayout);
//        navigationview = (NavigationView) findViewById(R.id.navigationView);
//    }
//
//    private void actionToolBar() {
//        setSupportActionBar(toolbar);
//        // Lo???i b??? ti???u ????? m???c ?????nh
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //M???c ?????nh n???u b???n mu???n hi???n th??? bi???u t?????ng c?? h??nh m??i t??n nh?? l?? n??t b???m quay tr??? l???i c???a s??? tr?????c
//        toolbar.setNavigationIcon(R.drawable.ic_account);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerlayout.openDrawer(GravityCompat.START);
//            }
//            //????? m??? Drawer , khi ng?????i d??ng ch???m v??o button
//        });
//    }
//


//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch ((item.getItemId()))
//        {
//            case R.id.mSetting:
//            {
//                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_LONG).show();
//                break;
//            }
//
//            case R.id.mLogout:
//            {
//                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case R.id.mSupport:
//            {
//                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}