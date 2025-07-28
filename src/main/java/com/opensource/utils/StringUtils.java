package com.opensource.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.text.Collator;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.fail;

public class StringUtils {
    Logger logger = LogManager.getLogger(this.getClass());

    public static String getRandomStringWithLength(Integer length, Boolean useLetters,Boolean useNumbers){
        return RandomStringUtils.random(length,useLetters,useNumbers);
    }

    public String getRandomStringWithLength(String length, String useLetters,String useNumbers){
        return getRandomStringWithLength(Integer.parseInt(length),Boolean.parseBoolean(useLetters),Boolean.parseBoolean(useNumbers));
    }

    public int getRandomNumber(int length){

        Random random = new Random();
        return random.nextInt(length);
    }

    public Integer getRandomInt(int origin, int bound){

        return getRandomIteratorInt(origin, bound).nextInt();
    }

    public PrimitiveIterator.OfInt getRandomIteratorInt(int origin, int bound){

        Random random = new Random();
        return random.ints(origin, bound).iterator();
    }

    public Long getRandomLong(int origin, int bound){

        return getRandomIteratorLong(origin, bound).nextLong();
    }

    public PrimitiveIterator.OfLong getRandomIteratorLong(long origin, long bound){

        Random random = new Random();
        return random.longs(origin, bound).iterator();
    }

    public static Integer randomNumber(int origin, int bound){

        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public Double randomNumber(double origin, double bound){

        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    public int randomNumber(int number){

        Random random = new Random();
        return random.nextInt(number);
    }

    public List<Integer> getRandomNumberList(int number, int count){

        List<Integer> list = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            list.add(i);
        }
        int value;
        if (count == 0 || count > number){
            return list;
        }else if (count < 0){
            fail("count 0 dan küçük olamaz!");
        }
        for (int j = 0; j < count; j++) {
            value = randomNumber(number - j);
            numberList.add(list.get(value));
            list.remove(value);
        }
        return numberList;
    }


    public String randomString(int stringLength){

        Random random = new Random();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUWVXYZabcdefghijklmnopqrstuwvxyz0123456789".toCharArray();
        String stringRandom = "";
        for (int i = 0 ; i < stringLength ; i++){

            stringRandom = stringRandom + String.valueOf(chars[random.nextInt(chars.length)]);
        }

        return stringRandom;
    }

    public String randomStringExtended(int stringLength, String charType, String startCharType, int startCharLength){

        Random random = new Random();
        StringBuilder stringRandom = new StringBuilder();
        char[] chars = null;

        if(!startCharType.equals("")){

            chars = getChars(startCharType);
            for (int i = 0 ; i < startCharLength; i++) {
                stringRandom.append(String.valueOf(chars[random.nextInt(chars.length)]));
            }
            stringLength = stringLength - startCharLength;
        }

        chars = getChars(charType);

        for (int i = 0 ; i < stringLength; i++){

            stringRandom.append(String.valueOf(chars[random.nextInt(chars.length)]));
        }

        return stringRandom.toString();
    }

    public char[] getChars(String condition){

        String upperChars = "ABCDEFGHIJKLMNOPQRSTUWVXYZ";
        String lowerChars = "abcdefghijklmnopqrstuwvxyz";
        String numbers = "0123456789";
        String space = " ";
        String turkishCharUpper = "İÜÖŞÇĞ";
        String turkishCharLower = "ıüöşçğ";
        String specialChar = ".,:;%+#!'`=()[]&/\\?*-_<>|^≥≤µ~≈Ωæß∂@∑€®₺¥π¨~¬∆ƒ£";
        String specialChar2 = "☮✈♋웃유☠☯♥✌✖☢☣☤⚜❖Σ⊗♒♠Ω♤♣♧♡♦♢♔♕♚♛★☆✮✯☄☾☽☼۞۩✄✂✆✉✦✧∞♂♀☿❤❥❦❧™®©✗✘⊗♒▢◆◇○◎●◯Δ◕◔ʊϟღ回₪✓✔✕☥☦☧☨☩☪☫☬☭™©®¿¡½⅓⅔¼¾⅛⅜⅝⅞℅№⇨❝❞∃∧∠∨∩⊂⊃∪⊥∀ΞΓɐəɘεβɟɥλч∞ΣΠ⌥⌘文⑂ஜ๏";
        char[] chars = null;
        switch (condition){
            case "upper":
                chars = upperChars.toCharArray();
                break;
            case "lower":
                chars = lowerChars.toCharArray();
                break;
            case "char":
                chars = (upperChars + lowerChars).toCharArray();
                break;
            case "numeric":
                chars = numbers.toCharArray();
                break;
            case "upperCharNumber":
                chars = (upperChars + numbers).toCharArray();
                break;
            case "all":
                chars = (upperChars + lowerChars + numbers).toCharArray();
                break;
            case "allSpecialChar":
                chars = (upperChars + lowerChars + numbers + space + specialChar).toCharArray();
                break;
            default:
                fail("");
        }

        return chars;
    }

    public String getCopiedText() {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        String copiedText = null;
        try {
            copiedText = contents.getTransferData(DataFlavor.stringFlavor).toString();
        } catch (IOException | UnsupportedFlavorException e) {
            logger.error(getStackTraceLog(e));
        }
        return copiedText;
    }

    public void setCopiedText(String text){

        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection,null);
    }


    public static String stringTrim(String value, String trimCondition){

        if (value == null){
            return null;
        }
        switch (trimCondition){
            case "true":
                value = value.trim();
                break;
            case "clearSpace":
                value = value.replace("\r","").replace("\n","").trim();
                break;
            case "false":
            case "":
                break;
            default:
                fail(trimCondition + " condition hatalı");
        }
        return value;
    }

    private Matcher getMatcherKeyValue(String value){

        return Pattern.compile("\\{[A-Za-z0-9_\\-?=:;,.%+*$&/()<>|ıİüÜöÖşŞçÇğĞ]+KeyValue\\}").matcher(value);
    }

    public static boolean conditionValueControl(String expectedValue, String actualValue, String condition){

        boolean result = false;
        switch (condition){
            case "equal":
                result = actualValue != null && actualValue.equals(expectedValue);
                break;
            case "equalsIgnoreCase":
                result = actualValue != null && actualValue.equalsIgnoreCase(expectedValue);
                break;
            case "contain":
                result = actualValue != null && actualValue.contains(expectedValue);
                break;
            case "startWith":
                result = actualValue != null && actualValue.startsWith(expectedValue);
                break;
            case "endWith":
                result = actualValue != null && actualValue.endsWith(expectedValue);
                break;
            case "notEqual":
                result = actualValue != null && !actualValue.equals(expectedValue);
                break;
            case "notContain":
                result = actualValue != null && !actualValue.contains(expectedValue);
                break;
            case "notStartWith":
                result = actualValue != null && !actualValue.startsWith(expectedValue);
                break;
            case "notEndWith":
                result = actualValue != null && !actualValue.endsWith(expectedValue);
                break;
            case "notNull":
                result = actualValue != null;
                break;
            case "null":
                result = actualValue == null;
                break;
            case "regex":
                result = actualValue != null && Pattern.matches(expectedValue, actualValue);
                break;
            case "doubleEqual":
                result = actualValue != null && Double.parseDouble(expectedValue) == Double.parseDouble(actualValue);
                break;
            case "notDoubleEqual":
                result = actualValue != null && Double.parseDouble(expectedValue) != Double.parseDouble(actualValue);
                break;
            default:
                fail("conditionValueControl geçersiz durum - " + condition);
        }
        return result;
    }





    public void getListSort(List<String> list, boolean asc, Locale locale){

        if (asc) {
            list.sort(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    Collator collator = Collator.getInstance(locale);
                    return collator.compare(s1, s2);
                }
            });
        } else {
            list.sort(Collections.reverseOrder(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    Collator collator = Collator.getInstance(locale);
                    return collator.compare(s1, s2);
                }
            }));
        }
    }

    public <T extends Comparable<? super T>> void getListSort(List<T> list, boolean asc){

        if (asc) {
            Collections.sort(list);
        } else {
            list.sort(Collections.reverseOrder());
        }
    }


    public List<String> getStringListSameOrDifference(List<String> list1, List<String> list2, boolean condition){

        List<String> list = new ArrayList<>();
        for(String value: list1){
            boolean isValueExist = list2.contains(value);
            if (condition && isValueExist){
                list.add(value);
            }
            if (!condition && !isValueExist){
                list.add(value);
            }
        }
        return list;
    }

    public List<Integer> getListSameOrDifference(List<Integer> list1, List<Integer> list2, boolean condition){

        List<Integer> list = new ArrayList<>();
        for(Integer value: list1){
            boolean isValueExist = list2.contains(value);
            if (condition && isValueExist){
                list.add(value);
            }
            if (!condition && !isValueExist){
                list.add(value);
            }
        }
        return list;
    }

    public String getStackTraceLog(Throwable e){

        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String error = e.toString() + "\r\n";
        for (int i = 0; i < stackTraceElements.length; i++) {

            error = error + "\r\n" + stackTraceElements[i].toString();
        }
        return error;
    }
}
