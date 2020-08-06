package org.rsslosc;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.util.*;

public class Update {
    public static Update INSTANCE;

    static {
        try {
            INSTANCE = new Update();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MainStage myController;
    int totalWords;
    int totalLastWords;
    Map.Entry<String, String> nowWords;
    Map.Entry<String, String> lastWords;
    LinkedList<Map.Entry<String, String>> linkedList = new LinkedList<>();
    LinkedList<NowUnit> level = new LinkedList<>();
    int ListIndex = 0;
    boolean success = false;
    boolean UnKnowWords = false;
    //0.word  1.word description
    int status = 0;
    Hashtable<String, Integer> hashtable = new Hashtable<>();
    GetHistory getHistory = new GetHistory();
    GetWord getWord = new GetWord();

    SimpleStringProperty descriptionProperty = new SimpleStringProperty();
    SimpleStringProperty wordsProperty = new SimpleStringProperty();
    SimpleStringProperty okkProperty = new SimpleStringProperty();
    double nowH = 0., nowW = 0.;
    int roll = 0;

    private Update() throws IOException {
        totalWords = getWord.getLen();
        totalLastWords = getHistory.getLen();
    }

    public static Update getInstance() {

        return INSTANCE;
    }

    public void set(MainStage mainStage) throws IOException {
        myController = mainStage;
        Image image = new Image("file:" + Name.pic);
        myController.imgView.setImage(image);
        myController.imgView.setSmooth(true);
        myController.imgView.setOpacity(0.3);
        myController.description.textProperty().bind(descriptionProperty);
        myController.words.textProperty().bind(wordsProperty);
        myController.okk.textProperty().bind(okkProperty);
        myController.words.setSmooth(true);
        myController.description.setSmooth(true);
        myController.okk.setSmooth(true);
        myController.description.setFill(Color.web("#090909"));
        myController.words.setStrokeWidth(3);
        myController.words.setStroke(Color.web("#38348b"));
        Sri sri = new Sri().get();
        if (sri != null) {
            this.level = sri.level;
            this.nowWords = sri.nowWords;
            this.lastWords = sri.lastWords;
            this.ListIndex = sri.ListIndex;
            this.linkedList = sri.linkedList;
            showNext();
        } else {
            addToList(20);
            showNext();
        }
    }
    void close() throws IOException {
        Sri sri = new Sri();
        sri.lastWords = this.lastWords;
        sri.level = this.level;
        sri.linkedList = this.linkedList;
        sri.ListIndex = this.ListIndex;
        sri.nowWords = this.nowWords;
        sri.status = this.status;
        sri.save();
    }
    int succ = 0;
    void run(KeyEvent event){
        KeyCode code = event.getCode();
        try {
            if (success) {
                wordsProperty.setValue("");
                descriptionProperty.setValue("");
                myController.rightText.setText("继续背诵");

                File reviewFile = new File("ReviewWords");
                boolean reviewWordsExists = true;
                if (!reviewFile.exists() || reviewFile.length() <= 3) {
                    reviewWordsExists = false;
                } else {
                    myController.leftText.setText("回顾之前");
                }
                succ++;
                if (succ >= 2) {
                    if (reviewWordsExists) {
                        //左
                        if (code == KeyCode.Q || code == KeyCode.A || code == KeyCode.Z ||
                                code == KeyCode.W || code == KeyCode.S || code == KeyCode.X ||
                                code == KeyCode.E || code == KeyCode.D || code == KeyCode.C ||
                                code == KeyCode.R || code == KeyCode.F || code == KeyCode.V ||
                                code == KeyCode.T || code == KeyCode.G) {
                            FileReader fileReader1 = new FileReader(reviewFile);
                            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                            int n = Integer.parseInt(bufferedReader1.readLine());
                            HashMap<String, String> map = new HashMap<>();
                            while (n-- != 0) {
                                String s1 = bufferedReader1.readLine();
                                String[] s = s1.split("\\+");
                                map.put(s[0], s[1]);
                            }

                            bufferedReader1.close();
                            fileReader1.close();
                            linkedList.clear();
                            linkedList.addAll(map.entrySet());
                            level.clear();
                            hashtable.clear();
                            showNext();
                            myController.leftText.setText("");
                            myController.rightText.setText("");
                            succ = 0;
                            success = false;
                            return;
                        }
                        succ--;
                    }
                    if (code == KeyCode.Y || code == KeyCode.H || code == KeyCode.B ||
                            code == KeyCode.U || code == KeyCode.I || code == KeyCode.J ||
                            code == KeyCode.N || code == KeyCode.O || code == KeyCode.K ||
                            code == KeyCode.P || code == KeyCode.L || code == KeyCode.M) {
                        linkedList.clear();
                        level.clear();
                        hashtable.clear();
                        myController.leftText.setText("");
                        myController.rightText.setText("");
                        addToList(20);
                        showNext();
                        succ = 0;
                        success = false;
                        return;
                    }

                }
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        switch (status) {
            case 0:
                if (code == KeyCode.Q || code == KeyCode.A || code == KeyCode.Z) {
                    showDescription(1);
                } else if (code == KeyCode.W || code == KeyCode.S || code == KeyCode.X ||
                        code == KeyCode.E || code == KeyCode.D || code == KeyCode.C) {
                    showDescription(2);
                } else if (code == KeyCode.R || code == KeyCode.F || code == KeyCode.V ||
                        code == KeyCode.T) {
                    showDescription(3);
                } else if (code == KeyCode.G || code == KeyCode.Y || code == KeyCode.H ||
                        code == KeyCode.B || code == KeyCode.U) {
                    showDescription(4);
                } else if (code == KeyCode.I || code == KeyCode.J || code == KeyCode.N ||
                        code == KeyCode.O || code == KeyCode.K) {
                    showDescription(5);
                } else if (code == KeyCode.P || code == KeyCode.L || code == KeyCode.M) {
                    showDescription(6);
                }
                break;
            case 1:
                try {
                    showNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void showNext() throws IOException {
        System.out.println(linkedList.toString());
        for (NowUnit n : level) {
            System.out.print(n.getEntry().getKey() + "_" + n.getNum() + " ");
        }
        System.out.println();
        System.out.println(level.size());
        System.out.println(roll);

        myController.total.setText("总共单词: " + totalWords + System.lineSeparator() +
                "总共剩余: " + totalLastWords + System.lineSeparator() +
                "当前: " + (level.size() + linkedList.size() - ListIndex)+ System.lineSeparator()
                );
        myController.words.setFill(Color.web("#38348b"));
        descriptionProperty.setValue("");
        okkProperty.setValue("");
        int levelSize = level.size();
        int listSize = linkedList.size();

        for (NowUnit nowUnit : level) {
            nowUnit.setNum(nowUnit.getNum() + nowUnit.getA());
        }

        if (ListIndex == listSize && levelSize == 0) {

            wordsProperty.setValue("Successfully!!!");
            descriptionProperty.setValue("恭喜你完成了任务");
            FileWriter fileWriter = new FileWriter(new File("ReviewWords"));
            fileWriter.write(hashtable.size() + System.lineSeparator());
            for (String ss : hashtable.keySet()) {
                if (hashtable.get(ss) > 0) {
                    for (Map.Entry<String, String> s : linkedList) {
                        if (s.getKey().equals(ss)) {
                            fileWriter.write(ss + "+" + s.getValue() + System.lineSeparator());
                            break;
                        }
                    }
                }
            }
            fileWriter.flush();
            fileWriter.close();
            myController.wordsList.setText("");
            ListIndex = 0;
            success = true;
            return;
        } else if (ListIndex < listSize && levelSize <= 3) {
            if (lastWords != null) {
                lastWords = nowWords;
            }
            nowWords = linkedList.get(ListIndex++);
            wordsProperty.setValue(nowWords.getKey());
//            status = 0;
            roll++;
//            return;
        } else if (ListIndex != listSize && roll >= levelSize) {
            lastWords = nowWords;
            nowWords = linkedList.get(ListIndex++);
            wordsProperty.setValue(nowWords.getKey());
            roll = 0;
        } else {
            int max = Integer.MIN_VALUE;
            NowUnit nowUnit = null;
            for (NowUnit n : level) {
                if (max < n.getNum()) {
                    max = n.getNum();
                    nowUnit = n;
                }
            }

            lastWords = nowWords;
            assert nowUnit != null;
            nowWords = nowUnit.getEntry();
            wordsProperty.setValue(nowWords.getKey());
            roll++;
            if (ListIndex >= listSize) {
                int tInt;
                String key = nowWords.getKey();
                if (hashtable.containsKey(key)) {
                    tInt = hashtable.get(key);
                    hashtable.remove(key);
                    hashtable.put(key, tInt + 1);
                } else {
                    hashtable.put(key, 1);
                }
                if (hashtable.get(key) > 3) {
                    UnKnowWords = true;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (NowUnit n : level) {
            stringBuilder.append(n.getEntry().getKey()).append(System.lineSeparator());
        }
        System.out.println(level.size() + " |||||" + levelSize);
        System.out.println(listSize + " ]]]]" + linkedList.size());
        System.out.println(ListIndex);
        for (int i = ListIndex; i < linkedList.size(); i++) {
            stringBuilder.append(linkedList.get(i).getKey()).append(System.lineSeparator());
        }
        myController.wordsList.setText(stringBuilder.toString());
        status = 0;


    }

    public void showDescription(int grade) {
        String value = nowWords.getValue();
        descriptionProperty.setValue(value);

        NowUnit nowUnit = null;
        NowUnit tNewUnit = null;
        for (NowUnit n : level) {
            if (n.getEntry().equals(nowWords)) {
                nowUnit = n;
                break;
            }
        }
        if (nowUnit == null) {
            if (grade != 6) {
                int max = 0;
                for (NowUnit n : level) {
                    max = Math.max(max, n.getNum());
                }
                level.add(new NowUnit(nowWords, 7 - grade, max - 10));
            }
        } else {
            if (grade != 6) {
                tNewUnit = new NowUnit(nowWords, 7 - grade, nowUnit.getNum() - 10);
                level.add(tNewUnit);
            }
            level.remove(nowUnit);
        }
        switch (grade) {
            case 1:
                okkProperty.setValue("继续努力！！");
                myController.words.setFill(Color.web("#ff6666"));
                break;
            case 2:
                okkProperty.setValue("加油加油！！");
                myController.words.setFill(Color.web("#ff9966"));

                break;
            case 3:
                okkProperty.setValue("可以可以！！");
                myController.words.setFill(Color.web("#ffcc33"));
                break;
            case 4:
                okkProperty.setValue("不错不错！！");
                myController.words.setFill(Color.web("#eee733"));
                break;
            case 5:
                okkProperty.setValue("优秀！！");
                myController.words.setFill(Color.web("#c4f836"));
                break;
            case 6:
                okkProperty.setValue("完美！！");
                myController.words.setFill(Color.web("#6ae639"));
        }
        if (UnKnowWords) {
            level.remove(tNewUnit);
            System.out.println("这里！");
            System.out.println(nowWords.toString());
            UnKnowWords = false;
        }
        status = 1;
    }

    public void addToList(int n) {
        HashMap<String, String> shows = null;
        try {
            shows = getRandom(n);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert shows != null;
        linkedList.addAll(shows.entrySet());
    }

    HashMap<String, String> getRandom(int n) throws IOException {
        if (totalLastWords < n) {
            return null;
        }
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < n) {
            set.add(random.nextInt(totalLastWords) + 1);
        }
//        System.out.println(set);
        getHistory.delete(set);
        totalLastWords = getHistory.getLen();
        return getWord.get(set);
    }

    public void changeH(Number newValue) {
        nowH = newValue.doubleValue();
        changeFond();
    }

    public void changeW(Number newValue) {
        nowW = newValue.doubleValue();
        changeFond();
    }

    void changeFond() {
        double size = Math.min(nowH, nowW);
        myController.words.setFont(Font.font(size * 0.15));
        myController.description.setFont(Font.font(size * 0.07));
        myController.okk.setFont(Font.font(size * 0.05));
        myController.words.setWrappingWidth(nowW);
        myController.description.setWrappingWidth(nowW);
        myController.total.setFont(Font.font(size * 0.02));
        myController.imgView.setPreserveRatio(false);
        myController.imgView.setFitWidth(nowW);
        myController.imgView.setFitHeight(nowH);
        myController.wordsList.setFont(Font.font(size * 0.02));
        myController.wordsListPane.setPadding(new Insets(size * 0.2, 20, 20, 20));
        myController.leftText.setFont(Font.font(size * 0.13));
        myController.rightText.setFont(Font.font(size * 0.13));
    }
}
