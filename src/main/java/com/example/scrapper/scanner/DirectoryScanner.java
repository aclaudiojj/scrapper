package com.example.scrapper.scanner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DirectoryScanner {
    private static final String folderIdentifier = "<a.*href=.*\\/tree\\/.*";
    private static final String fileIdentifier = "/blob/";

    private final String url;

    public DirectoryScanner(String url) {
        this.url = url;
    }

    public List<String> getFilesAndFolders() throws IOException {
        Scanner scanner = new Scanner(url);

        return scanner.lookFor(Arrays.asList(fileIdentifier, folderIdentifier));
    }
}
