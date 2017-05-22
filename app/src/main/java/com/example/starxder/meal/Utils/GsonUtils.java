package com.example.starxder.meal.Utils;

import com.example.starxder.meal.Bean.Wxorder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class GsonUtils {
    public static List<Wxorder> getWxOrderByGson(String jsonString) {
        List<Wxorder> list = new ArrayList<Wxorder>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<Wxorder>>() {

        }.getType());
        return list;
    }
}
