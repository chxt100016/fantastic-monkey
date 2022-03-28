package com.chxt.fantasticmonkey.dependency;

import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.http.HttpExecuteDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Slf4j
public abstract class MyHttpClient {
    @Resource
    private HttpClient httpClient;

    protected String doGet(HttpExecuteDTO dto) {
        dto.setHttpRequest(new HttpGet());
        return this.doExecute(dto);
    }
    protected String doPost(HttpExecuteDTO dto) {
        HttpPost httpPost = new HttpPost();
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(JSON.toJSONString(dto.getEntity()), "UTF-8"));
        dto.setHttpRequest(httpPost);
        return this.doExecute(dto);

    }

    protected void download(HttpExecuteDTO dto) {
        InputStream is = null;
        try {
            HttpGet httpGet = new HttpGet(dto.getUri());
            HttpResponse res = this.httpClient.execute(httpGet);
            is = res.getEntity().getContent();
            FileUtils.copyInputStreamToFile(is, new File(dto.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String doExecute(HttpExecuteDTO dto) {
        try {
            URIBuilder uriBuilder = new URIBuilder(dto.getUri());
            uriBuilder.setParameters(dto.getParams());
            URI uri = uriBuilder.build();
            dto.getHttpRequest().setURI(uri);
            log.info("开始请求{}, uri:{}, entity:{}", dto.getMessage(), uri, JSON.toJSONString(dto.getEntity()));
            HttpResponse res = this.httpClient.execute(dto.getHttpRequest());
            return res.getEntity() == null ? "" : EntityUtils.toString(res.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发送http请求失败");
        }
    }
}
