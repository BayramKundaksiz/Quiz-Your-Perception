package com.QuizYourPerception;

import android.content.Context;
import android.graphics.Color;

import java.util.Random;

public class AtamaYap{

    public int[] items = new int[]{
            Color.parseColor("#FF2400")
            ,Color.parseColor("#120A8F")
            ,Color.parseColor("#FFFF00")
            ,Color.parseColor("#660099")
            ,Color.parseColor("#000000")
            ,Color.parseColor("#008000")
            ,Color.parseColor("#FF7F00")
            ,Color.parseColor("#FF69B4")
            ,Color.parseColor("#30D5C8")};

    int[] renkIsımleri = {R.string.kirmizi, R.string.lacivert, R.string.sari,
                    R.string.mor, R.string.siyah, R.string.yeşil, R.string.turuncu,
                    R.string.pembe, R.string.turkuaz};

    Random rand = new Random();

    public int renkleriAta(){
        return items[rand.nextInt(items.length)];
    }

    public int denemeYeni(){return renkIsımleri[rand.nextInt(renkIsımleri.length)];}
}
