package com.chxt.fantasticmonkey.service.text;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chxt.fantasticmonkey.bean.text.Text;
import com.chxt.fantasticmonkey.component.convert.ConvertChainManager;
import com.chxt.fantasticmonkey.dao.text.TextMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TextService {
    @Resource
    private TextMapper textMapper;

    @Resource
    private ConvertChainManager manager;

    public String getText(String prefix, String filename, boolean isConvert){
        LambdaQueryWrapper<Text> query = new QueryWrapper<Text>().lambda()
                .eq(Text::getPrefix, prefix)
                .eq(Text::getFilename, filename);
        Text text = this.textMapper.selectOne(query);
        String res = text == null ? "" : text.getData();
        if (isConvert) res = this.manager.keywordConvert(res);
        return res;
    }

    public void updateText(Text text){
        LambdaUpdateWrapper<Text> update = new UpdateWrapper<Text>().lambda()
                .eq(Text::getPrefix, text.getPrefix())
                .eq(Text::getFilename, text.getFilename());
        this.textMapper.update(text, update);
    }

    public void createText(Text text) {
        if (text.getData() == null) text.setData("");
        this.textMapper.insert(text);
    }
}
