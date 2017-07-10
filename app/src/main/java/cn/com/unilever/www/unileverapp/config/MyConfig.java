package cn.com.unilever.www.unileverapp.config;

import android.content.Intent;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class MyConfig {
    public static Bitmap bitmap;
    //问题
    public static ArrayList<String> problem = new ArrayList<>();
    //选择问题序号(有多余)
    public static ArrayList<Integer> sourceStrArray = new ArrayList<>();
    //被提问人员
    public static String[] name;
    //优
    public static int ExcellentNumber = 0;
    //良
    public static int FineNumber = 0;
    //差
    public static int DadNumber = 0;
}
