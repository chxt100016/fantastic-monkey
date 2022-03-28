package com.chxt.fantasticmonkey.bean.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpExecuteDTO {
    private String uri;
    private List<NameValuePair> params;
    private String message;
    private Object entity;
    private String path;

    private HttpRequestBase httpRequest;
}
