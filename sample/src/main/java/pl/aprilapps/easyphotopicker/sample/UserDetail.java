package pl.aprilapps.easyphotopicker.sample;

import android.net.Uri;

/**
 * Created by Dell on 17/12/2015.
 */
public class UserDetail {
    Uri Image1;
    Uri Image2;
    int Duration;
    String floatingText;
    String NameReason;
    Uri Music;
     String MusicName;
    int loopSlideShow=1;

    public String getMusicName() {
        return MusicName;
    }

    public void setMusicName(String musicName) {
        MusicName = musicName;
    }
    public int getLoopSlideShow() {
        return loopSlideShow;
    }

    public void setLoopSlideShow(int loopSlideShow) {
        this.loopSlideShow = loopSlideShow;
    }


    public Uri getMusic() {
        return Music;
    }

    public void setMusic(Uri music) {
        Music = music;
    }



    public Uri getImage2() {
        return Image2;
    }

    public void setImage2(Uri image2) {
        Image2 = image2;
    }

    public Uri getImage1() {
        return Image1;
    }

    public void setImage1(Uri image1) {
        Image1 = image1;
    }


    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getFloatingText() {
        return floatingText;
    }

    public void setFloatingText(String floatingText) {
        this.floatingText = floatingText;
    }

    public String getNameReason() {
        return NameReason;
    }

    public void setNameReason(String nameReason) {
        NameReason = nameReason;
    }


}
