package com.example.scrapper.file;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FilesInfo {
    private final List<File> files;

    public FilesInfo(List<File> files) {
        this.files = files;
    }

    public Collection<FileAgg> getFiles() {
        HashMap<String, FileAgg> accFiles = new HashMap<>();

        files.forEach(file -> {
            FileAgg fileAggFound = accFiles.get(file.getExtension());

            if (fileAggFound != null) {
                fileAggFound.increaseBytes(file.getBytes());
                fileAggFound.increaseLines(file.getLines());
                fileAggFound.increaseCount();
            } else {
                accFiles.put(file.getExtension(), new FileAgg(file));
            }
        });

        return accFiles.values();
    }
}
