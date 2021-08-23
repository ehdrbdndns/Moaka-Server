package com.moaka.service;

import com.moaka.dto.Chunk;
import com.moaka.dto.Section;
import com.moaka.dto.Tag;
import com.moaka.mapper.BookmarkMapper;
import com.moaka.mapper.SectionMapper;
import com.moaka.mapper.TagMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@Transactional
public class SectionService {
    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    TagMapper tagMapper;

    public void insertSection(Section section) throws Exception {
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
