package com.usebilbo.vertx.properties.loader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.usebilbo.vertx.util.Pair;
import com.usebilbo.vertx.util.Utils;

public class LineProcessor {
    private static final Pattern VAR_PATTERN = Pattern.compile("(.+)=(.+)");
    private static final Pattern SECTION_PATTERN = Pattern.compile("\\[(.+)\\]");
    private static final Pattern SUB_PATTERN = Pattern.compile("(\\$\\{.+\\})");
    private static final String COMMENT_CHARS = ";#*";

    private Map<String, List<String>> collected = new LinkedHashMap<>();
    private String currentPrefix = "";
    private Properties properties;

    public LineProcessor(Properties properties) {
        this.properties = new Properties(properties);
    }

    public void process(String line) {
        String text = strip(line);

        if (text.isEmpty()) {
            return;
        }

        if (text.startsWith("[")) {
            currentPrefix = Utils.coalesce(parseSection(text), currentPrefix);
        } else {
            tryParseVariable(text);
        }
    }

    private void tryParseVariable(String text) {
        Pair<String, String> var = parseVariable(currentPrefix, text, properties);

        if (var == null) {
            return;
        }
        
        collected.computeIfAbsent(var.left(), k -> new ArrayList<>()).add(var.right());
        properties.put(var.left(), var.right());
    }

    protected static Pair<String, String> parseVariable(String prefix, String text, Properties props) {
        Matcher matcher = VAR_PATTERN.matcher(text);
        if (!matcher.find()) {
            return null;
        }
        return Pair.of(merge(prefix, matcher.group(1).trim()), substitute(matcher.group(2).trim(), props));
    }

    protected static String substitute(String source, Properties props) {
        Matcher matcher = SUB_PATTERN.matcher(source);

        if (!matcher.find()) {
            return source;
        }

        StringBuffer res = new StringBuffer();

        for (int i = 1; i <= matcher.groupCount(); i++) {
            String var = matcher.group(i);
            var = var.substring(2, var.length() - 1);
            matcher.appendReplacement(res, Matcher.quoteReplacement(props.getProperty(var, "")));
        }
        matcher.appendTail(res);

        return res.toString().trim();
    }

    private static String merge(String prefix, String text) {
        return prefix.isEmpty() ? text : prefix + "." + text;
    }

    protected static String parseSection(String text) {
        Matcher matcher = SECTION_PATTERN.matcher(text);
        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1).trim();
    }

    private String strip(String line) {
        String text = line.trim();
        if (text.isEmpty()) {
            return text;
        }
        return COMMENT_CHARS.indexOf(text.charAt(0)) == -1 ? text : "";
    }

    public Map<String, List<String>> collected() {
        return collected;
    }

    public void resetSection() {
        currentPrefix = "";
    }
}
