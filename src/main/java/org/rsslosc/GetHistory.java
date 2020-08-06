package org.rsslosc;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class GetHistory {

    public static void main(String[] args) throws IOException {
//        GetHistory getHistory = new GetHistory(4509);
//        getHistory.init();
//        Set<Integer> array = new HashSet<>();
//        array.add(3);
//        array.add(5);
//        array.add(6);
//        array.add(7);
//        getHistory.delete(array);
//        Set<Integer> all = getHistory.getAll();
//        System.out.println(all.toString());
    }

    int getLen() throws IOException {
        return getAll().size();
    }

    void delete(Set<Integer> set) throws IOException {
        File file = new File(Name.HISTORY);
        if (!file.exists()) {
            return;
        }

        Set<Integer> all = getAll();
        ArrayList<Integer> arrayList = new ArrayList<>(all);

        for (Integer value : set) {
            arrayList.remove((int) value);
        }
        all.clear();
        all.addAll(arrayList);
        FileWriter fileWriter = new FileWriter(file);
        for (Integer integer : all) {
            fileWriter.write(integer + System.lineSeparator());
        }
        fileWriter.flush();
        fileWriter.close();
    }

    Set<Integer> getAll() throws IOException {
        File file = new File(Name.HISTORY);
        if (!file.exists()) {
            return null;
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Set<Integer> set = new HashSet<>();
        String s;

        while ((s = bufferedReader.readLine()) != null) {
            set.add(Integer.parseInt(s));
        }
        bufferedReader.close();
        fileReader.close();
        return set;
    }
    void init(int len) throws IOException {
        File file = new File(Name.HISTORY);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                return;
            }
            create(len);
        }
    }
    void create(int len) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(Name.HISTORY));
        for (int i = 1; i <= len; i++) {
            fileWriter.write(i + System.lineSeparator());
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
