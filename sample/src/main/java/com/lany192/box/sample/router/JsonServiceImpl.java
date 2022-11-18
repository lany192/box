package com.lany192.box.sample.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.github.lany192.log.XLog;
import com.github.lany192.utils.JsonUtils;

import java.lang.reflect.Type;

/**
 * 如果需要传递自定义对象，需要实现 SerializationService,并使用@Route注解标注(方便用户自行选择序列化方式)
 *
 * @author lyg
 */
@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    private final XLog log = XLog.tag(getClass().getSimpleName());

    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String json, Class<T> clazz) {
        log.d("json转对象A：" + json + "  class:" + clazz.getSimpleName());
        return JsonUtils.json2object(json, clazz);
    }

    @Override
    public String object2Json(Object object) {
        String json = JsonUtils.object2json(object);
        log.d(object.getClass().getSimpleName() + "对象转Json：" + json);
        return json;
    }

    @Override
    public <T> T parseObject(String json, Type clazz) {
        log.d("json转对象：" + json + "  Type:" + clazz.getClass().getSimpleName());
        return JsonUtils.json2object(json, clazz);
    }
}