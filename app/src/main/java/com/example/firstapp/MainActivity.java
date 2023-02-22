package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.clevertap.android.sdk.CleverTapAPI;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import com.clevertap.android.sdk.CTInboxActivity;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.CleverTapInstanceConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import android.util.Log;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CTInboxListener, DisplayUnitListener{
    public Button btn;
    public Button btn2;
    public Button btn3;
    public Button btn4;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        CleverTapAPI.createNotificationChannel(getApplicationContext(),"P01","First Push","This is my first push channel try",NotificationManager.IMPORTANCE_MAX,true);
        Bundle extras = null;
        CleverTapAPI.getDefaultInstance(this).pushNotificationViewedEvent(null);

        if (clevertapDefaultInstance != null) {
            //Set the Notification Inbox Listener
            clevertapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
            clevertapDefaultInstance.initializeInbox();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);     //Set Log level to VERBOSE
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);
        CleverTapAPI.getDefaultInstance(this).pushEvent("Native Display pc");
        clevertapDefaultInstance.pushEvent("OnePlus Event");
        btn=findViewById(R.id.p);
        btn2=findViewById(R.id.p2);
        btn3=findViewById(R.id.p3);
        btn4=findViewById(R.id.p4);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // each of the below mentioned fields are optional
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", "P C");    // String
                profileUpdate.put("Identity", 1210);      // String or number
                profileUpdate.put("Email", "pc@gmail.com"); // Email address of the user
                profileUpdate.put("Phone", "7418529637");   // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "F");             // Can be either M or F
                profileUpdate.put("DOB", new Date());         // Date of Birth. Set the Date object to the appropriate value first
                // optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", true);        // Disable email notifications
                profileUpdate.put("MSG-push", true);          // Enable push notifications
                profileUpdate.put("MSG-sms", true);          // Disable SMS notifications
                profileUpdate.put("MSG-whatsapp", true);      // Enable WhatsApp notifications
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("Tote Bag");
                stuff.add("Jordans");
                profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings
                String[] otherStuff = {"Denims","Body Mist"};
                profileUpdate.put("MyStuff", otherStuff);                   //String Array

                clevertapDefaultInstance.pushProfile(profileUpdate);
            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Casio Chronograph Watch");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
                prodViewedAction.put("Date", new Date());

                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                chargeDetails.put("Amount", 300);
                chargeDetails.put("Payment Mode", "Credit card");
                chargeDetails.put("Charged ID", 24052013);

                HashMap<String, Object> item1 = new HashMap<String, Object>();
                item1.put("Product category", "books");
                item1.put("Book name", "The Millionaire next door");
                item1.put("Quantity", 1);

                HashMap<String, Object> item2 = new HashMap<String, Object>();
                item2.put("Product category", "books");
                item2.put("Book name", "Achieving inner zen");
                item2.put("Quantity", 1);

                HashMap<String, Object> item3 = new HashMap<String, Object>();
                item3.put("Product category", "books");
                item3.put("Book name", "Chuck it, let's do it");
                item3.put("Quantity", 5);

                ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                items.add(item1);
                items.add(item2);
                items.add(item3);

                CleverTapAPI cleverTap;
                clevertapDefaultInstance.pushChargedEvent(chargeDetails, items);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertapDefaultInstance.showAppInbox();//Opens Activity with default style configs
            }
        });
    }

    @Override
    public void inboxDidInitialize() {

    }

    @Override
    public void inboxMessagesDidUpdate() {

    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        for (int i = 0; i <units.size() ; i++) {
            CleverTapDisplayUnit unit = units.get(i);
            prepareDisplayView(unit);
            Log.d("Clevertap", "Native display" + units);
        }
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {
        //TODO: code for UI
    }
}