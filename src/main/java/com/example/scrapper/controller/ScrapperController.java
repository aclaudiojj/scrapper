package com.example.scrapper.controller;

import com.example.scrapper.Scrapper;
import com.example.scrapper.file.FileAgg;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.Collection;

@Controller
@RequestMapping("/api")
public class ScrapperController {

    @GetMapping
    @ResponseBody
    @Cacheable(value = "scrapper", key = "#projectPath")
    public ResponseEntity<Collection<FileAgg>> index(@RequestParam(required = false, name = "project") String projectPath) throws IOException {
        Scrapper scrapper = new Scrapper("/" + projectPath);
        scrapper.scan();

        return ResponseEntity.ok(scrapper.getFilesInfo());
    }
}
