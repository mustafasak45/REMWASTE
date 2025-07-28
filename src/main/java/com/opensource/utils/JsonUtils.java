package com.opensource.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class JsonUtils {

    Logger logger= LogManager.getLogger(this.getClass());
    private void setDoubleGsonFormat(GsonBuilder gsonBuilder){

        gsonBuilder.registerTypeAdapter(BigDecimal.class, (JsonSerializer<BigDecimal>) (src, typeOfSrc, context) -> {

            Number n = //src.scale() >= 8 ? (
                    new Number() {

                        @Override
                        public long longValue() {
                            return 0;
                        }

                        @Override
                        public int intValue() {
                            return 0;
                        }

                        @Override
                        public float floatValue() {
                            return 0;
                        }

                        @Override
                        public double doubleValue() {
                            return 0;
                        }

                        @Override
                        public String toString() {
                            return new BigDecimal(String.valueOf(src)).toPlainString();
                        }
                    }; //) : src
            return new JsonPrimitive(n);
        });
    }

    public Boolean writeJson(String jsonString, String fileLocation, boolean prettyPrint, boolean serializeNulls, boolean isAppend){

        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileLocation, isAppend), StandardCharsets.UTF_8));
            JsonElement element = JsonParser.parseString(jsonString);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.disableHtmlEscaping();
            setDoubleGsonFormat(gsonBuilder);
            if (prettyPrint) { gsonBuilder.setPrettyPrinting(); }
            if (serializeNulls) { gsonBuilder.serializeNulls(); }
            Gson gson = gsonBuilder.create();
            gson.toJson(element, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            logger.error(getStackTraceLog(e));
        }
        return false;
    }

    public <PANDA> Boolean writeJson(PANDA panda, String fileLocation, boolean prettyPrint, boolean serializeNulls, boolean isAppend){

        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileLocation, isAppend), StandardCharsets.UTF_8));
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.disableHtmlEscaping();
            setDoubleGsonFormat(gsonBuilder);
            if (prettyPrint) { gsonBuilder.setPrettyPrinting(); }
            if (serializeNulls) { gsonBuilder.serializeNulls(); }
            Gson gson = gsonBuilder.create();
            gson.toJson(panda, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            logger.error(getStackTraceLog(e));
        }
        return false;
    }

    public <PANDA> PANDA readJson(Type type, String fileLocationOrStringJson, boolean isFile){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        setDoubleGsonFormat(gsonBuilder);
        Gson gson = gsonBuilder.create();
        try {
            if(isFile){
                FileReader fileReader = new FileReader(fileLocationOrStringJson);
                return gson.fromJson(fileReader, type);
            }
            return gson.fromJson(fileLocationOrStringJson, type);
        } catch (FileNotFoundException e) {
            logger.error(getStackTraceLog(e));
        }
        return null;
    }

    public Type getClassTypeToken(Type panda, Type... pandaClasses){
        // Type elementType = new TypeToken<>(){}.getType();
        if (pandaClasses.length != 0)
            return TypeToken.getParameterized(panda, pandaClasses).getType();
        return TypeToken.getParameterized(panda).getType();
    }

    public String getJsonStringWithBufferedReader(String fileLocation){

        StringBuilder jsonStringBuilder = new StringBuilder();
        InputStream propertiesStream = null;
        try {
            propertiesStream = new FileInputStream(fileLocation);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(propertiesStream, StandardCharsets.UTF_8));
            String jsonString;
            while(true){
                if ((jsonString = bufferedReader.readLine()) == null) break;
                jsonStringBuilder.append(jsonString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStringBuilder.toString();
    }

    String getStackTraceLog(Throwable e){

        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String error = e.toString() + "\r\n";
        for (int i = 0; i < stackTraceElements.length; i++) {

            error = error + "\r\n" + stackTraceElements[i].toString();
        }
        return error;
    }
}
