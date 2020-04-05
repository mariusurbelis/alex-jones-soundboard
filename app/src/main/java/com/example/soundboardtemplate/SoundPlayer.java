package com.example.soundboardtemplate;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

import de.greenrobot.event.EventBus;

public class SoundPlayer {

    private MediaPlayer mPlayer;
    private Context mContext;

    private static final String TAG = "SoundPlayer";

    public SoundPlayer(Context context) {
        EventBus.getDefault().register(this);
        this.mContext = context.getApplicationContext();
    }

    public void onEvent(Sound sound) {
        playSound(sound);
    }

    public void playSound(Sound sound) {
        int resource = sound.getResourceId();
        if (mPlayer != null) {
            if (mPlayer.isPlaying())
                mPlayer.stop();
            mPlayer.reset();

            try {
                AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(resource);
                if (afd == null)
                    return;
                mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                mPlayer.prepare();
            } catch (IOException | IllegalArgumentException | SecurityException e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            mPlayer = MediaPlayer.create(mContext, resource);
        }
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                EventBus.getDefault().post("Done");
            }
        });
    }

    public void release() {
        EventBus.getDefault().unregister(this);
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
