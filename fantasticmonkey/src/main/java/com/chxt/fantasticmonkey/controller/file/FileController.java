package com.chxt.fantasticmonkey.controller.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@RestController
public class FileController {

    @Value("${kanban.img_base_path}")
    private String kanbanPath;

    @GetMapping("/file/img")
    public void getFile(HttpServletResponse response, @RequestParam("path") String path) throws IOException {
        FileInputStream in = new FileInputStream(path);
        byte[] bytes = IOUtils.toByteArray(in);

        response.setContentType("image/png");
        response.getOutputStream().write(bytes);
    }

    @PostMapping("/file/img")
    public void createKanbanImg(@RequestParam("path") String path, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File dest = new File(path + "/" + fileName);
        file.transferTo(dest);

    }

    @PostMapping("/file/kanban")
    public String createKanbanImg(@RequestParam("file") MultipartFile file) throws IOException {
        String path = String.format("%s/%s_%s", this.kanbanPath, DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"), file.getOriginalFilename());
        File dest = new File(path);
        file.transferTo(dest);
        return path;

    }

    @GetMapping("/file/kanban")
    public void getKanbanFile(HttpServletResponse response, @RequestParam("path") String path) throws IOException {
        FileInputStream in = new FileInputStream(path);
        byte[] bytes = IOUtils.toByteArray(in);

        response.setContentType("image/png");
        response.getOutputStream().write(bytes);
    }

}
