package com.example.user.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private static final String TAG = ContactsAdapter.class.getSimpleName();

    private List<Contact> contactList;
    private Context ctx;
    private LayoutInflater inflater;
    private Listener listener;

    interface Listener {
        void onClick();
    }

    public ContactsAdapter(List<Contact> list, Context ctx, LayoutInflater inflater) {
        this.contactList = list;
        this.ctx = ctx;
        this.inflater = inflater;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.contact_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Contact contact = contactList.get(i);

        viewHolder.name.setText(contact.getContactName());
        viewHolder.mobile.setText(contact.getContactMobile());
        Uri uri = Uri.parse(contact.getContactImage());
        viewHolder.photo.setImageBitmap(getContactBitmapFromURI(ctx, uri));

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Contacts item is clicked");
                listener.onClick();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    private Bitmap getContactBitmapFromURI(Context context, Uri uri) {

        InputStream input = null;
        try {
            input = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (input == null) {
            return null;
        }

        //return BitmapFactory.decodeStream(input);
        return getCroppedBitmap(BitmapFactory.decodeStream(input));
    }

    private Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView name, mobile;
        private ImageView photo;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            name = (TextView) view.findViewById(R.id.name);
            mobile = (TextView) view.findViewById(R.id.mobile);
            photo = (ImageView) view.findViewById(R.id.photo);

        }


    }
}
