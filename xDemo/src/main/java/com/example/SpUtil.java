package com.example;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * ClassName：SpUtil<p>
 * Author：Oubowu<p>
 * Fuction：SharedPreferences工具<p>
 * CreateDate：2015/7/9 16:27<p>
 * UpdateAuthor：<p>
 * UpdateDate：<p>
 */
public class SpUtil {

    private static final String APP_SP = "app_sp";
    private static SpUtil instance;
    private Context mContext;

    public static SpUtil getInstance() {
        if (instance == null) {
            instance = new SpUtil();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public String readString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public String readString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public void writeString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public boolean readBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public boolean readBoolean(String key, boolean def) {
        return getSharedPreferences().getBoolean(key, def);
    }

    public void writeBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public int readInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public int readInt(String key, int value) {
        return getSharedPreferences().getInt(key, value);
    }

    public void writeInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    public long readLong(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public void writeLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    public void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    public void removeAll() {
        getSharedPreferences().edit().clear().apply();
    }

    /**
     * 清除preference
     *
     * @param preName
     */
    public void removeAll(String preName) {
        SharedPreferences pre = getSharedPreferences(preName);
        pre.edit().clear().apply();
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(APP_SP);
    }

    /**
     * 用于存储实体对象时,每个对象单独保存到一个preference文件中
     *
     * @param name
     * @return
     */
    private SharedPreferences getSharedPreferences(String name) {
        return mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public <T> void removeBean(Class<T> clazz) {
        SharedPreferences.Editor editor = getSharedPreferences(clazz.getSimpleName()).edit();
//        Class clazz = object.getClass();
        editor.clear().apply();
    }

    public void saveBean(Object object) {
        if (object == null) {
            return;
        }
//        L.d("saveBean " + object.getClass().getSimpleName());
//        L.d(object.toString());
        SharedPreferences.Editor editor = getSharedPreferences(object.getClass().getSimpleName()).edit();
        Class clazz = object.getClass();
        Field[] arrFiled = clazz.getDeclaredFields();
        try {
            for (Field f : arrFiled) {
                if (!TYPES.containsKey(f.getType())) {
//                    L.d("not support type " + f.getType().getName());
                    continue;
                }
                int type = TYPES.get(f.getType());
                switch (type) {
                    case CLZ_BYTE:
                    case CLZ_SHORT:
                    case CLZ_INTEGER: {
                        int value = f.getInt(object);
                        editor.putInt(f.getName(), value).apply();
                    }
                    break;
                    case CLZ_LONG: {
                        long value = f.getLong(object);
                        editor.putLong(f.getName(), value).apply();
                    }
                    break;
                    case CLZ_STRING: {
                        String value = (String) f.get(object);
                        editor.putString(f.getName(), value).apply();
                    }
                    break;
                    case CLZ_BOOLEAN:
                        editor.putBoolean(f.getName(), f.getBoolean(object)).apply();
                        break;
                    case CLZ_FLOAT:
                        editor.putFloat(f.getName(), f.getFloat(object)).apply();
                        break;
                    case CLZ_DOUBLE:
                        editor.putFloat(f.getName(), (float) f.getDouble(object)).apply();
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T readBean(Class<T> clazz) {
        T t = null;
        SharedPreferences sharedPre = getSharedPreferences(clazz.getSimpleName());
        boolean hasValue = false;
        try {
            t = clazz.newInstance();
            Field[] arrFiled = clazz.getDeclaredFields();
            for (Field f : arrFiled) {
                if (!TYPES.containsKey(f.getType()) || !sharedPre.contains(f.getName())) {
                    continue;
                }
                hasValue = true;
                int type = TYPES.get(f.getType());
                switch (type) {
                    case CLZ_BYTE:
                        byte byteValue = (byte) sharedPre.getInt(f.getName(), 0);
                        f.set(t, byteValue);
                        break;
                    case CLZ_SHORT:
                        short shortValue = (short) sharedPre.getInt(f.getName(), 0);
                        f.set(t, shortValue);
                        break;
                    case CLZ_INTEGER:
                        int intValue = sharedPre.getInt(f.getName(), 0);
                        f.set(t, intValue);
                        break;
                    case CLZ_LONG:
                        if ("serialVersionUID".equals(f.getName())) {
                            continue;
                        }
                        long longValue = sharedPre.getLong(f.getName(), 0L);
                        f.set(t, longValue);
                        break;
                    case CLZ_STRING:
                        String str = sharedPre.getString(f.getName(), null);
                        f.set(t, str);
                        break;
                    case CLZ_BOOLEAN:
                        boolean bool = sharedPre.getBoolean(f.getName(), false);
                        f.set(t, bool);
                        break;
                    case CLZ_FLOAT:
                        float floatValue = sharedPre.getFloat(f.getName(), 0.0f);
                        f.set(t, floatValue);
                        break;
                    case CLZ_DOUBLE:
                        double doubleValue = sharedPre.getFloat(f.getName(), 0.0f);
                        f.set(t, doubleValue);
                        break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        L.d("readBean " + clazz.getSimpleName());
        if (hasValue) {
//            L.d(t.toString());
            return t;
        } else {
            return null;
        }
    }

    public static final int CLZ_BYTE = 1;
    public static final int CLZ_SHORT = 2;
    public static final int CLZ_INTEGER = 3;
    public static final int CLZ_LONG = 4;
    public static final int CLZ_STRING = 5;
    public static final int CLZ_BOOLEAN = 6;
    public static final int CLZ_FLOAT = 7;
    public static final int CLZ_DOUBLE = 8;
    public static final Map<Class<?>, Integer> TYPES;

    static {
        TYPES = new HashMap<Class<?>, Integer>();
        TYPES.put(byte.class, CLZ_BYTE);
        TYPES.put(short.class, CLZ_SHORT);
        TYPES.put(int.class, CLZ_INTEGER);
        TYPES.put(long.class, CLZ_LONG);
        TYPES.put(String.class, CLZ_STRING);
        TYPES.put(boolean.class, CLZ_BOOLEAN);
        TYPES.put(float.class, CLZ_FLOAT);
        TYPES.put(double.class, CLZ_DOUBLE);
    }
}
