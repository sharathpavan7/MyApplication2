package com.example.user.myapplication;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayContactsFrag extends Fragment {

    private static final String TAG = DisplayContactsFrag.class.getSimpleName();

    private Context ctx;

    private Cursor phone;
    private ArrayList<Contact> contact_list;

    private RecyclerView contacts;
    private ContactsAdapter adapter;


    public DisplayContactsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contact_list = new ArrayList<>();
        ctx = inflater.getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_contacts, container, false);
        contacts = (RecyclerView) view.findViewById(R.id.contacts);

        adapter = new ContactsAdapter(contact_list, ctx, inflater);
        contacts.setLayoutManager(new LinearLayoutManager(ctx));
        contacts.setAdapter(adapter);

        adapter.setListener(new ContactsAdapter.Listener() {
            @Override
            public void onClick() {
                Log.i(TAG, "Contacts item is clicked fragment is called");
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadContacts();
    }

    private void loadContacts() {
        phone = ctx.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        new LoadContacts().execute();
    }

    private class LoadContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            /*LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            adapter = new ContactsAdapter(contact_list, ctx, inflater);
            contacts.setLayoutManager(new LinearLayoutManager(ctx));
            contacts.setAdapter(adapter);*/

        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (phone != null) {

                Log.w(TAG, "contacts count"+phone.getCount());
                if (phone.getCount() == 0) {
                    Log.w(TAG, "No contacts in phone");
                    return null;
                }

                while (phone.moveToNext()) {
                    String name =  phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String mobile = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String photo = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    String update = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                    Log.i(TAG, "photo uri : "+update);

                    Contact contact = new Contact(name, mobile, photo);
                    contact_list.add(contact);
                }

            }
            return null;
        }
    }


}
