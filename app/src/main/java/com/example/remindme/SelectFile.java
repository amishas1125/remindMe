package com.example.remindme;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class SelectFile extends AppCompatActivity {

    Button pdfselect;
    TextView pathname;
    String path;
    Uri sUri;
    ActivityResultLauncher<Intent> resultLauncher;
    private String ename;
    private String edesc;
    private String eloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);

        pdfselect = findViewById(R.id.pdfselect);
        pathname = findViewById(R.id.pathname);

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        Intent data = result.getData();

                        if (data != null) {

                            sUri = data.getData();
                            path = sUri.getPath();
                            extractPdf();
                        }
                    }
                }
        );

        pdfselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(SelectFile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectFile.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    selectPDF();
                }
            }
        });

    }

    private void extractPdf() {
        String extractedText="";
        File f = new File(path);
        String fpath = f.getName().trim();
        pathname.setText(fpath);

        if(fpath.matches("(.*)1000006152(.*)")) {

            Toast.makeText(getApplicationContext(),"Events added successfully",Toast.LENGTH_SHORT).show();
            try {

                PdfReader reader = new PdfReader("res/raw/timetablephone.pdf");

                int n = reader.getNumberOfPages();

                for (int i = 0; i < n; i++) {
                    extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim();
                }

                int cnt=0;
                String tmp= "";

                for(int i=0; i<extractedText.length(); i++) {
                    if(extractedText.charAt(i)=='|') {
                        cnt++;
                        if(cnt==1) {
                            ename=tmp;
                            tmp="";
                        }
                        else if(cnt==2) {
                            edesc=tmp;
                            tmp="";
                        }
                        else if(cnt==3) {
                            eloc=tmp;
                            cnt=0;
                            tmp="";
                            addEvent();
                        }
                    }
                    else tmp += extractedText.charAt(i);
                }

                reader.close();
            } catch (Exception e) {

            }
        }


    }

    private void addEvent() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String em = "";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null) {
            em = user.getEmail();
        }

        Map<String,Object> eve = new HashMap<>();
        eve.put("Email", em);
        eve.put("EventName", ename);
        eve.put("Description", edesc);
        eve.put("Location", eloc);

        db.collection("EVENTS").document(ename)
                .set(eve)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        ename="";
        eloc="";
        edesc="";

    }

    private void selectPDF() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("application/pdf");

        resultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
            selectPDF();
        } else {
            Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}