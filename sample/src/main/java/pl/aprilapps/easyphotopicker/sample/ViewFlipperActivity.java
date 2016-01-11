package pl.aprilapps.easyphotopicker.sample;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 it	0	1	0	1	0	1	0	1
 i	0	1	2	3	4	5	6	7
 im	1	2	1	2	1	2	1	2
 durdec	con	dec	con dec	con d   c

iteration/sec
 6	6	6	5	6	4	6	3	6	2	6	1	6
 5	5	5	4	5	3	5	2	5	1	5
 4	4	4	3	4	2	4	1	4
 3	3	3	2	3	1	3
 2	2	2	1	2
 1	1	1

dec is z for thread and for even number(i) iteration


 */


public class ViewFlipperActivity extends AppCompatActivity {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Animation.AnimationListener mAnimationListener;
    private Context mContext;
    Toast t;
    int looptime=0;////for ondestroy() all asynctask cancel
    AsyncTaskRunner runner;
    MediaPlayer mediaPlayer;
    AsyncTaskRunner[] a;
//    Handler handler;
  //  Runnable runble;

    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    // controlling animation
                    mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                    // controlling animation
                    mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
        int l=looptime;
       // System.out.println("ondestroy looptime"+l);
        while(l!=0) {
            l=l-1;
            System.out.println("ondestroy looptime" + l + "a[l].isCancelled()" + a[l].isCancelled());
           if(a[l].isCancelled()==false){
               System.out.println("ondestroy before cancel");
                a[l].cancel(true);}
         }
        //runner.cancel(true);
        finish();
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        System.out.println("oncreate");
        mContext = this;
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
        Bundle extras=getIntent().getExtras();
        String musicpath=extras.getString("musicPath");

        Uri musicUri = Uri.parse(musicpath);
    //   System.out.println("musicpath "+musicpath+" musicUri "+musicUri);

       try {
        //   mediaPlayer = new MediaPlayer();

           // mediaPlayer = MediaPlayer.create(this, R.raw.sample_song);
          // mediaPlayer.setDataSource(this, musicUri);
           mediaPlayer = MediaPlayer.create(this,musicUri);
        // mediaPlayer.create(this, musicUri);

          // mediaPlayer.prepare();
           mediaPlayer.setLooping(true);
           mediaPlayer.start();

       }catch (Exception e){
           e.printStackTrace();
       }


        String[] imagelist = extras.getStringArray("listofimage");
         final int duration=extras.getInt("duration");
         int loop=extras.getInt("loop");
        if(loop==0)
            loop=loop+1;
System.out.println("loop in create start"+loop);
      //  loop=loop+1;

        if (imagelist.length != 0) {
            for (int i = 0; i < imagelist.length; ++i) {
                // String stringUri = imagelist[i];
                Uri imageUri = Uri.parse(imagelist[i]);
                ImageView imageView = new ImageView(this);
                imageView.setImageURI(imageUri);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setAdjustViewBounds(true);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                params.gravity = Gravity.CENTER;

                imageView.setLayoutParams(params);

         /*android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:layout_gravity="center"
        android:adjustViewBounds="true"
        android:scaleType="centerCrop"*/


                mViewFlipper.addView(imageView);
            }//for image
//             handler=new Handler();
            looptime=loop;
            if (duration != 0) {
                 a = new AsyncTaskRunner[loop];
             //for ondestroy
                while(loop!=0) {
                    System.out.println("**********loop "+loop);

                    int l=loop-1;
                    a[l]=new AsyncTaskRunner();
                    System.out.println("**********loop "+loop+" a["+l+"] "+a[l]);
                  //  AsyncTaskRunner runner;
               // runner[loop] = new AsyncTaskRunner();
                // String sleepTime = time.getText().toString();
                //runner.execute(duration + "");
                    a[l].execute(duration + "",looptime+ "",l+ "");
                    loop--;
                }//while loop

           //     runner = new AsyncTaskRunner();
             //   runner.execute(duration + "");
     /*    class MyRunnable implements Runnable {
                int byteData;

        public MyRunnable(int byteData) {
            this.byteData = byteData;
        }

                public void run() { //

                        System.out.println("byteData" + byteData);
                        Runnable runble = new MyRunnable(byteData);// update the


                        System.out.println("in run while bytedata" + byteData);
                        byteData = byteData - 1000;
                        System.out.println("in run while 11");
                        handler.removeCallbacks(runble);
                        System.out.println("in run while2");
                        handler.postDelayed(runble, byteData);
                        System.out.println("in run while 33");
                        mViewFlipper.setFlipInterval(byteData);
                        mViewFlipper.setAutoStart(true);
                        //   mViewFlipper.setFlipInterval(duration);
                        mViewFlipper.startFlipping();
                        //mViewFlipper.showNext();
                        System.out.println("in run byteData " + byteData);

                }
            }//end of myRunnable
             int dur=duration*1000;
            runble=new MyRunnable(dur);
        System.out.println("after adding images to view flipper");

        //  runble  =new MyRunnable(dur);
          //Thread t= new Thread(runble);
            //  t.start();
         handler.removeCallbacks(runble);
            handler.postDelayed(runble, dur);
            System.out.println("before handler postdelayed dur" + dur);*/
                System.out.println("handler starts here end of if duration");
            }//if duration
        }//if listimage length
    /*        btnTakePhoto = new Button(this);
        btnTakePhoto.setBackgroundResource(android.R.drawable.ic_menu_camera);


        *//*Set container*//*
        mPreview = new Preview(this);
        setContentView(mPreview);

        *//*Set button params and add it to the view*//*
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addContentView(btnTakePhoto, buttonParams);*/
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("in play onclick");
                //sets auto flipping
                /*{
                    mViewFlipper.setAutoStart(true);
                    mViewFlipper.setFlipInterval(duration);
                    mViewFlipper.startFlipping();
                }*/
               if(duration!=0) {
                   AsyncTaskRunner runner = new AsyncTaskRunner();
                   // String sleepTime = time.getText().toString();

                   runner.execute(duration + "",looptime+ "",0+ "");
               }
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("in stop onclick");
                //stop auto flipping
                //  mViewFlipper.setAutoStart(false);
                mViewFlipper.stopFlipping();


            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("in back onclick");
                //sets auto flipping
                finish();
            }
        });
        //get all images from local device which by default stores in sdcard/DCIM
     /*   String   photoDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/.thumbnails/";


        //get the path of the images folder of our sdcard using the following statement.
// getting the path of the images folder in the sdcard
        // File sdcardPath = new File(Environment.getExternalStorageDirectory().getPath() + "/images");
        File sdcardPath = new File(photoDir);
        System.out.println("chk sdcardpath"+sdcardPath);
        //  if (sdcardPath.exists()& (sdcardPath.listFiles()!=null)) {
       if (sdcardPath.exists()) {

            System.out.println("in sdcardaa");
            setImagesToFlipper(mViewFlipper, sdcardPath);
            System.out.println("1");
            runnable = new Runnable() {

                public void run() {
                    System.out.println("2");
                    handler.postDelayed(runnable, 3000);
                    mViewFlipper.showNext();
                    System.out.println("3");
                }
            };
            handler = new Handler();
            handler.postDelayed(runnable, 500);

        }
        else
          //  Toast.makeText(this, "No folder with name images..", Toast.LENGTH_LONG).show();*/
        System.out.println("end of setting images");
        /* setImagesToFlipper() add ImageViews dynamically to the ViewFlipper.*/

        //animation listener
        mAnimationListener = new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                System.out.println(" mAnimationListener  onAnimationStart");
                //animation started event
                int childCount = mViewFlipper.getChildCount();
                //Toast.makeText(ViewFlipperActivity.this, "image count in start"+childCount, Toast.LENGTH_SHORT).show();
            }

            public void onAnimationRepeat(Animation animation) {
                System.out.println(" mAnimationListener  onAnimationRepeat");
            }

            public void onAnimationEnd(Animation animation) {
                //TODO animation stopped event
                System.out.println(" mAnimationListener  onAnimationEnd");
                int displayedChild = mViewFlipper.getDisplayedChild();
                int childCount = mViewFlipper.getChildCount();
             //  Toast.makeText(ViewFlipperActivity.this, "displayedChild/image count"+displayedChild+"/"+childCount, Toast.LENGTH_SHORT).show();
                if (displayedChild == childCount - 1) {
                    mViewFlipper.stopFlipping();
                }            }
        };

        System.out.println("end of create");

    }
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp="";
        private int duration;
        //private boolean running = true;
/*
        @Override
        protected void onCancelled() {
            running = false;
        }*/
        @Override
        protected String doInBackground(String... params) {

            try {
//while (!isCancelled()) {
    // Do your long operations here and return the result
    int dur = Integer.parseInt(params[0]);
                int loopin=Integer.parseInt(params[1]);
                int currentasync=Integer.parseInt(params[2]);
    duration = dur * 1000;
    resp = dur * 1000 + "";

    int z = dur;//for setting thread sleep time to decrease  and decrease loop for first photo and send to progress update too

    for (int i = 0; i < 2 * dur; i++) {
        System.out.println("in dobckgnd before going to sleep in do bckgroundi"+i+" dur "+dur +" z "+z+" loopin "+loopin+" currentasync "+currentasync);
        int it = i % 2;
        System.out.println("in dobckgnd it value "+it);
        if (it == 0) {
            resp = z * 1000 + "&" + i +"&" + currentasync;
            Thread.sleep(z * 1000);
            z = z - 1;
        } else {
            resp = dur * 1000 + "&" + i+"&" + currentasync;
            Thread.sleep(duration);
        }

        publishProgress(resp + "");

    }//for
    resp = "";
//}
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            //System.out.println("in  progressupdate text "+text[0]+"check out");
           try {
               String[] parts = text[0].split("&");// resp = dur * 1000 + "&" + i;
               String part1 = parts[0]; //time or length of odd or even if o or even then decreasing time else full time
               String part2 = parts[1]; // i
               String part3=parts[2];//asyncarraynumber
               System.out.println("part1 "+part1+" part2 "+part2+" part3 "+part3);
               int time = 0;
               String test = part1.trim();
               if (test != "") {
                   time = Integer.parseInt(part1);
                   //  System.out.println("in  progressupdate time"+time +"check out");
               }
               if (time != 0) {

                   int displayedChild = mViewFlipper.getDisplayedChild();
                   int childCount = mViewFlipper.getChildCount();
                   System.out.println("++++++++++++++ on progressupdate Displayedchild " + displayedChild + "part[1]or part2/i " + part2 + " time or duration to flip or part[0] " + time);
                   int numtype = Integer.parseInt(part2);
                   int it = numtype % 2;

                   if (displayedChild == 0 && it == 0) {
                       mViewFlipper.setFlipInterval(time);
                       System.out.println(" 0000000000000th child on progressupdate displayed child 0 or first child time " + time + " i " + part2 + " it " + it);
                       // Toast t=  Toast.makeText(ViewFlipperActivity.this, "displayedChild/image count"+displayedChild+"/"+childCount+"response for time"+time+"sec",Toast.LENGTH_SHORT );
                       //        t.show();
                       mViewFlipper.showNext();
                   } else if (displayedChild == 1 && it == 1) {
                       mViewFlipper.setFlipInterval(duration);
                       // Toast t=Toast.makeText(ViewFlipperActivity.this, "displayedChild/image count"+displayedChild+"/"+childCount+"response for time"+duration+"sec",Toast.LENGTH_SHORT );
                       // t.show();
                       System.out.println("111111111111 child in  progressupdate displayed child others duration " + duration + " i " + part2 + " it " + it);
                       if (numtype == ((2 * duration / 1000) - 1)) {
                           System.out.println("loop last its last and no more flipping");
                           if(looptime>1){
                               System.out.println("loop last its last and no more flipping check out in loop");
                               if(Integer.parseInt(part3)>0){
                                   mViewFlipper.showPrevious();
                                   System.out.println("loop last its last and no more flipping to previous");
                               }
                           }
                           // while (isCancelled()==false)
                           //   Thread.currentThread().

                       } else {
                           //System.out.println("loop not ended so its previos" );
                           mViewFlipper.showPrevious();
                       }
                   } else {

                       System.out.println("there are only 2 images 2 childs its not possible to come here  Displayedchild " + displayedChild + " ************ time " + time + "*********** i" + 1);
                      // mViewFlipper.showPrevious();
                   }

               } else {//else time==0
                   System.out.println("in run on progressupdate else time its not possible to run slideshow for zero time " + time + " whyyyyyyyyyyyyyyyyyyy");
               }
           }
       catch (Exception e) {
            e.printStackTrace();

        }

        }//onprogress()
        /*
        * (non-Javadoc)
        *
        * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
        */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            //   finalResult.setText(result);
            //int time=0;
            String test=result.trim();
            if ( test=="" ) {
                //result="0";
                //System.out.println("in run on postexec test " + test+"test");
                //time = Integer.parseInt(result);
                // if(time==0)
                mViewFlipper.stopFlipping();
                //  System.out.println("in run on postexece  " );
            }


            //System.out.println("in run on postexec time " );

        }//onpostexecute

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

    }//class aysnctask


}
