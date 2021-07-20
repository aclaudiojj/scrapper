package com.example.scrapper.file;

public class FileAgg {
    private String extension;
    private int lines;
    private float bytes;
    private int count;

    public FileAgg(File file) {
        this.extension = file.getExtension();
        this.lines = file.getLines();
        this.bytes = file.getBytes();
        this.count = 1;
    }

    public void increaseLines(int lines) {
        this.lines += lines;
    }

    public void increaseBytes(float bytes) {
        this.bytes += bytes;
    }

    public void increaseCount() {
        this.count++;
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

    public int getCount() {
        return count;
    }
}
