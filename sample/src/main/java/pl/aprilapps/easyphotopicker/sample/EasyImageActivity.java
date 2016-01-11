package pl.aprilapps.easyphotopicker.sample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyMusic;

public class EasyImageActivity extends AppCompatActivity {
    static final int REQ_IMAGE1 = 1;
    static final int REQ_IMAGE2 = 2;
    static final int RESULT_OK = 1;
    static final int REQ_MUSIC=3;
    static final int ENDSLIDESHOW=4;
    protected ImageView imageView1;
    protected ImageView imageView2;
    protected TextView musicNameTextView;
    protected EditText floatingText;
    protected SeekBar durationSeekbar;
    protected ListView userDetailListView;
    protected TextView durationTextView;
    protected TextView looptimesTextView;
    protected SeekBar looptimesSeekBar;
    UserDetail userDetail;
    Button saveBtn;
    Button slideshowBtn;
    Toolbar toolbar;
    Uri imageUri;
    Uri musicUri;
    //FloatingActionButton fab;
    ArrayList<UserDetail> userDetailList;
    UserSlideNameAdapter userSlideNameAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_data);
        findViewById();
        userDetailList=new ArrayList<UserDetail>();
        userDetail=new UserDetail();

       // userDetail.setLoopSlideShow(looptimeSeekBar.getValue());
        looptimesSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressvalue = 0;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressvalue = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                       looptimesTextView.setText( progressvalue + "/" + looptimesSeekBar.getMax());
                       userDetail.setLoopSlideShow(progressvalue);
                    }
                }
        );
       // numberPicker.setWrapSelectorWheel(false);
        durationSeekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressvalue = 0;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressvalue = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        durationTextView.setText(progressvalue + "/" + durationSeekbar.getMax()+" seconds");
                        //userDetail.setDuration(progressvalue);
                    }
                }
        );
       // ArrayAdapter<UserDetail> userDetailArrayAdapter=new ArrayAdapter<UserDetail>(this, ,userDetailList);
        //      setSupportActionBar(toolbar);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        SaveRetrieveDBTask  retrieveDB=new SaveRetrieveDBTask();
        retrieveDB.execute("2");
    }//oncreate
     class UserSlideNameAdapter extends ArrayAdapter<UserDetail>{
        private ArrayList<UserDetail> userDetails;
        private Context context;
       // public UserSlideNameAdapter(ArrayList<UserDetail> userDetails){
            public UserSlideNameAdapter(Context context, int textViewResourceId, ArrayList<UserDetail> userDetails) {
                super(context, textViewResourceId, userDetails);
                this.userDetails = userDetails;
                this.context=context;
            }
        public class Holder
        {
            TextView userName;
            TextView duration;
            TextView floatingText;
            ImageView listImageView1;
            ImageView   listImageView2;
            TextView musicText;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView;
            Holder holder=new Holder();
            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
          //  if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             //   v = inflater.inflate(R.layout.selectuserfromlist, null);
                 rowView = inflater.inflate(R.layout.selectuserfromlist, parent, false);

            //}
         final   UserDetail UserDetailpos =userDetails.get(position);

            if (UserDetailpos != null) {
                holder.userName =(TextView) rowView.findViewById(R.id.userslidenamelisttext);
                holder.duration=(TextView) rowView.findViewById(R.id.durationlisttext);
                holder.floatingText=(TextView) rowView.findViewById(R.id.floatingTextlisttext);
                holder.listImageView1=(ImageView)rowView.findViewById(R.id.listimageView1);
                holder.listImageView2=(ImageView)rowView.findViewById(R.id.listimageView2);
               holder.musicText=(TextView)rowView.findViewById(R.id.musiclistText);
               // System.out.println("before set for saving in list");
               if(UserDetailpos.getNameReason()!=null)
                holder.userName .setText(UserDetailpos.getNameReason());
                else {
                   holder.userName.setText("");
                   UserDetailpos.setNameReason("");
               }
                holder.duration.setText(UserDetailpos.getDuration() + "");
                if(UserDetailpos.getFloatingText()!=null)
                    holder.floatingText.setText(UserDetailpos.getFloatingText());
                else {
                    holder.floatingText.setText("");
                    UserDetailpos.setFloatingText("");
                }
                holder.listImageView1.setImageURI(UserDetailpos.getImage1());
                holder.listImageView2.setImageURI(UserDetailpos.getImage2());
                if(UserDetailpos.getMusicName()!=null)
                 holder.musicText.setText(UserDetailpos.getMusicName());
                else {
                    holder.musicText.setText("");
                    UserDetailpos.setMusicName("");
                }
                //System.out.println("all set after saving in list displayed ");
            }
           rowView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   setUI(UserDetailpos);

                   // TODO Auto-generated method stub
                   Toast.makeText(context, "You Clicked " +UserDetailpos.getNameReason()+" "+UserDetailpos.getDuration()+" "+UserDetailpos.getMusicName(), Toast.LENGTH_LONG).show();
               }
           });


            return rowView;
        }
    }

    public void saveButtonClicked(View view){
        setUserDetail();
        if(view.getId()==R.id.save_button) {
            userDetail.setNameReason("rups");
            UserDetail ud=new UserDetail();
            ud.setNameReason(userDetail.getNameReason());
            ud.setImage1(userDetail.getImage1());
            ud.setImage2(userDetail.getImage2());
            ud.setDuration(userDetail.getDuration());
            ud.setFloatingText(userDetail.getFloatingText());
            ud.setMusic(userDetail.getMusic());
            ud.setMusicName(userDetail.getMusicName());
            ud.setLoopSlideShow(userDetail.getLoopSlideShow());
            userDetailList.add(ud);
        }
        SaveRetrieveDBTask savetoDB =new SaveRetrieveDBTask();
        savetoDB.execute("1");
    //  userSlideNameAdapter =new UserSlideNameAdapter(this,R.layout.selectuserfromlist,userDetailList);
      //  ArrayAdapter<UserDetail> userSlideNameAdapter =new ArrayAdapter<UserDetail>(this,android.R.layout.simple_list_item_1,userDetailList);

      //  userDetailListView.setAdapter(userSlideNameAdapter);
        System.out.println("in savebutton");
    }

    class SaveRetrieveDBTask extends AsyncTask<String ,Integer,ArrayList<UserDetail>>{

        @Override
        protected void onPostExecute(ArrayList<UserDetail> userDetailList) {
            super.onPostExecute(userDetailList);
            userSlideNameAdapter =new UserSlideNameAdapter(getApplicationContext(),R.layout.selectuserfromlist,userDetailList);
            //  ArrayAdapter<UserDetail> userSlideNameAdapter =new ArrayAdapter<UserDetail>(this,android.R.layout.simple_list_item_1,userDetailList);

            userDetailListView.setAdapter(userSlideNameAdapter);
        }

        @Override
        protected ArrayList<UserDetail> doInBackground(String... params) {
            int task = Integer.parseInt(params[0]);
            //do here work of database
            System.out.println("doinBCKGND start");
            MyDBHandler myDBHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
            System.out.println("doinBCKGND 1");
            if (task == 1) {
                //  myDBHandler.open();
                myDBHandler.addUserDetail(userDetail);
                myDBHandler.close();
            }else{
                if (task == 2) {
                  //  myDBHandler.open();
                    userDetailList = myDBHandler.getUserDetail();
                    myDBHandler.close();
                } else
                    System.out.println("not doing db work++++++++++++++");
            }

            System.out.println("doinBCKGND ends");


            return userDetailList;
        }
    }
    public void slideshowButtonClicked(View view){
        setUserDetail();
        System.out.println("slideshowButtonClicked times"+userDetail.getLoopSlideShow());

        List<String> lst= new ArrayList<String>();
        if(userDetail.getImage1()==null || userDetail.getImage2()==null) {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }else {
                if (durationSeekbar.getProgress() == 0)
                    Toast.makeText(this, "Please Select duration", Toast.LENGTH_SHORT).show();
        }

        if(userDetail.getImage1()!=null && userDetail.getImage2()!=null && durationSeekbar.getProgress()!=0  ) {
            String image1 = userDetail.getImage1().toString();
            String image2 = userDetail.getImage2().toString();
            lst.add(image1);
            lst.add(image2);
            String[] imagearray = lst.toArray(new String[lst.size()]);
            if (view.getId() == R.id.slideshow_button) {
                Intent slideIntent = new Intent(this, ViewFlipperActivity.class);
                slideIntent.putExtra("listofimage", imagearray);
                slideIntent.putExtra("duration", userDetail.getDuration());
                String pathmusic="";
                if(userDetail.getMusic()!=null) {
                    pathmusic = userDetail.getMusic().toString();
                }
                slideIntent.putExtra("musicPath",pathmusic );
                slideIntent.putExtra("loop",userDetail.getLoopSlideShow());
               System.out.println("duration" + userDetail.getDuration() + "loop" + userDetail.getLoopSlideShow());
                startActivityForResult(slideIntent, ENDSLIDESHOW);//will change with EasyImageConfig.SLIDESHOW
            }
        }
    }

    private void findViewById(){
            durationTextView=(TextView)findViewById(R.id.durationText);
        looptimesTextView=(TextView)findViewById(R.id.looptimesText);
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView1=(ImageView) findViewById(R.id.imageView1);
        imageView2=(ImageView) findViewById(R.id.imageView2);
        musicNameTextView=(TextView)findViewById(R.id.musicNameText);
        floatingText=(EditText)findViewById(R.id.floatingText);
        durationSeekbar=(SeekBar) findViewById(R.id.durationSeekBar);
        userDetailListView=(ListView)findViewById(R.id.userDetailListView);
        saveBtn=(Button) findViewById(R.id.save_button);
        slideshowBtn=(Button) findViewById(R.id.slideshow_button);
        looptimesSeekBar=(SeekBar)findViewById(R.id.looptimesSeekBar);
       // fab = (FloatingActionButton) findViewById(R.id.fab);
    }
    public void  musicButtonClicked(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(
                intent, REQ_MUSIC);
    }
    public void imageSelectionClick(View view){
        setUserDetail();
        if(view.getId()==R.id.firstImage_button){
        Intent imageIntent=new Intent(this, MainActivity.class);

            startActivityForResult(imageIntent,1);//will change with EasyImageConfig.REQ_IMAGE
        }
        if(view.getId()==R.id.secondImage_button){
            Intent imageIntent=new Intent(this, MainActivity.class);
            startActivityForResult(imageIntent,2);//will change with EasyImageConfig.REQ_IMAGE

        }
    }
    private void setUserDetail(){
        userDetail.setDuration(durationSeekbar.getProgress());
       userDetail.setFloatingText(floatingText.getText().toString());
      userDetail.setLoopSlideShow(looptimesSeekBar.getProgress());
       //userDetail.setImage1(imageView1.get;
    }
    @Override
    protected void onResume(){
       super.onResume();

        setView();
//        UserSlideNameAdapter userSlideNameAdapter =new UserSlideNameAdapter(this,R.layout.selectuserfromlist,userDetailList);
//        userDetailListView.setAdapter(userSlideNameAdapter);

    }
    private void setUI(UserDetail userDetail){
        durationTextView.setText(userDetail.getDuration() + "/" + durationSeekbar.getMax()+" seconds");
        looptimesTextView.setText( userDetail.getLoopSlideShow() + "/" + looptimesSeekBar.getMax());
        musicNameTextView.setText(userDetail.getMusicName());
        imageView1.setImageURI(userDetail.getImage1());
        imageView2.setImageURI(userDetail.getImage2());
        floatingText.setText(userDetail.getFloatingText());
        durationSeekbar.setProgress(userDetail.getDuration());
        looptimesSeekBar.setProgress(userDetail.getLoopSlideShow());
    }
    private void setView(){
        durationTextView.setText(userDetail.getDuration() + "/" + durationSeekbar.getMax()+" seconds");
        looptimesTextView.setText(userDetail.getLoopSlideShow() + "/" + looptimesSeekBar.getMax());
        musicNameTextView.setText(userDetail.getMusicName());
        imageView1.setImageURI(userDetail.getImage1());
        imageView2.setImageURI(userDetail.getImage2());
        floatingText.setText(userDetail.getFloatingText());
        durationSeekbar.setProgress(userDetail.getDuration());
        looptimesSeekBar.setProgress(userDetail.getLoopSlideShow());
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        System.out.println("requestCode" + requestCode + "resultCode" + resultCode);
        if (requestCode ==REQ_IMAGE1 ||requestCode ==REQ_IMAGE2 ) {
            if (resultCode == RESULT_OK) {
                // An Image was picked.  Here we will just savwe it in an user object

                // Uri photoPath = data.getData();
                Bundle extras = data.getExtras();
                //File pictureFile = (File)data.getExtras().get("ImagePath");
                // System.out.println("pictureFile"+pictureFile);

               /* File value1 =(File) extras.get("ImagePath");

                if (value1 != null) {
                    System.out.println("value1"+value1);
                    System.out.println("value1 path"+value1.getPath());// do something with the data
                }*/
                 imageUri = Uri.parse(extras.getString("ImageURI"));
                System.out.println("imageUri" + imageUri.toString());
                //    EasyImage.ImageSource value2 =(EasyImage.ImageSource) data.getSerializableExtra("Source");
              /*  if (value2 != null) {
                    System.out.println("Source"+value2);// do something with the data
                }*/
                //System.out.println("photopath" + " photoPath.toString()" + "resultCode" + resultCode + "data" + data);
                //File auxFile = new File(value1.getPath()+".jpg");
                // onPhotoReturned(value1);
                //System.out.println("all set");
                if(requestCode ==REQ_IMAGE1) {
                    imageView1.setImageURI(imageUri);
                    userDetail.setImage1(imageUri);
                }
                if(requestCode ==REQ_IMAGE2) {
                    imageView2.setImageURI(imageUri);
                    userDetail.setImage2(imageUri);
                }

            }
        }
        if (requestCode ==REQ_MUSIC ) {
            if (resultCode ==-1) {
                // An Image was picked.  Here we will just savwe it in an user object

                // Uri photoPath = data.getData();
              //  Bundle extras = data.getExtras();
                //File pictureFile = (File)data.getExtras().get("ImagePath");
                // System.out.println("pictureFile"+pictureFile);

               /* File value1 =(File) extras.get("ImagePath");

                if (value1 != null) {
                    System.out.println("value1"+value1);
                    System.out.println("value1 path"+value1.getPath());// do something with the data
                }*/
                try {
                 musicUri = data.getData();
             //   musicUri = Uri.parse(extras.getString("MusicURI"));
                   /* audio
                    musicUri content://com.android.providers.media.documents/document/audio%3A1254
                    MusicFile /data/data/pl.aprilapps.easyphotopicker.sample/cache/EasyMusic/245fd454-a9b9-4053-a929-0c2a3511627f
                    music track
                    musicUri content://media/external/audio/media/1254
                    MusicFile /data/data/pl.aprilapps.easyphotopicker.sample/cache/EasyMusic/01f72023-75e7-4edc-b209-b5eaa40ae0d9

                    filemagr /audio/watsapp
                    musicUri file:///storage/sdcard/WhatsApp/Media/WhatsApp%20Audio/AUD-20160104-WA0002.aac
                    MusicFile /data/data/pl.aprilapps.easyphotopicker.sample/cache/EasyMusic/366affc1-4e11-467a-b511-38c784aae13d

                    filemagr /audio/others
                    musicUri file:///storage/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a
                    MusicFile /data/data/pl.aprilapps.easyphotopicker.sample/cache/EasyMusic/15e4ac8e-557e-4f5d-b9a6-46b88d535c77
*/
                    File f = new File("" + musicUri);


                    String smusicName = f.getName();
                    musicNameTextView.setText(smusicName);
                   userDetail.setMusicName(smusicName);
                    File musicFile = EasyMusic.pickedMusic(this, musicUri);//for saving in EasyImage folder in application
                    System.out.println("smusicname "+smusicName+" musicUri " + musicUri.toString()+" MusicFile "+musicFile.getPath());
                 musicUri = Uri.fromFile(musicFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    //callbacks.onImagePickerError(e, ImageSource.DOCUMENTS);
                }
                //    EasyImage.ImageSource value2 =(EasyImage.ImageSource) data.getSerializableExtra("Source");
              /*  if (value2 != null) {
                    System.out.println("Source"+value2);// do something with the data
                }*/
                //System.out.println("photopath" + " photoPath.toString()" + "resultCode" + resultCode + "data" + data);
                //File auxFile = new File(value1.getPath()+".jpg");
                // onPhotoReturned(value1);
                //System.out.println("all set");



                    userDetail.setMusic(musicUri);

            }
        }
        if(requestCode==ENDSLIDESHOW){

        }
    }


    /*private void onPhotoReturned(File photoFile) {
        Picasso.with(this)
                .load(photoFile)
                .fit()
                .centerCrop()
                .into(imageView1);
    }*/
}
