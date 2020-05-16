package in.curioustools.a100daysofcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;
import android.view.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class ScreenShotter {
    private final View view;

    //Create snapshots based on the view and its children.
    public ScreenShotter(View root) {
        this.view = root;
    }

    //Create snapshot handler that captures the root of the whole activity.
    public ScreenShotter(Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        this.view = contentView.getRootView();
    }

    //Take a snapshot of the view.
    public Bitmap snap() {
        Bitmap bitmap = Bitmap.createBitmap(this.view.getWidth(), this.view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}

