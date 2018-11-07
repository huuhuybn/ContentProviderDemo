package vn.edu.poly.contentproviderdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContacts = findViewById(R.id.tvContacts);

        findViewById(R.id.btnLoadContacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            999);
                } else {
                    loadContacts();
                }
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void loadContacts() {

        // khai bao duong dan toi noi luu tru Danh Ba (Contact)

        List<String> contacts = new ArrayList<>();

        Cursor c1 = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);


        Log.e("SIZE", c1.getCount() + "");
        if (c1 != null && c1.getCount() > 0) {
            c1.moveToFirst();

            while (c1.isAfterLast() == false) {

                String id = c1.getString(c1.getColumnIndex(ContactsContract.Contacts._ID));

                String name = c1.getString(c1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                contacts.add(id + " - " + name);

                c1.moveToNext();
            }

            c1.close();

            String text = "";
            for (int i = 0; i < contacts.size(); i++) {
                text = text + " \n " + contacts.get(i);
            }

            tvContacts.setText(text);


        }


    }

}
