package com.example.scrapper.scanner;

import com.example.scrapper.file.File;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileScanner {
    private final String url;

    public FileScanner(String url) {
        this.url = url;
    }

    public File getFile() throws IOException {
        Scanner scanner = new Scanner(url);
        List<String> found = scanner.lookFor(Arrays.asList("(([0-9]+) lines)", "(([0-9]+) Bytes)", "(([0-9]+) KB)"));

        return new File(found, url);
    }
}
