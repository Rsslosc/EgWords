package org.rsslosc;

import java.io.*;
import java.util.*;

public class GetWord {
    HashMap<String, String> hashMap = new LinkedHashMap<String, String>();

    private int len = 0;

    GetWord() throws IOException {
        init();
    }

    public static void main(String[] args) throws IOException {
        GetWord getWord = new GetWord();
        getWord.init();
        Integer[] integer = new Integer[]{1, 2};
        Set<Integer> set = new HashSet<>();
        set.add(2);
        set.add(4);
        set.add(5);

        HashMap<String, String> ff = getWord.get(set);
        System.out.println(ff.toString());
    }

    public int getLen() {
        return len;
    }

    HashMap<String, String> get(Set<Integer> n) {
        return get(n.toArray(new Integer[]{}));
    }


    HashMap<String, String> get(Integer[] n) {
        int a = 0;
        int i = 0;
        int nowLen = n.length;
        Arrays.sort(n);
        HashMap<String, String> stringHashMap = new HashMap<>();
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            if (i == nowLen) {
                break;
            }
            if (a++ < n[i]) {
                iterator.next();
                continue;
            }
            String next = iterator.next();
            stringHashMap.put(next, hashMap.get(next));
            ++i;
        }
        return stringHashMap;
    }


    HashMap<String, String> get(Integer n) {
        int a = 0;
        HashMap<String, String> stringHashMap = new LinkedHashMap<String, String>();
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            if (a++ < n) {
                iterator.next();
                continue;
            }
            String next = iterator.next();
            stringHashMap.put(next, hashMap.get(next));
            return stringHashMap;
        }
        return null;
    }

    void init() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(Name.CET4)),
                "GB2312");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String key = s.split(" ")[0];
            hashMap.put(key, s.substring(key.length()).trim());
        }
        len = hashMap.size();
        bufferedReader.close();
        inputStreamReader.close();
    }
}
