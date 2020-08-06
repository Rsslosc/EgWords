package org.rsslosc;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

public class NowUnit {
    private String Key;
    private String Value;
    private Map.Entry<String, String> entry;
    private int a;
    private int num;

    public NowUnit() {
    }

    public NowUnit(String key, String value, int a, int num) {
        Key = key;
        Value = value;
        this.a = a;
        this.num = num;
    }

    public NowUnit(Map.Entry<String, String> entry, int a, int num) {
        this.entry = entry;
        this.a = a;
        this.num = num;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Map.Entry<String, String> getEntry() {
        return entry;
    }

    public void setEntry(Map.Entry<String, String> entry) {
        this.entry = entry;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "NowUnit{" +
                "Key='" + Key + '\'' +
                ", Value='" + Value + '\'' +
                ", entry=" + entry +
                ", a=" + a +
                ", num=" + num +
                '}';
    }
}
