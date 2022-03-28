package com.chxt.fantasticmonkey.component.convert.impl;

import com.chxt.fantasticmonkey.bean.openDota.OpenDotaWinLose;
import com.chxt.fantasticmonkey.component.convert.KeywordConvert;
import com.chxt.fantasticmonkey.dependency.openDota.PlayerClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Matcher;

@Component
public class DotaWinLoseConvert extends KeywordConvert {

    @Resource
    private PlayerClient playerClient;

    @Override
    protected String getPattern() {
        return "\\{\\{(dota.wl):(.*)}}";
    }

    @Override
    public String getParam(Matcher matcher) {
        return matcher.group(2);
    }


    @Override
    public String getDest(String param) {
        OpenDotaWinLose wl = this.playerClient.getWL(param, "5");
        String status = "";
        if (wl.getWin() != null) {
            switch (wl.getWin().compareTo(wl.getLose())){
                case 1:
                    status = String.format("净胜%s分, 太棒了涨分了！！！", (wl.getWin() - wl.getLose()) * 30);
                    break;
                case 0:
                    if (wl.getWin() != 0) {
                        status = "居然平了, 幸幸苦苦半天, 白打！！";
                    }
                    break;
                case -1:
                    status = String.format("净负%s分, 这个游戏也太难了吧！", (wl.getLose() - wl.getWin()) * 30);
                    break;
            }
        }

        return String.format("胜%s场 : 负%s场  %s", wl.getWin(), wl.getLose(), status);
    }
}
