package murrayvarey.conkerplonk;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private String _imageString;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoadPictureClick(View view)
    {
        Intent getFromGalleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getFromGalleryIntent, RESULT_LOAD_IMAGE);
    }

    final private int REQUEST_WRITE_EXTERNAL_PERMISSIONS = 100;

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if(resultCode == RESULT_OK && data != null)
            {
                if (requestCode == RESULT_LOAD_IMAGE)
                {
                   displaySelectedImageWrapper(data);
                }
            }
        }
        catch (Exception e)
        {
            boolean here = true;

        }
    }

    private void displaySelectedImageWrapper(Intent data) {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_PERMISSIONS);
            return;

        }
        displaySelectedImage(data);
    }

    private void displaySelectedImage(Intent data)
    {
        Uri imageUri = data.getData();
        String column[] = { MediaStore.Images.Media.DATA};

        // Get image data from uri
        // Seems like this is how you get results -- you have to query the returning
        // Intent. Interesting ...
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(imageUri, column, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(column[0]);
        _imageString = cursor.getString(columnIndex);
        cursor.close();

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(BitmapFactory.decodeFile(_imageString));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch(requestCode)
        {
            case REQUEST_WRITE_EXTERNAL_PERMISSIONS:

                break;
        }
    }


}
