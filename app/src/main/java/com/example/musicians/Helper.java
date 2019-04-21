package com.example.musicians;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Helper extends AppCompatActivity {



    public static void showCustomDialog(String text,final Context c, final Class<? extends Activity> ActivityToOpen) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = (ViewGroup) ((Activity) c).findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(c).inflate(R.layout.my_dialog, viewGroup, false);



        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final  AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView editText = (TextView) dialogView.findViewById(R.id.dialog_text);
        editText.setText(text);

        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                alertDialog.dismiss();
                timer2.cancel(); //this will cancel the timer of the system
                Intent myIntent = new Intent(((Activity) c).getBaseContext(), ActivityToOpen);
                c.startActivity(myIntent);
            }
        }, 2000);


    }
}

