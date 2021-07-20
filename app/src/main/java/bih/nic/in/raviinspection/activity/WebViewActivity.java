package bih.nic.in.raviinspection.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import bih.nic.in.raviinspection.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;
    String idproof = "",resedential = "",bankpassbook = "",bhu_swamitwa ="",swa_gosna,base64="";
    private static final String googleDocsUrl = "http://docs.google.com/viewer?url=";
    Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        try {
            base64 = getIntent().getStringExtra("base64");
//            String filepath1 = getIntent().getStringExtra("filepath");
//            filepath = Uri.parse(filepath1);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            resedential = getIntent().getStringExtra("resedential");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            bankpassbook = getIntent().getStringExtra("bankpassbook");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            bhu_swamitwa = getIntent().getStringExtra("bhu_swamitwa");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            swa_gosna = getIntent().getStringExtra("swa_gosna");
        }catch (Exception e){
            e.printStackTrace();
        }


        webview = (WebView)findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);

        WebSettings settings = webview.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
      //  settings.setDefaultTextEncodingName("utf-8");
//        String filename ="https://www3.nd.edu/~cpoellab/teaching/cse40816/android_tutorial.pdf";
//        if(!(idproof.equalsIgnoreCase("N") )){
//                webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + idproof);
//        }else if(!(resedential.equalsIgnoreCase("N"))) {
//            webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + resedential);
//        }else if(!(bankpassbook.equalsIgnoreCase("N"))) {
//            webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + bankpassbook);
//        }else if(!(bhu_swamitwa.equalsIgnoreCase("N"))) {
//            webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + bhu_swamitwa);
//        }else if(!(swa_gosna.equalsIgnoreCase("N")))
//        {
////            String extStorageDirectory = Environment.getExternalStorageDirectory()
////                    .toString();
////            File folder = new File(extStorageDirectory, "pdf");
////            folder.mkdir();
////            File file = new File(folder, "Read.pdf");
////            try {
////                file.createNewFile();
////            } catch (IOException e1) {
////                e1.printStackTrace();
////            }
////            Downloader.DownloadFile(swa_gosna, file);
////
////            showPdf();
//
//            webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + swa_gosna);
//
//        }
//        else {
//            Toast.makeText(WebViewActivity.this, "कोई रिकॉर्ड नही है!", Toast.LENGTH_SHORT).show();
//
//        }

        Log.d("path", "" + filepath);
        //webview.loadUrl(filepath.toString());
       // webview.loadUrl(" file:///storage/emulated/0/Documents/Attachments-7298.pdf");



        String content =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
                        "<html><head>"+
                        "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
                        "</head><body>";

        content += base64 + "</body></html>";

      //  WebView WebView1 = (WebView) findViewById(R.id.webView1);
        webview.loadData(content, "text/html; charset=utf-8", "UTF-8");
      //  webview.loadData(base64, "text/html", "UTF-8");
       // webview.loadData(base64, "text/html; charset=utf-8", "base64");
        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);

            }
        });


//        WebView webview = (WebView) findViewById(R.id.webview);
//        webview.getSettings().setJavaScriptEnabled(true);
//        String pdf = "http://10.133.20.159/TestService/FSY/Uploads/JBZREET8/1819/HELP/209000000004_SA_HELP.Pdf";
//        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);


    }


}
