package pl.aprilapps.easyphotopicker;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Dell on 4/01/2016.
 */
public class EasyMusic {
    private static String DEFAULT_FOLDER_NAME = "EasyMusic";

    public static File pickedMusic(Context context, Uri musicPath) throws IOException {
        InputStream pictureInputStream = context.getContentResolver().openInputStream(musicPath);
        //File directory = EasyImage.tempImageDirectory(context);
        File directory = tempImageDirectory(context);
        File musicFile = new File(directory, UUID.randomUUID().toString());
        musicFile.createNewFile();
        EasyImage.writeToFile(pictureInputStream, musicFile);
        return musicFile;
    }
    public static File tempImageDirectory(Context context) {
        File dir = new File(context.getApplicationContext().getCacheDir(), getFolderName(context));
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }
    private static String getFolderName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(getFolderNameKey(context), DEFAULT_FOLDER_NAME);
    }
    private static String getFolderNameKey(Context context) {
        return context.getPackageName() + ".folder_name";
    }
}
