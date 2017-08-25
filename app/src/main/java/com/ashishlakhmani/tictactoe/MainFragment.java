package com.ashishlakhmani.tictactoe;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class MainFragment extends Fragment {

    private boolean chance = true;
    private int count = 0;
    private boolean isFinished = false;

    private TextView player1;
    private TextView player2;

    private ImageView player1Image;
    private ImageView player2Image;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;

    private ConstraintLayout first;
    private ConstraintLayout second;
    private ConstraintLayout third;
    private ConstraintLayout fourth;
    private ConstraintLayout fifth;
    private ConstraintLayout sixth;
    private ConstraintLayout seventh;
    private ConstraintLayout eighth;
    private ConstraintLayout ninth;

    private ImageView[][] imageArray;
    private ConstraintLayout[][] layoutArray;
    private Drawable.ConstantState[][] drawableArray = new Drawable.ConstantState[3][3];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    displayAlertForBack();
                    return true;
                }
                return false;
            }
        });

        initialize(view);
        setPlayerName();
        imageOnClick();
        return view;
    }


    private void initialize(View view) {

        //TEXT FIELDS INITIALIZE
        player1 = view.findViewById(R.id.player1);
        player2 = view.findViewById(R.id.player2);

        //IMAGEVIEW INITIALIZE
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        image6 = view.findViewById(R.id.image6);
        image7 = view.findViewById(R.id.image7);
        image8 = view.findViewById(R.id.image8);
        image9 = view.findViewById(R.id.image9);

        //LAYOUT INITIALIZE
        first = view.findViewById(R.id.first);
        second = view.findViewById(R.id.second);
        third = view.findViewById(R.id.third);
        fourth = view.findViewById(R.id.fourth);
        fifth = view.findViewById(R.id.fifth);
        sixth = view.findViewById(R.id.sixth);
        seventh = view.findViewById(R.id.seventh);
        eighth = view.findViewById(R.id.eighth);
        ninth = view.findViewById(R.id.ninth);


        ConstraintLayout[][] tempLayoutArray = {{first, second, third}, {fourth, fifth, sixth}, {seventh, eighth, ninth}};
        layoutArray = tempLayoutArray;

        ImageView[][] tempImageArray = {{image1, image2, image3}, {image4, image5, image6}, {image7, image8, image9}};
        imageArray = tempImageArray;

        player1Image = view.findViewById(R.id.finger_player1);
        player2Image = view.findViewById(R.id.finger_player2);
    }

    //SET BOTH PLAYERS' NAME
    private void setPlayerName() {

        if (getArguments().getString("player1") != null) {
            if (!getArguments().getString("player1").trim().isEmpty())
                player1.setText(getArguments().getString("player1"));
            else
                player1.setText("Player 1");
        }

        if (getArguments().getString("player2") != null) {
            if (!getArguments().getString("player2").trim().isEmpty())
                player2.setText(getArguments().getString("player2"));
            else
                player2.setText("Player 2");
        }


        if (chance) {
            player1.setBackgroundColor(Color.argb(255, 255, 64, 129));
            player1Image.setVisibility(View.VISIBLE);
        } else {
            player2.setBackgroundColor(Color.argb(255, 255, 64, 129));
            player2Image.setVisibility(View.VISIBLE);
        }

    }


    //ON CLICK EVENTS OF BOXES
    private void imageOnClick() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final ImageView local = imageArray[i][j];
                local.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        local.setOnClickListener(null);
                        if (chance) {
                            local.setImageResource(R.drawable.cross);
                            chance = !chance;
                        } else {
                            local.setImageResource(R.drawable.circle);
                            chance = !chance;
                        }
                        count++;


                        if (!chance)
                            checkPattern(player1.getText().toString());
                        else
                            checkPattern(player2.getText().toString());

                        if (isFinished)
                            return;


                        player1.setBackgroundColor(Color.TRANSPARENT);
                        player2.setBackgroundColor(Color.TRANSPARENT);
                        player1Image.setVisibility(View.INVISIBLE);
                        player2Image.setVisibility(View.INVISIBLE);
                        if (chance) {
                            player1.setBackgroundColor(Color.argb(255, 255, 64, 129));
                            player1Image.setVisibility(View.VISIBLE);
                        } else {
                            player2.setBackgroundColor(Color.argb(255, 255, 64, 129));
                            player2Image.setVisibility(View.VISIBLE);
                        }

                        countTask();
                    }
                });
            }
        }

    }


    private void checkPattern(String playerName) {

        for (int i = 0; i < 3; i++) {
            //HORIZONTAL CHECK
            try {
                if ((imageArray[i][0].getDrawable().getConstantState() != null && imageArray[i][1].getDrawable().getConstantState() != null && imageArray[i][2].getDrawable().getConstantState() != null) && imageArray[i][0].getDrawable().getConstantState().equals(imageArray[i][1].getDrawable().getConstantState()) && imageArray[i][1].getDrawable().getConstantState().equals(imageArray[i][2].getDrawable().getConstantState())) {
                    isFinished = true;
                    layoutArray[i][0].setBackgroundColor(Color.GREEN);
                    layoutArray[i][1].setBackgroundColor(Color.GREEN);
                    layoutArray[i][2].setBackgroundColor(Color.GREEN);
                    displayAlert(playerName);
                    return;
                }
            } catch (NullPointerException e) {
            }


            //VERTICAL CHECK
            try {
                if ((imageArray[0][i].getDrawable().getConstantState() != null && imageArray[1][i].getDrawable().getConstantState() != null && imageArray[2][i].getDrawable().getConstantState() != null) && imageArray[0][i].getDrawable().getConstantState().equals(imageArray[1][i].getDrawable().getConstantState()) && imageArray[1][i].getDrawable().getConstantState().equals(imageArray[2][i].getDrawable().getConstantState())) {
                    isFinished = true;
                    layoutArray[0][i].setBackgroundColor(Color.GREEN);
                    layoutArray[1][i].setBackgroundColor(Color.GREEN);
                    layoutArray[2][i].setBackgroundColor(Color.GREEN);
                    displayAlert(playerName);
                    return;
                }
            } catch (NullPointerException e) {
            }
        }

        //1st DIAGONAL CHECK
        try {
            if ((imageArray[0][0].getDrawable().getConstantState() != null && imageArray[1][1].getDrawable().getConstantState() != null && imageArray[2][2].getDrawable().getConstantState() != null) && imageArray[0][0].getDrawable().getConstantState().equals(imageArray[1][1].getDrawable().getConstantState()) && imageArray[1][1].getDrawable().getConstantState().equals(imageArray[2][2].getDrawable().getConstantState())) {
                isFinished = true;
                layoutArray[0][0].setBackgroundColor(Color.GREEN);
                layoutArray[1][1].setBackgroundColor(Color.GREEN);
                layoutArray[2][2].setBackgroundColor(Color.GREEN);
                displayAlert(playerName);
                return;
            }
        } catch (NullPointerException e) {
        }


        try {
        //2nd DIAGONAL CHECK
        if ((imageArray[0][2].getDrawable().getConstantState() != null && imageArray[1][1].getDrawable().getConstantState() != null && imageArray[2][0].getDrawable().getConstantState() != null) && imageArray[0][2].getDrawable().getConstantState().equals(imageArray[1][1].getDrawable().getConstantState()) && imageArray[1][1].getDrawable().getConstantState().equals(imageArray[2][0].getDrawable().getConstantState())) {
            isFinished = true;
            layoutArray[2][0].setBackgroundColor(Color.GREEN);
            layoutArray[1][1].setBackgroundColor(Color.GREEN);
            layoutArray[0][2].setBackgroundColor(Color.GREEN);
            displayAlert(playerName);
        }
    }catch (NullPointerException e){
        }

}

    private void displayAlert(String playerName) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        if (playerName.equals("draw")) {
            alertDialogBuilder.setIcon(R.drawable.sad);
            alertDialogBuilder.setTitle("TIE");
            alertDialogBuilder.setMessage("Try Once Again to Beat Each Other.." + "\nPress \"Start\" for New Game.." + "\nPress \"Restart\" to Start Again..");
        } else {
            alertDialogBuilder.setIcon(R.drawable.clap);
            alertDialogBuilder.setTitle("CONGRATULATIONS");
            alertDialogBuilder.setMessage(playerName.toUpperCase() + " WON.." + "\nPress \"Start\" for New Game.." + "\nPress \"Restart\" to Start Again..");
        }
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        alertDialogBuilder.setNegativeButton("RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putString("player1", player1.getText().toString());
                bundle.putString("player2", player2.getText().toString());

                MainFragment fragment = new MainFragment();
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).loadFragment(fragment);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    //TO CHECK IF ALL THE BOXES ARE FILLED OR NOT
    private void countTask() {
        if (count == 9) {
            player1.setBackgroundColor(Color.TRANSPARENT);
            player2.setBackgroundColor(Color.TRANSPARENT);
            player1Image.setVisibility(View.INVISIBLE);
            player2Image.setVisibility(View.INVISIBLE);
            displayAlert("draw");
        }
    }

    private void displayAlertForBack() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setIcon(R.drawable.warning);
        alertDialogBuilder.setTitle("Warning");
        alertDialogBuilder.setMessage("Your Game will End.." + "\nDo you want to Exit?");

        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
