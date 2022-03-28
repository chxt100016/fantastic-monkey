package com.chxt.fantasticmonkey.component.convert;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class KeywordConvert {

    protected abstract String getPattern();

    protected abstract String getParam(Matcher matcher);

    public abstract String getDest(String param);

    private Matcher getSrc(String data) {
        Pattern compile = Pattern.compile(this.getPattern());
        Matcher matcher = compile.matcher(data);
        if (matcher.find()) {
            return matcher;
        } else {
            return null;
        }
    }

    public final boolean preCheck(String data) {
        return this.getSrc(data) != null;
    }

    public final String convert(String data) {
        Matcher src = this.getSrc(data);
        while (src != null) {
            String param = this.getParam(src);
            data = src.replaceFirst(this.getDest(param));
            src = this.getSrc(data);
        }
        return data;
    }

    public static void main( String[] args ){

        // 按指定模式在字符串查找
        String line = "# JAVA基础\n" +
                "\n" +
                "```mermaid fantastic-monkey\n" +
                "graph LR\n" +
                "# JVM\n" +
                "## [Java虚拟机（JVM）你只要看这一篇就够了！](https://blog.csdn.net/qq_41701956/article/details/81664921)\n" +
                "## [JVM性能调优的6大步骤，及关键调优参数详解](https://blog.csdn.net/u013030086/article/details/85068766)\n" +
                "## [Java类加载机制](https://blog.csdn.net/weixin_40236948/article/details/88072698)\n" +
                "## [Full GC触发条件](https://blog.csdn.net/Hollake/article/details/90484027)\n" +
                "## OutOfMemoryError\n" +
                "### [几种常见的内存溢出与解决办法](https://www.cnblogs.com/leasonWang/p/11146030.html)\n" +
                "### [Javaheapspace]\n" +
                "### [Directbuffermemory](https://blog.csdn.net/baidu_37313657/article/details/105054757)\n" +
                "### [GCoverheadlimitexceeded](https://blog.csdn.net/qq_40162735/article/details/81775828)\n" +
                "### [Metaspace]()\n" +
                "### [cpu使用率过高问题排查](https://blog.csdn.net/qq_34944965/article/details/81107419)\n" +
                "\n" +
                "```";
        String pattern = "```mermaid fantastic-monkey([\\s\\S]*)```";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
//            System.out.println("Found value: " + m.group(0) );
            System.out.println("    Found value: " + m.group(1) );
        } else {
            System.out.println("NO MATCH");
        }


    }
}
