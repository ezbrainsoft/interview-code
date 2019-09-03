package com.jlesoft.civilservice.koreanhistoryexam9.util;

import java.util.Random;

public class GetRandomText {

    public static String getRandomText() {
        Random rnd = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < 20; i++){
            if(rnd.nextBoolean()){
                sb.append((char)((int)(rnd.nextInt(26))+97));
            }else{
                sb.append((rnd.nextInt(10)));
            }
        }
        return sb.toString();
    }

    public static String getRandomText(int length) {
        Random rnd = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; i++){
            if(rnd.nextBoolean()){
                sb.append((char)((int)(rnd.nextInt(26))+97));
            }else{
                sb.append((rnd.nextInt(10)));
            }
        }
        return sb.toString();
    }
}
