package com.moaka.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class LinkController {
    @ApiOperation(value = "링크 미리보기", notes = "웹사이트 url을 이용하여 웹페이지를 조회합니다.")
    @PostMapping(value = "/linkPreview", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object linkPreview(
            @ApiParam(value = "url", required = true)
            @RequestParam(value = "url", required = true) String url) throws IOException {

        System.out.println(url);

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        Document document = Jsoup.connect(url).get();
        String title = getMetaTagContent(document, "meta[name=title]");
        String desc = getMetaTagContent(document, "meta[name=description]");
//        String ogUrl = StringUtils.defaultIfBlank(getMetaTagContent(document, "meta[property=og:url]"), url);
        String ogUrl = getMetaTagContent(document, "meta[property=og:url]");
        String ogTitle = getMetaTagContent(document, "meta[property=og:title]");
        String ogDesc = getMetaTagContent(document, "meta[property=og:description]");
        String ogImage = getMetaTagContent(document, "meta[property=og:image]");
        String ogImageAlt = getMetaTagContent(document, "meta[property=og:image:alt]");

        System.out.println("title: " + title);
        System.out.println("desc: " + desc);
        System.out.println("ogTitle: " + ogTitle);
        System.out.println("ogDesc: " + ogDesc);
        System.out.println("ogImage: " + ogImage);
        System.out.println("ogImageAlt: " + ogImageAlt);
        System.out.println("ogUrl: " + ogUrl);

        return true;
    }

    /**
     * Returns the given meta tag content
     *
     * @param document
     * @return
     */
    private String getMetaTagContent(Document document, String cssQuery) {
        Element elm = document.select(cssQuery).first();
        if (elm != null) {
            return elm.attr("content");
        }
        return "";
    }
}
