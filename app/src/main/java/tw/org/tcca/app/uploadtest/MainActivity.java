package tw.org.tcca.app.uploadtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private File sdroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    123);
        }

        sdroot = Environment.getExternalStorageDirectory();

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
