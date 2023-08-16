package com.QuizYourPerception;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class MainActivity extends AppCompatActivity {

    private CardView kirmizi, lacivert, sari, mor, siyah, yesil, turuncu, pembe, turkuaz;
    private TextView textViewRenk, textViewPuan, textViewSaniye;

    public int sayi = 0;
    private int atananEleman,atananEleman2;
    private String atananYazi;
    private timer t;
    private ProgressBar progressBar;
    private int i = 0;
    Dialog dialog;
    private RewardedAd mrewardedAd;
    KonfettiView konfettiView;
    Dialog dialogBaslat;
    private boolean mSingleUse = false;

    @Override
    public void onBackPressed() {
        t.cancel();
        super.onBackPressed();
        t.ses.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        t.cancel();
        finish();
        t.ses.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kirmizi = findViewById(R.id.kirmizi);
        lacivert = findViewById(R.id.lacivert);
        sari = findViewById(R.id.sari);
        mor = findViewById(R.id.mor);
        siyah = findViewById(R.id.siyah);
        yesil = findViewById(R.id.yesil);
        turuncu = findViewById(R.id.turuncu);
        pembe = findViewById(R.id.pembe);
        turkuaz = findViewById(R.id.turkuaz);
        textViewRenk = findViewById(R.id.textViewRenk);
        textViewPuan = findViewById(R.id.textViewPuan);
        textViewSaniye = findViewById(R.id.textViewSaniye);
        progressBar = findViewById(R.id.progressBar);
        konfettiView = findViewById(R.id.konfettiView);


        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "gre2432");

        sequence.setConfig(config);

        sequence.addSequenceItem(textViewPuan,
                getApplicationContext().getString(R.string.puanim_showcase), getApplicationContext().getString(R.string.gecmek_icin_tiklayin_showcase));

        sequence.addSequenceItem(progressBar,
                getApplicationContext().getString(R.string.kalan_sure_showcase), getApplicationContext().getString(R.string.gecmek_icin_tiklayin_showcase));

        sequence.addSequenceItem(textViewRenk,
                getApplicationContext().getString(R.string.random_renk), getApplicationContext().getString(R.string.gecmek_icin_tiklayin_showcase));

        sequence.start();

        sequence.setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
            @Override
            public void onDismiss(MaterialShowcaseView itemView, int position) {
                sequence.setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
                    @Override
                    public void onDismiss(MaterialShowcaseView itemView, int position) {
                        sequence.setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
                            @Override
                            public void onDismiss(MaterialShowcaseView itemView, int position) {
                                baslatmaDialogu();
                            }
                        });
                    }
                });
            }
        });



        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("your_int_key", sayi);
        editor.commit();

        dialog = new Dialog(this);
        dialogBaslat = new Dialog(this);

        if (sequence.hasFired() == true){
            baslatmaDialogu();
        }


        //baslatmaDialogu();

        //ProgressBarBaslat();

        t = new timer(30000,1000);

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        AtamaYap sayiAta = new AtamaYap();
        atananEleman = sayiAta.renkleriAta();
        atananEleman2 = sayiAta.renkleriAta();
        atananYazi = getResources().getString(sayiAta.denemeYeni());


        textViewRenk.setTextColor(atananEleman);
        textViewRenk.setText(""+ atananYazi);





        kirmizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[0]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();

                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });

        lacivert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[1]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi - 10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });


        sari.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[2]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });

        mor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[3]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });

        siyah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[4]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });

        yesil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[5]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });

        turuncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[6]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });

        pembe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[7]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });

        turkuaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atananEleman == sayiAta.items[8]){
                    sayi = sayi+5;
                    textViewPuan.setText("" + sayi);
                    konfetiAt();
                }
                else {
                    sayi = sayi-10;
                    textViewPuan.setText("" + sayi);
                    vibe.vibrate(150);
                    if (sayi<0){
                        textViewPuan.setText(""+0);
                        sayi = 0;
                    }
                }
                atananEleman = sayiAta.renkleriAta();
                atananYazi = getResources().getString(sayiAta.denemeYeni());
                textViewRenk.setTextColor(atananEleman);
                textViewRenk.setText(""+ atananYazi);
                textViewSaniye.setTextColor(atananEleman);
            }
        });
    }



    class timer extends CountDownTimer {

        final MediaPlayer ses = MediaPlayer.create(MainActivity.this, R.raw.gerisayim);
        public timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            textViewSaniye.setText("" + l/1000);
            if (l<5000){
                ses.start();
            }
        }

        @Override
        public void onFinish() {
            textViewSaniye.setTextSize(15);
            textViewSaniye.setText("Süre Bitti");
            final MediaPlayer ses = MediaPlayer.create(MainActivity.this, R.raw.gerisayim);
            ses.stop();
            dialogGoster();
        }
    }

    private void baslatmaDialogu(){

        dialogBaslat.setContentView(R.layout.baslatma_dialogu);
        dialogBaslat.setCancelable(false);

        ImageView imageViewBaslat = dialogBaslat.findViewById(R.id.imageViewBaslat);
        TextView textViewBaslat = dialogBaslat.findViewById(R.id.textViewBaslat);
        TextView textViewYuksekYazi = dialogBaslat.findViewById(R.id.textViewYuksekYazi);
        TextView textViewBaslatEnYuksek = dialogBaslat.findViewById(R.id.textViewBaslatEnYuksek);

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        int enYuksekSkor = sp.getInt("enYuksekSkor", 0);

        textViewBaslatEnYuksek.setText(String.valueOf(enYuksekSkor));

        imageViewBaslat.setOnClickListener(view -> {
            dialogBaslat.dismiss();
            t.start();
            ProgressBarBaslat();
        });

        dialogBaslat.show();



    }

    private void dialogGoster(){
        dialog.setContentView(R.layout.sonuc_ekrani);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        ImageView imageViewYenidenOyna = dialog.findViewById(R.id.imageViewYenidenOyna);
        ImageView imageViewEkstraSure = dialog.findViewById(R.id.imageViewEkstraSure);
        TextView puanim = dialog.findViewById(R.id.puanim);
        TextView puanGoster = dialog.findViewById(R.id.puanGoster);
        TextView textViewYuksekPuan = dialog.findViewById(R.id.textViewYuksekPuan);

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        int enYuksekSkor = sp.getInt("enYuksekSkor", -1);

        if (sayi>enYuksekSkor){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("enYuksekSkor",sayi);
            editor.commit();
            textViewYuksekPuan.setText(String.valueOf(sayi));
        } else{
            textViewYuksekPuan.setText(String.valueOf(enYuksekSkor));
        }
        puanGoster.setText(""+sayi);
        dialog.show();

        imageViewYenidenOyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sayi =0;
                textViewPuan.setText("Puan");
                textViewSaniye.setTextSize(40);
                ProgressBarBaslat();
                t.start();
            }
        });

        odulReklamYukle();

        imageViewEkstraSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mrewardedAd!=null){
                    mrewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Toast.makeText(MainActivity.this, "Ekstra süreniz eklendi.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            textViewSaniye.setTextSize(40);
                            ProgressBarBaslat();
                            t.start();
                        }
                    });
                }
            }
        });

    }

    public void odulReklamYukle(){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-8759388747431307/6379772560", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mrewardedAd = rewardedAd;
                mrewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        odulReklamYukle();
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mrewardedAd = null;
                Toast.makeText(MainActivity.this, "Lütfen internet bağlantınızı kontrol edin..."
                        , Toast.LENGTH_LONG).show();
            }
        });
    }



    private void ProgressBarBaslat(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i<=100){
                    i++;
                    progressBar.setProgress(i);
                    handler.postDelayed(this,290);
                }else {
                    i=0;
                    progressBar.setProgress(i);
                    handler.removeCallbacks(this);
                }
            }
        },300);
    }

    private void konfetiAt(){
        konfettiView.build().addColors(Color.YELLOW,Color.GREEN,Color.MAGENTA,Color.RED)
                .setDirection(0.0,359.0).setSpeed(1f,5f)
                .setFadeOutEnabled(true).setTimeToLive(3000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12,5f))
                .setPosition(-50f,konfettiView.getWidth()+50f,-50f,-50f)
                .streamFor(300,200L);
    }
}