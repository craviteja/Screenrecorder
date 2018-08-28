package com.pronixinc.freescreenrecorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pronixinc.freescreenrecorder.R;

import java.io.File;
import java.util.ArrayList;

public class ListoffilesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> arrayList;
    String directory = Environment.getExternalStorageDirectory() + File.separator + "Recordings";
    public ListoffilesAdapter(Context context,ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.single_file,null);
        TextView textView = (TextView)view.findViewById(R.id.filename);
        ImageView imageView = (ImageView)view.findViewById(R.id.deleteFile);
        ImageView share = (ImageView)view.findViewById(R.id.shareFile);
        textView.setText(arrayList.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    File folder = new File(directory+"/"+arrayList.get(position)+".mp4");
                    if(folder.exists())
                    {
                        folder.delete();
                        arrayList.remove(position);
                        notifyDataSetChanged();
                    }
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File folder = new File(directory+"/"+arrayList.get(position)+".mp4");
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("video/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" +folder));
                context.startActivity(Intent.createChooser(share, "Share File"));
            }
        });
        return view;
    }
}
