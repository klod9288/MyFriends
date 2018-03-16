package com.example.srongklod.myfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.srongklod.myfriends.fragment.Mainfragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        add Fragment Activity
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentMainFragment, new Mainfragment()).commit();
        }

    }//Main Method

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {

    }
}//Main Class
