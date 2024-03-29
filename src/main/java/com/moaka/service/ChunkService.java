package com.moaka.service;

import com.moaka.dto.Chunk;
import com.moaka.dto.Room;
import com.moaka.mapper.ChunkMapper;
import com.moaka.mapper.RoomMapper;
import com.moaka.mapper.TagMapper;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

@Service
@Transactional
public class ChunkService {
    @Autowired
    ChunkMapper chunkMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    RoomMapper roomMapper;

    public JSONObject insertChunk(Chunk chunk) throws Exception {
        JSONObject result = new JSONObject();
        chunk.setRegdate(getToday());

        // 청크 생성
        chunkMapper.insertChunk(chunk);

        // 청크의 채팅 창 생성
        Room room = new Room();
        room.setChunk_no(chunk.getNo());
        room.setRoom_id(UUID.randomUUID().toString());
        room.setRegdate(getToday());
        roomMapper.insertRoom(room);

        result.put("isSuccess", true);
        result.put("no", chunk.getNo());

        return result;
    }

    public boolean updateChunk(Chunk params) throws Exception {
        chunkMapper.updateChunk(params);
        return true;
    }

    public boolean updateChunkFromChrome(Chunk params) throws Exception {
        if (chunkMapper.isAuthorityOfUpdateChunk(params.getNo(), params.getUser_no())) {
            chunkMapper.updateChunkFromChrome(params.getDescription(), params.getSection_no(), params.getNo());
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteChunk(Chunk params) throws Exception {
        if (chunkMapper.isAuthorityOfDeleteChunk(params)) {
            chunkMapper.deleteChunk(params);
            return true;
        } else {
            return false;
        }
    }

    public JSONObject linkPreview(String url) throws Exception {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }

        Document document = Jsoup.connect(url).get();

        URI uri = new URI(url);
        String domain = uri.getHost();

        String link = url;
        String description = "";
        String thumbnail = "";
        String favicon = "";

        if (!domain.startsWith("www")) {
            domain = domain;
        }

        description = getMetaTagContent(document, "meta[name=description]");
        if (description.equals("")) {
            description = getMetaTagContent(document, "meta[property=og:description]");
        }

        thumbnail = getMetaTagContent(document, "meta[property=og:image]");
        if (thumbnail.equals("")) {
            thumbnail = getMetaTagContent(document, "meta[property=twitter:image]");
            if (thumbnail.equals("")) {
                thumbnail = getMetaTagSrc(document, "img");
            }
        }
        if (!thumbnail.startsWith("http")) {
            thumbnail = "http://" + domain + thumbnail;
        }

        Element faviconElem = document.head().select("link[href~=.*\\.(ico|png)]").first();
        if (faviconElem != null) {
            favicon = faviconElem.attr("href");
        } else if (document.head().select("meta[itemprop=image]").first() != null) {
            favicon = document.head().select("meta[itemprop=image]").first().attr("content");
        }

        if (!favicon.startsWith("http")) {
            if (domain.startsWith("www")) {
                favicon = "http://" + domain + favicon;
            } else {
                favicon = "http://www." + domain + favicon;
            }
        }

        JSONObject result = new JSONObject();
        result.put("domain", domain);
        result.put("link", link);
        result.put("description", description);
        result.put("thumbnail", thumbnail);
        result.put("favicon", favicon);

        return result;
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

    /**
     * Returns the given meta tag src
     *
     * @param document
     * @return
     */
    private String getMetaTagSrc(Document document, String cssQuery) {
        Element elm = document.select(cssQuery).first();
        if (elm != null) {
            return elm.attr("src");
        }
        return "";
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
