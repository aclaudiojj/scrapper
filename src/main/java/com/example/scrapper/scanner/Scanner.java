package com.example.scrapper.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    private final static String GITHUB = "https://github.com";

    private final String url;

    public Scanner(String url) {
        this.url = url;
    }

    public List<String> lookFor(List<String> needles) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        (new URL(GITHUB + url)).openStream()
                )
        );

        String inputLine;

        AtomicBoolean found = new AtomicBoolean(false);

        List<String> info = new ArrayList<>();

        while ((inputLine = bufferedReader.readLine()) != null) {
            String finalInputLine = inputLine;

            needles.forEach(needle -> {
                Pattern p = Pattern.compile(needle);
                Matcher m = p.matcher(finalInputLine);

                if (m.find() && ! finalInputLine.contains("Permalink")) {
                    info.add(finalInputLine);
                    found.set(true);
                }
            });
        }

        bufferedReader.close();

        return info;
    }

    public List<String> lookFor(String needle) throws IOException {
        List<String> needles = new ArrayList<>();

        needles.add(needle);

        return lookFor(needles);
    }
}
