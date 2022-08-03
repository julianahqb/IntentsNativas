package com.example.intentsnativas;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lista;
    private static final int REQ_CODE = 1000;

    String[] permissoes = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.lista);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.acoes, android.R.layout.simple_list_item_1);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        verificarPermissoes();
    }

    private void verificarPermissoes() {
        List<String> permissoesRequeridas = new ArrayList<>();

        for(String p: permissoes){
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                permissoesRequeridas.add(p);
            }
        }

        if(!permissoesRequeridas.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissoesRequeridas.toArray(new String[permissoesRequeridas.size()]), REQ_CODE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Uri uri;
        Intent intent;
        switch (i){
            case 0:
                uri = Uri.parse("tel:12345678");
                intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                break;
            case 1:
                uri = Uri.parse("tel:12345678");
                intent = new Intent(Intent.ACTION_CALL, uri);
                startActivity(intent);
                break;
            case 2:
                uri = Uri.parse("sms:12345678");
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "Ola");
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhar");
                intent.putExtra(Intent.EXTRA_TEXT, "Mensagem");
                startActivity(intent);
                break;
            case 4:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "O acesso à camera não foi autorizado", Toast.LENGTH_SHORT).show();
                }
                break;
            case 5:
                intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(intent);
                break;
            case 6:
                uri = Uri.parse("content://com.android.contacts/contacts");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case 7:
                uri = Uri.parse("content://com.android.contacts/contacts/1");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case 8:
                uri = Uri.parse("content://com.android.contacts/contacts");
                intent = new Intent(Intent.ACTION_PICK, uri);
                startActivity(intent);
                break;
            case 9:
                intent = new Intent("com.example.professor.TESTE");
                startActivity(intent);
                break;
            case 10:
                uri = Uri.parse("teste://tads/android");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case 11:
                uri = Uri.parse("http://www.foz.ifpr.edu.br");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            for(int g: grantResults){
                if (g != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Algumas funcionalidades podem não funcionar", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }
}