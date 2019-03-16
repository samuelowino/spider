package com.wikedapps.spider.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SpiderNet {

    private List<String> requestUrlsList = new ArrayList<>();

    public SpiderNet(List<String> requestUrlsList) {
        this.requestUrlsList = requestUrlsList;
    }

    public static void main(String arge[]) {
        try {
            printData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Temporary logger
     * purge after use.
     */
    public static void printData() throws IOException, URISyntaxException {
        CloseableHttpResponse httpResponse = executeHttpRequest("url");
        byte[] data = decodeHttpsResponseContent(httpResponse);
        //String stringContent = SpiderFileUtils.decodeByteArrayToString(data);
        System.err.print(new String(data, StandardCharsets.UTF_8));
    }

    public static byte[] decodeHttpsResponseContent(CloseableHttpResponse httpResponse) throws IOException {

        HttpEntity httpEntity = httpResponse.getEntity();

        System.out.println("Encoding : " + httpEntity.getContentEncoding());
        System.out.println("Content type: " + httpEntity.getContentType());
        System.out.println("Content Length " + httpEntity.getContentLength());

        InputStream inputStream = httpEntity.getContent();
        byte[] data = new byte[inputStream.available() + 1024];

        do {
            inputStream.read(data);
        } while (inputStream.available() > 0);

        inputStream.close();

        return data;
    }

    public static CloseableHttpResponse executeHttpRequest(String url) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGetRequest = new HttpGet();
        httpGetRequest.setHeader(HttpHeaders.CONTENT_TYPE,"text/plain");
        httpGetRequest.setHeader(HttpHeaders.ACCEPT,"text/plain");
        httpGetRequest.setHeader(HttpHeaders.ACCEPT_LANGUAGE,"en");
        httpGetRequest.setHeader(HttpHeaders.HOST,"en.wikipedia.org");
        httpGetRequest.setURI(new URI("https://en.wikipedia.org/wiki/Biochemistry.html"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest);
        return httpResponse;
    }
}
