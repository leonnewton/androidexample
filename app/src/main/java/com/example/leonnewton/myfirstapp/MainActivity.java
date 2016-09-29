package com.example.leonnewton.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

public class MainActivity extends Activity {


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFilename = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;

    private void onRecord(boolean start) {
        if(start){
            startRecording();

        }else {

            stopRecording();
        }


    }

    private void onplay(boolean start){
        if(start){
            startPlaying();

        }else{
            stopPlaying();
        }

    }






private void startRecording(){
    mRecorder = new MediaRecorder();
    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    mRecorder.setOutputFile(mFilename);
    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    try{
        mRecorder.prepare();
    }catch (IOException e){
        Log.e(LOG_TAG,"record prepare() failed");


    }
    mRecorder.start();
}

    private void stopRecording(){
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

    }

    private void startPlaying(){
        mPlayer = new MediaPlayer();

        try{
            mPlayer.setDataSource(mFilename);
            mPlayer.prepare();
            mPlayer.start();
        }catch(IOException e){

            Log.e(LOG_TAG,"play prepare() failed");
        }


    }


    private void stopPlaying(){
        mPlayer.release();
        mPlayer = null;

    }



    class RecordButton extends Button {

             boolean mStartRecording = true;
        View.OnClickListener clicker = new View.OnClickListener(){
                public void onClick(View v) {
                    onRecord(mStartRecording);

                    if(mStartRecording){
                        setText("press to stop recording");
                    }else {

                       setText("press to start recording");
                    }

                    mStartRecording =!mStartRecording;
                }
        };

        public RecordButton(Context ctx){
            super(ctx);
            setText("start recording");
            setOnClickListener(clicker);
        }

    }


    class PlayButton extends Button{
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onplay(mStartPlaying);
                if(mStartPlaying){

                    setText("press to stop play");

                }else {
                    setText("press to start play");
                }
                mStartPlaying=!mStartPlaying;
            }
        };

        public PlayButton(Context ctx){
            super(ctx);
            setText("start playing");
            setOnClickListener(clicker);
        }





    }



    public MainActivity(){
        mFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilename += "/test.3gp";
        System.out.println(mFilename);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout mainlayout = new LinearLayout(this);
        mRecordButton = new RecordButton(this);
        mainlayout.addView(mRecordButton, new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                        0));
        mPlayButton = new PlayButton(this);
        mainlayout.addView(mPlayButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));

        setContentView(mainlayout);
    }

    public void onPause(){
        super.onPause();
        if(mRecorder != null){
            mRecorder.release();
            mRecorder = null;
        }

        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;

        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
