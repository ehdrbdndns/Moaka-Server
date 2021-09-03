package com.moaka.service;

import com.moaka.dto.Archive;
import com.moaka.dto.Chunk;
import com.moaka.dto.Section;
import com.moaka.mapper.ArchiveMapper;
import com.moaka.mapper.BookmarkMapper;
import com.moaka.mapper.SectionMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Service
@Transactional
public class UserService {
    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    SectionMapper sectionMapper;

    public JSONArray retrieveDirectory(int user_no) throws Exception {
        JSONArray directoryList = new JSONArray();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveFromUserNo(user_no);
        for(int i = 0; i < archiveList.size(); i++) {
            JSONObject directoryInfo = new JSONObject();
            Archive archive = archiveList.get(i);
            ArrayList<Section> sectionList = sectionMapper.retrieveSectionByArchiveNo(archive.getNo());

            if (sectionList.size() != 0) {
                directoryInfo.put("archive_no", archive.getNo());
                directoryInfo.put("archive_title", archive.getTitle());
                JSONArray sectionArray = new JSONArray();
                for (int j = 0; j < sectionList.size(); j++) {
                    JSONObject sectionInfo = new JSONObject();
                    sectionInfo.put("no", sectionList.get(j).getNo());
                    sectionInfo.put("title", sectionList.get(j).getTitle());
                    sectionArray.put(sectionInfo);
                }
                directoryInfo.put("section_list", sectionArray);
                directoryList.put(directoryInfo);
            }
        }
        return directoryList;
    }
}
