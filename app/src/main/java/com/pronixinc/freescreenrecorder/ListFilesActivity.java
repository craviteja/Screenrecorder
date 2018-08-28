package com.pronixinc.freescreenrecorder;

import android.content.Intent;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pronixinc.freescreenrecorder.adapter.ListoffilesAdapter;

import java.io.File;
import java.util.ArrayList;

public class ListFilesActivity extends AppCompatActivity {
    private ListView listView;
    String directory = Environment.getExternalStorageDirectory() + File.separator + "Recordings";
    private ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);
        listView = (ListView)findViewById(R.id.listoffiles);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFileNames();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void getFileNames() {
        File folder = new File(directory);
        for (File f : folder.listFiles()) {
            if (f.isFile()){
                String name = f.getName();
                if (name.indexOf(".") > 0)
                    name = name.substring(0, name.lastIndexOf("."));
                arrayList.add(name);
            }
            // Do your stuff
        }
        ListoffilesAdapter listoffilesAdapter = new ListoffilesAdapter(ListFilesActivity.this,arrayList);
        listView.setAdapter(listoffilesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = ((TextView)view.findViewById(R.id.filename)).getText().toString();
                Intent intent = new Intent(ListFilesActivity.this,VideoPlayerActivity.class);
                intent.putExtra("url",fileName);
                startActivity(intent);
            }
        });
        listoffilesAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(ListFilesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
