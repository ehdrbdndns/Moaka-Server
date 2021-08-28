package com.moaka.service;

import com.moaka.dto.Chunk;
import com.moaka.dto.Section;
import com.moaka.dto.Tag;
import com.moaka.mapper.BookmarkMapper;
import com.moaka.mapper.ChunkMapper;
import com.moaka.mapper.SectionMapper;
import com.moaka.mapper.TagMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Service
@Transactional
public class SectionService {
    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ChunkMapper chunkMapper;

    public void updateSection(Section params) throws Exception {
        sectionMapper.updateSection(params);
        tagMapper.deleteTagBySectionNo(params.getNo());
        String today = getToday();
        for(int i = 0; i < params.getTag_list().size(); i++) {
            Tag tag = new Tag();
            tag.setTag(params.getTag_list().get(i));
            tag.setRegdate(today);
            tag.setSection_no(params.getNo());
            tag.setStore_type("section");

            tagMapper.insertSectionTag(tag);
        }
    }

    public JSONArray retrieveSectionByArchiveNo(int archive_no) throws Exception {
        JSONArray result = new JSONArray();
        ArrayList<Section> sectionList = sectionMapper.retrieveSectionByArchiveNo(archive_no);
        for(int i = 0; i < sectionList.size(); i++) {
            JSONObject sectionInfo = new JSONObject();
            Section section = sectionList.get(i);
            ArrayList<String> tagList = tagMapper.retrieveTagItemBySectionNo(section.getNo());
            ArrayList<Chunk> chunkList = chunkMapper.retrieveChunkBySectionNo(section.getNo());

            // TODO JSON 데이터 생성
            sectionInfo.put("no", section.getNo());
            sectionInfo.put("title", section.getTitle());
            sectionInfo.put("archive_no", section.getArchive_no());
            sectionInfo.put("description", section.getDescription());
            sectionInfo.put("regdate", section.getRegdate());
            sectionInfo.put("tag_list", tagList);
            sectionInfo.put("chunk_list", chunkList);
            // TODO 주후 chunk 리스트 데이터를 가지고 오는 로직도 추가해야 함
            // sectionInfo.put("chunkList", chunkList);
            result.put(sectionInfo);
        }
        return result;
    }

    public JSONObject insertSection(Section section) throws Exception {
        JSONObject result = new JSONObject();
        String today = getToday();
        section.setRegdate(today);
        sectionMapper.insertSection(section);
        for(int i = 0; i < section.getTag_list().size(); i++) {
            Tag tag = new Tag();
            tag.setTag(section.getTag_list().get(i));
            tag.setRegdate(today);
            tag.setSection_no(section.getNo());
            tag.setStore_type("section");

            tagMapper.insertSectionTag(tag);
        }
        result.put("section_no", section.getNo());
        return result;
    }

    public void deleteSection(int no) throws Exception {
        sectionMapper.deleteSection(no);
    }

    public String getToday(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
