package murrayvarey.conkerplonk;

import android.app.Activity;
import android.content.Intent;
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if(resultCode == RESULT_OK && data != null)
            {
                if (requestCode == RESULT_LOAD_IMAGE)
                {
                    Uri imageUri = data.getData();
                    String column[] = { MediaStore.Images.Media.DATA};

                    // Get image data from uri
                    // Seems like this is how you get results -- you have to query the returning
                    // Intent. Interesting ...
                    Cursor cursor = getContentResolver().query(imageUri, column, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(column[0]);
                    _imageString = cursor.getString(columnIndex);
                    cursor.close();

                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(_imageString));
                }
            }
        }
        catch (Exception e)
        {


        }




    }


}
