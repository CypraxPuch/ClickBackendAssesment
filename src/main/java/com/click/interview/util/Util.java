package com.click.interview.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Source code generated by Gerardo Pucheta Figueroa to Click Mexico
 * Twitter: @ledzedev
 * http://ledze.mx
 * 9/24/2018
 */
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    public static boolean validTransactionId(String text) {
        Pattern pattern = Pattern.compile("([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static boolean validJsonFormat(String transactionJson) {
        try {
            LOG.info("validating: {}",transactionJson);
            JsonReader jsonReader = Json.createReader(new StringReader(transactionJson));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            int uId = jsonObject.getInt("user_id");
            return (uId > 0);
        } catch (JsonException e) {
            return false;
        }
    }

    public static JsonObject getValueFromJson(String transactionJson) {
        JsonReader jsonReader = Json.createReader(new StringReader(transactionJson));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        return jsonObject;
    }

    public static int getUserIdFromJson(String transactionJson) {
        return getValueFromJson(transactionJson).getInt("user_id");
    }

}
