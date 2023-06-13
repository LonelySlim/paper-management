package com.pm.papermanagement.mapper;

import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.entity.PaperLibrary;
import com.pm.papermanagement.common.model.param.PaperParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperMapper {

    int addPaper(int library_id,String title,String owner);

    Paper selectPaperByTitle(String title);

    List<Paper> selectPaperBySequence(int library_id, int start, int offset);

    List<Paper> selectPaperBySequenceAndTitle(int library_id, int start, int offset, String title);

    void modifyTitleById(int paper_id, String title);

    void modifyAuthorsById(int paper_id,String authors);

    void modifyPublisherById(int paper_id,String publisher);

    void modifyYearById(int paper_id,int year);

    void deletePaperById(int paper_id);

    Paper selectPaperById(int paper_id);

    void releaseSourceByID(String source, int paper_id);

    int getPaperNum(int library_id);
}
