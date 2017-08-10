package com.comtech.ali.mahan2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.VideoView;

import com.crashlytics.android.Crashlytics;

import com.crashlytics.android.ndk.CrashlyticsNdk;
import io.fabric.sdk.android.Fabric;


public class Splashscreen extends Activity {
    VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());


        videoView = (VideoView) findViewById(R.id.videoView);
        ImageView imageView=(ImageView)findViewById(R.id.sdsdsd);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });

        videoView.start();
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        //startActivity(new Intent(this, PageLogin.class));
        startActivity(new Intent(this, PageMoshaverin.class));
        finish();
    }
}
