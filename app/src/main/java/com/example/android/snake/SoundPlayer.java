//package com.example.android.snake;
//
//import android.content.Context;
//import android.media.AudioAttributes;
//import android.media.AudioManager;
//import android.media.SoundPool;
//import android.os.Build;
//
//public class SoundPlayer {
//
//    private static SoundPool soundPool;
//    private static int hitSound;
//    private static int movingSound;
//    private static int eatSound;
//
//    /** Initializing the variables for soundPlayer - where does this go*/
//    private SoundPlayer sound;
//    private int hitSound, eatSound, movingSound;
//
//    public SoundPlayer(SnakeView context) {
//
//        /**Static soundPool.Builder works on API 21 or higher so
//         * the issue with the version is taken care of with an IF statement,
//         Lollipop is constant for 21. Define multiple values with AudioAttributes*/
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();
//
//            // MaxStreams is how many sounds can be played
//            soundPool = new SoundPool.Builder()
//                    .setMaxStreams(3)
//                    .setAudioAttributes(audioAttributes)
//                    .build();
//        } else {
//            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
//        }
//
//        eatSound = soundPool.load(this, R.raw.eatSound, 1);
//        movingSound = soundPool.load(this, R.raw.movingSound, 1);
//        hitSound = soundPool.load(this, R.raw.hitSound, 1);
//
//        public void playHitSound(){
//            soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
//        }
//
//        public void playMovingSound() {
//            soundPool.play(movingSound, 1.0f, 1.0f, 1, 0, 1.0f)
//        }
//
//        public void playEatSound(){
//            soundPool.play(eatSound, 1.0f, 1.0f, 1, 0, 1.0f);
//        }
//
//
//    }
//
//
//
//}
