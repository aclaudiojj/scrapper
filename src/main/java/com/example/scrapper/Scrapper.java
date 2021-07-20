package com.example.scrapper;

import com.example.scrapper.file.File;
import com.example.scrapper.file.FileAgg;
import com.example.scrapper.file.FilesInfo;
import com.example.scrapper.scanner.DirectoryScanner;
import com.example.scrapper.scanner.FileScanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Scrapper {
    private final String initialUrl;

    private final List<String> folders = new ArrayList<>();

    private final List<File> files = new ArrayList<>();

    public Scrapper(String initialUrl) {
        this.initialUrl = initialUrl;
        this.folders.add(initialUrl);
    }

    public Collection<FileAgg> getFilesInfo() {
        return (new FilesInfo(files)).getFiles();
    }

    public void scan(String url) throws IOException {
        DirectoryScanner directoryScanner = new DirectoryScanner(url);

        this.loadFiles(directoryScanner.getFilesAndFolders(), url);
    }

    public void scan() throws IOException {
        DirectoryScanner directoryScanner = new DirectoryScanner(this.initialUrl);

        this.loadFiles(directoryScanner.getFilesAndFolders(), this.initialUrl);
    }

    public void loadFiles(List<String> filesAndFolders, String url) {
        filesAndFolders.forEach(element -> findLinkAndCheckContent(element, url));
    }

    private void findLinkAndCheckContent(String line, String url) {
        String needle = "href=\"";
        int init = line.indexOf(needle);

        if (init != -1) {
            int end = line.indexOf("\"", init + needle.length());

            try {
                this.checkFileAndFolder(line.substring(init + needle.length(), end), url);
            } catch (IOException e) {
                throw new RuntimeException("Could not find link path");
            }
        }
    }

    private void checkFileAndFolder(String link, String url) throws IOException {
        if (isFolder(link)) {
            if (shouldScanFolder(link, url)) {
                this.folders.add(link);
                this.scan(link);
            }

            return;
        }

        if (isFile(link)) {
            this.files.add(getFileInfo(link));
        }
    }

    private boolean shouldScanFolder(String link, String url) {
        return ! link.equals(url) && link.startsWith(this.initialUrl) && ! this.folders.contains(link);
    }

    private boolean isFolder(String link) {
        return link.contains("tree");
    }

    private boolean isFile(String link) {
        return link.contains("blob");
    }

    private File getFileInfo(String url) throws IOException {
        return (new FileScanner(url)).getFile();
    }
}
