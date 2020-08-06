package org.rsslosc;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Sri {

    int ListIndex;
    int status;
    Map.Entry<String, String> nowWords;
    Map.Entry<String, String> lastWords;
    LinkedList<Map.Entry<String, String>> linkedList;
    LinkedList<NowUnit> level;

    void save() throws IOException {
        String line = System.lineSeparator();
        if (nowWords == null || lastWords == null || linkedList == null || level == null) {
            return;
        }
        FileWriter fileWriter = new FileWriter(new File(Name.Sri));
        fileWriter.write(ListIndex + line);
        fileWriter.write(status + line);
        fileWriter.write(nowWords.getKey() + " " + nowWords.getValue() + line);
        fileWriter.write(lastWords.getKey() + " " + lastWords.getValue() + line);

        fileWriter.write(linkedList.size() + line);
        for (Map.Entry<String, String> m : linkedList) {
            fileWriter.write(m.getKey() + " " + m.getValue() + line);
        }

        fileWriter.write(level.size() + line);
        for (NowUnit nowUnit : level) {
            fileWriter.write(nowUnit.getNum() + "+" +
                    nowUnit.getA() + "+" + nowUnit.getEntry().getKey() + "+" +
                    nowUnit.getEntry().getValue() + line);
        }
        fileWriter.flush();
        fileWriter.close();
    }

    Sri get() throws IOException {
        File file = new File(Name.Sri);
        if (!file.exists()) {
            return null;
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = bufferedReader.readLine();
        ListIndex = Integer.parseInt(s);
        s = bufferedReader.readLine();
        status = Integer.parseInt(s);

        String[] s1 = bufferedReader.readLine().split(" ");
        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put(s1[0], s1[1]);
        nowWords = stringHashMap.entrySet().iterator().next();

        s1 = bufferedReader.readLine().split(" ");
        stringHashMap = new HashMap<>();
        stringHashMap.put(s1[0], s1[1]);
        lastWords = stringHashMap.entrySet().iterator().next();

        int n = Integer.parseInt(bufferedReader.readLine());
        linkedList = new LinkedList<>();
        while (n-- != 0) {
            s1 = bufferedReader.readLine().split(" ");
            stringHashMap = new HashMap<>();
            stringHashMap.put(s1[0], s1[1]);
            linkedList.add(stringHashMap.entrySet().iterator().next());
        }

        n = Integer.parseInt(bufferedReader.readLine());
        level = new LinkedList<>();
        while (n-- != 0) {
            s1 = bufferedReader.readLine().split("\\+");
            stringHashMap = new HashMap<>();
            stringHashMap.put(s1[2], s1[3]);
            NowUnit nowUnit = new NowUnit(stringHashMap.entrySet().iterator().next(),
                    Integer.parseInt(s1[1]), Integer.parseInt(s1[0]));
            level.add(nowUnit);
        }
        bufferedReader.close();
        fileReader.close();
        return this;
    }
}
