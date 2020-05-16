package in.curioustools.a100daysofcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

public class ShareProgressActivity extends AppCompatActivity {
    private static final String TAG = ">$$$1114$$$>";
    TextInputEditText etMessage;
    ImageView ivGeneratedImage;
    TextView tvStampDaycount;
    FrameLayout frameFullImage;
    FloatingActionButton btFabChangeImage, btFabSharePost;

    private int[] picArr = new int[]{
            R.drawable.pic0, R.drawable.pic1, R.drawable.pic2,
            R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
            R.drawable.pic6, R.drawable.pic7, R.drawable.pic8,
            R.drawable.pic9
    };
    private int pic_i = (int) (System.currentTimeMillis() % 10);

    // TODO: 08-01-2019 add scrollable bars everywhere 
    // TODO: 08-01-2019 convert to constraint layout 
    // TODO: 08-01-2019 apply advance text features, like elevations, shading to all app


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_progress);

        initUI();
        initUIOnCicks();
        Log.e(TAG, "onCreate: successfully ran");

    }

    private void initUI() {
        etMessage = findViewById(R.id.ti_et_message);
        ivGeneratedImage = findViewById(R.id.iv_generated_image);
        tvStampDaycount = findViewById(R.id.tv_stamp_daycount);
        frameFullImage = findViewById(R.id.frameimage);

        btFabChangeImage = findViewById(R.id.bt_fab_change);
        btFabSharePost = findViewById(R.id.bt_fab_share2);

        Log.e(TAG, "initUI: successfully ran");

//         this could be wrong data. rather we should pass day number from current streak activity
        etMessage.requestFocus();

        SharedPreferences sp = getSharedPreferences(Statics.SP_NAME, MODE_PRIVATE);
        int currCount = sp.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0);

        etMessage.setText(MessageFormat.format("#100Daysofcode Completed 1 hour of coding on Day {0} ",currCount));
        tvStampDaycount.setText(MessageFormat.format("Day {0}", currCount));
        ivGeneratedImage.setImageDrawable(getDrawable(picArr[pic_i]));


    }

    private void initUIOnCicks() {
        btFabChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivGeneratedImage.setImageDrawable(getDrawable(picArr[(pic_i % 10)]));
                pic_i++;
            }
        });
        btFabSharePost.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                String message = "" + etMessage.getText();

                btFabChangeImage.setVisibility(View.GONE);
                Bitmap b = new ScreenShotter(frameFullImage).snap();
                btFabChangeImage.setVisibility(View.VISIBLE);

                ImageToUriTask task = new ImageToUriTask(b,ShareProgressActivity.this,message);
                task.execute();

            }
        });


        Log.e(TAG, "initUIOnCicks: successfully ran");

    }


    @SuppressLint("StaticFieldLeak")//using asynctask. cancel rotation now! todo
    private class ImageToUriTask extends AsyncTask<Void, Void, Void> {
        private Bitmap bitmapToSave;
        private Context context;
        private String message;
        private Uri savedImageUri;

        public ImageToUriTask(Bitmap bitmapToSave, Context context, String message) {
            this.bitmapToSave = bitmapToSave;
            this.context = context;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (this.bitmapToSave != null) {

                try {
                    File imagesFolder = new File(getCacheDir(), "images");
                    imagesFolder.mkdirs();
                    File file = new File(imagesFolder, "shared_image.png");

                    FileOutputStream stream = new FileOutputStream(file);
                    this.bitmapToSave.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.flush();
                    stream.close();
                    this.savedImageUri = FileProvider.getUriForFile(this.context,
                            "in.curioustools.fileprovider", file);

                } catch (IOException e) {
                    Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent(android.content.Intent.ACTION_SEND);


            intent.putExtra(Intent.EXTRA_TEXT, this.message);

            intent.putExtra(Intent.EXTRA_STREAM, this.savedImageUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("*/*");
            startActivity(intent);

        }




    }


}
