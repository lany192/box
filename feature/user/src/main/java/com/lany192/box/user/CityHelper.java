package com.lany192.box.user;


import android.content.Context;

import com.github.lany192.utils.ContextUtils;
import com.github.lany192.utils.JsonUtils;
import com.lany192.box.user.entity.AreaItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class CityHelper {
    private volatile static CityHelper instance = null;
    private final List<AreaItem> items;

    private CityHelper() {
        String json = getJsonFromAssets(ContextUtils.getContext());
        items = JsonUtils.json2ArrayList(json, AreaItem.class);
    }

    public static CityHelper getInstance() {
        if (instance == null) {
            synchronized (CityHelper.class) {
                if (instance == null) {
                    instance = new CityHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private String getJsonFromAssets(Context ctx) {
        BufferedReader is = null;
        try {
            StringBuilder builder = new StringBuilder();
            is = new BufferedReader(new InputStreamReader(ctx.getAssets().open("area_info.json"), StandardCharsets.UTF_8));
            String buf = "";
            while ((buf = is.readLine()) != null) {
                builder.append(buf);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 获取省份
     */
    public List<AreaItem> getProvinces() {
        return items;
    }

    /**
     * 获取城市列表
     */
    public List<AreaItem> getCities(long provinceId) {
        List<AreaItem> items = new ArrayList<>();
        if (provinceId == 0) {
            return items;
        }
        for (int i = 0; i < items.size(); i++) {
            AreaItem info = items.get(i);
            if (info.getId() == provinceId) {
                items.addAll(info.getSubarea());
            }
        }
        return items;
    }

    /**
     * 获取地区列表
     */
    public List<AreaItem> getAreaItems(long provinceId, long cityId) {
        List<AreaItem> items = new ArrayList<>();
        if (provinceId == 0 || cityId == 0) {
            return items;
        }
        List<AreaItem> cities = getCities(provinceId);
        for (int i = 0; i < cities.size(); i++) {
            AreaItem info = cities.get(i);
            if (info.getId() == cityId) {
                items.addAll(info.getSubarea());
            }
        }
        return items;
    }

    /**
     * 获取省份
     */
    public AreaItem getProvince(long provinceId) {
        if (provinceId == 0) {
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            AreaItem info = items.get(i);
            if (info.getId() == provinceId) {
                return info;
            }
        }
        return null;
    }

    /**
     * 获取城市
     */
    public AreaItem getCity(long provinceId, long cityId) {
        if (provinceId == 0 || cityId == 0) {
            return null;
        }
        List<AreaItem> cities = getCities(provinceId);
        for (int i = 0; i < cities.size(); i++) {
            AreaItem info = cities.get(i);
            if (info.getId() == cityId) {
                return info;
            }
        }
        return null;
    }

    /**
     * 获取地区
     */
    public AreaItem getAreaItem(long provinceId, long cityId, long areaId) {
        if (provinceId == 0 || cityId == 0 || areaId == 0) {
            return null;
        }
        List<AreaItem> cities = getCities(provinceId);
        for (int i = 0; i < cities.size(); i++) {
            AreaItem info = cities.get(i);
            if (info.getId() == cityId) {
                List<AreaItem> items = info.getSubarea();
                for (int j = 0; j < items.size(); j++) {
                    if (areaId == items.get(j).getId()) {
                        return items.get(j);
                    }
                }
            }
        }
        return null;
    }
}
