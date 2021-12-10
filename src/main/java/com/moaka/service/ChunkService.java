package com.moaka.service;

import com.moaka.dto.Chunk;
import com.moaka.dto.Comment;
import com.moaka.dto.Tag;
import com.moaka.mapper.BookmarkMapper;
import com.moaka.mapper.ChunkMapper;
import com.moaka.mapper.CommentMapper;
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

@Service
@Transactional
public class ChunkService {
    @Autowired
    ChunkMapper chunkMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    CommentMapper commentMapper;

    public JSONObject insertChunk(Chunk chunk) throws Exception {
        JSONObject result = new JSONObject();
        // TODO archive가 공개인지 체크
        if (chunkMapper.isAuthorityOfInsertChunk(chunk.getSection_no(), chunk.getUser_no())) {
            chunk.setRegdate(getToday());
            chunkMapper.insertChunk(chunk);
            chunkMapper.updateGroupNumOfChunk(chunk.getNo());

            for (int i = 0; i < chunk.getTag_list().size(); i++) {
                Tag tag = new Tag();
                tag.setChunk_no(chunk.getNo());
                tag.setTag(chunk.getTag_list().get(i));
                tag.setRegdate(chunk.getRegdate());
                tagMapper.insertChunkTag(tag);
            }
            result.put("isSuccess", true);
            result.put("no", chunk.getNo());
            result.put("regdate", chunk.getRegdate());
        } else {
            result.put("isSuccess", false);
        }

        return result;
    }

    public JSONObject insertRelativeChunk(Chunk params) throws Exception {
        JSONObject result = new JSONObject();
        if (chunkMapper.isAuthorityOfInsertChunk(params.getSection_no(), params.getUser_no())) {
            params.setRegdate(getToday());

            // content_order 구하기
            int content_order = chunkMapper.selectRelativeChunkNumber(params.getGroup_num()) + 1;
            params.setContent_order(content_order);

            chunkMapper.insertRelativeChunk(params);

            result.put("isSuccess", true);
            result.put("no", params.getNo());
            result.put("regdate", params.getRegdate());
        } else {
            result.put("isSuccess", false);
        }

        return result;
    }

    public JSONObject retrieveChunkOfBookmarkByUserNo(int user_no) {
        JSONObject result = new JSONObject();
        ArrayList<Chunk> chunkList = chunkMapper.retrieveChunkOfBookmarkByUserNo(user_no);
        for(int i = 0; i < chunkList.size(); i++) {
            // TODO 태그 리스트
            ArrayList<String> chunkTagList = tagMapper.retrieveChunkTagByChunkNo(chunkList.get(i).getNo());
            chunkList.get(i).setTag_list(chunkTagList);

            // TODO 댓글 리스트
            ArrayList<Comment> commentList = commentMapper.selectCommentOfChunk(chunkList.get(i).getNo());
            chunkList.get(i).setComment_list(commentList);

            // TODO 관련 청크 리스트
            ArrayList<Chunk> relativeChunkList = chunkMapper.retrieveRelativeChunkByGroupNum(chunkList.get(i).getNo());
            chunkList.get(i).setRelative_chunk_list(relativeChunkList);
        }
        result.put("isSuccess", true);
        result.put("chunk_list", chunkList);

        return result;
    }

    public boolean updateChunk(Chunk params) throws Exception {
        if (chunkMapper.isAuthorityOfUpdateChunk(params.getUser_no())) {
            chunkMapper.updateChunk(params);
            tagMapper.deleteChunkTagByChunkNo(params.getNo());
            for (int i = 0; i < params.getTag_list().size(); i++) {
                Tag tag = new Tag();
                tag.setChunk_no(params.getNo());
                tag.setTag(params.getTag_list().get(i));
                tag.setRegdate(getToday());
                tagMapper.insertChunkTag(tag);
            }
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

        System.out.println("url: " + url);
        Document document = Jsoup.connect(url).get();

        URI uri = new URI(url);
        String domain = uri.getHost();

        String title = "";
        String link = url;
        String description = "";
        String thumbnail = "";
        String favicon = "";

        title = document.title();
        if(title.equals("")) {
            title = getMetaTagContent(document, "meta[name=title]");
            if(title.equals("")) {
                title = getMetaTagContent(document, "meta[property=og:title]");
            }
        }

        description = getMetaTagContent(document, "meta[name=description]");
        if(description.equals("")) {
            description = getMetaTagContent(document, "meta[property=og:description]");
        }

        thumbnail = getMetaTagContent(document, "meta[property=og:image]");
        if(thumbnail.equals("")) {
            thumbnail = getMetaTagContent(document, "meta[property=twitter:image]");
            if(thumbnail.equals("")) {
                thumbnail = getMetaTagSrc(document, "img");
            }
        }
        if(!thumbnail.startsWith("http")) {
            thumbnail = "http://" + domain + thumbnail;
        }

        Element faviconElem = document.head().select("link[href~=.*\\.(ico|png)]").first();
        if(faviconElem != null) {
            favicon = faviconElem.attr("href");
        }
        else if(document.head().select("meta[itemprop=image]").first() != null) {
            favicon = document.head().select("meta[itemprop=image]").first().attr("content");
        }
        if(!favicon.startsWith("http")) {
            favicon = "http://" + domain + favicon;
        }

        JSONObject result = new JSONObject();
        result.put("title", title);
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
