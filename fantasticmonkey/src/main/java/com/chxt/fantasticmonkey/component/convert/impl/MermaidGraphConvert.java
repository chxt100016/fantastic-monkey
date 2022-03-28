package com.chxt.fantasticmonkey.component.convert.impl;

import com.chxt.fantasticmonkey.component.convert.KeywordConvert;
import org.springframework.stereotype.Component;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MermaidGraphConvert extends KeywordConvert {

    @Override
    protected String getPattern() {
        return "```mermaid fantastic-monkey([\\s\\S]*)```";
    }

    @Override
    protected String getParam(Matcher matcher) {
        return matcher.group(1);
    }

    @Override
    public String getDest(String param) {
        String[] lineList = param.split("\n");
        Stack<String> stack = new Stack<>();
        int counter = 0;
        int nowLevel = -1;

        StringBuilder res = new StringBuilder();
        StringBuilder click = new StringBuilder();
        res.append("```mermaid\n");
        for (String line : lineList) {
            if (line.length() == 0 || '#' != line.charAt(0)) {
                res.append(line);
                continue;
            }

            String item = null;
            String pattern = "\\[(.*)].*\\((.*)\\)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(line);
            if (m.find()) {
                item = String.format("%s[\"%s\"]", counter, m.group(1));
                if (m.group(2).length() > 0) {
                    click.append(String.format("click %s \"%s\"\n", counter, m.group(2)));
                }
            } else {
                continue;
            }

            int level = this.getLevel(line);
            if (nowLevel == -1) {
                nowLevel = level;
                stack.push(item);
            } else if (nowLevel == level) {
                String pop = stack.pop();
                if (stack.size() == 0) {
                    res.append(pop);
                    stack.push(item);
                } else {
                    String peek = stack.peek();
                    res.append(String.format("%s --> %s", peek, item));
                    stack.push(item);
                }
            } else if (nowLevel < level) {
                String pop = stack.peek();
                res.append(String.format("%s --> %s", pop, item));
                stack.push(item);
                nowLevel = level;
            } else if (nowLevel > level) {
                stack.pop();
                stack.pop();
                stack.push(item);
            }

            counter ++;
            res.append("\n");
        }

        if (stack.size() == 2) {
            String to = stack.pop();
            String from = stack.pop();
            res.append(String.format("%s --> %s", from, to));
            res.append("\n");
        }
        res.append(click);
        res.append("```");

        return res.toString();
    }

    private int getLevel(String line) {
        int counter = 0;
        while (line.charAt(counter) == '#') {
            counter ++;
        }
        return counter;
    }
}
