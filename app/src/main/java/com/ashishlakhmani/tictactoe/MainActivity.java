package com.ashishlakhmani.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPlayerName();
    }

    private void setPlayerName() {

    }

    public void onPlayClicked(View v) {
        EditText player1 = (EditText) findViewById(R.id.first_player);
        EditText player2 = (EditText) findViewById(R.id.second_player);

        Bundle bundle = new Bundle();
        bundle.putString("player1", player1.getText().toString());
        bundle.putString("player2", player2.getText().toString());

        final MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final Button button = (Button) findViewById(R.id.play_button);

        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFragment(fragment);
            }
        }, 700);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.front_layout, fragment);
        ft.commit();
    }

}
