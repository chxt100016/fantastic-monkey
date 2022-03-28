package com.chxt.fantasticmonkey.controller.text;

import com.chxt.fantasticmonkey.bean.text.Text;
import com.chxt.fantasticmonkey.service.text.TextService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TextController {

    @Resource
    private TextService textService;

    @GetMapping("/text/{prefix}/{filename}")
    public String get(@PathVariable String prefix, @PathVariable String filename) {
        return this.textService.getText(prefix, filename, true);
    }

    @GetMapping("/text/forEdit/{prefix}/{filename}")
    public String getForEdit(@PathVariable String prefix, @PathVariable String filename) {
        return this.textService.getText(prefix, filename, false);
    }

    @PutMapping("/text/{prefix}/{filename}")
    public void update(@PathVariable String prefix, @PathVariable String filename, @RequestBody String data) {
        this.textService.updateText(Text.builder().prefix(prefix).filename(filename).data(data).build());
    }

    @PostMapping("/text")
    public void create(Text text) {
        this.textService.createText(text);
    }
}
