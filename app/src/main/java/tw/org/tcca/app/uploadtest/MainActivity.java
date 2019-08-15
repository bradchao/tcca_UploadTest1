package tw.org.tcca.app.uploadtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private File sdroot;
    private WebView webView;
    private WebChromeClient webChromeClient = new WebChromeClient(){

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

            Log.v("brad", "OK");

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Uri photoUri = FileProvider.getUriForFile(MainActivity.this, "", new File(sdroot,"filename"));
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, 124);


            return true; //super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    123);
        }

        sdroot = Environment.getExternalStorageDirectory();

        webView = findViewById(R.id.webview);
        initWebView();
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);

        webView.setWebChromeClient(webChromeClient);

        webView.loadUrl("https://www.bradchao.com/upload.php");

    }




    public void test1(View view) {
        new Thread(){
            @Override
            public void run() {
                try {
                    MultipartUtility mu = new MultipartUtility(
                            "https://www.bradchao.com/doUpload.php",
                            "", "UTF-8");
                    mu.addFilePart("upload", new File(sdroot, "Download/gamer.pdf"));
                    List<String> result = mu.finish();
                    Log.v("brad", "OK");
                    for (String line : result){
                        Log.v("brad", line);
                    }
                }catch (Exception e){
                    Log.v("brad", e.toString());
                }
            }
        }.start();
    }
}
