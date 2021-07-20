package com.example.scrapper.file;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class File {
    private String extension;
    private int lines;
    private float bytes;

    public File(List<String> matches, String filename) {
        AtomicInteger lines = new AtomicInteger();
        AtomicReference<Float> bytes = new AtomicReference<>((float) 0);

        matches.forEach(element -> {
            if (lines.get() == 0) {
                lines.set(lines(element));
            }

            if (bytes.get() == 0) {
                bytes.set(bytes(element));
            }
        });

        this.bytes = bytes.get();
        this.lines = lines.get();
        this.extension = extension(filename).orElse("");
    }

    public String getExtension() {
        return extension;
    }

    public int getLines() {
        return lines;
    }

    public float getBytes() {
        return bytes;
    }

    private Optional<String> extension(String file) {
        return Optional.ofNullable(file)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(file.lastIndexOf(".") + 1));
    }

    private int lines(String match) {
        if (! match.contains("lines")) {
            return 0;
        }

        return Integer.parseInt(match.substring(0, match.indexOf("lines")).trim());
    }

    private float bytes(String match) {
        if (match.contains("Bytes")) {
            return Float.parseFloat(match.replaceAll("\\D+",""));
        }

        if (match.contains("KB")) {
            Pattern pattern = Pattern.compile("([0-9.]+)");
            Matcher matcher = pattern.matcher(match);

            if (! matcher.find()) {
                return 0;
            }

            return Float.parseFloat(matcher.group(0)) * 1024;
        }

        return 0;
    }
}
