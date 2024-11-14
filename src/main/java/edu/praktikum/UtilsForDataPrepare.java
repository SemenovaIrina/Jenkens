package edu.praktikum;

import java.util.Random;

public class UtilsForDataPrepare {
    public static String stringRandomGenerate(int length) {
        Random random = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            //используем символы с кодами в диапазоне от 32 до 175 (пробел, знаки операций и препинания, английские и русские буквы, цифры)
            //если появятся ограничения на используемые символы, то можно описать массив с этими символами и выбирать случайные значения из него
            text[i] = (char) (random.nextInt(150) + 32); //150=175-32+1
        }
        return new String(text);
    }
}
