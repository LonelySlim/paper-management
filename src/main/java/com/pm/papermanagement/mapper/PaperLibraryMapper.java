package com.pm.papermanagement.mapper;

import com.pm.papermanagement.common.entity.PaperLibrary;
import com.pm.papermanagement.common.model.param.PaperLibraryParam;
import com.pm.papermanagement.common.model.param.TopicParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperLibraryMapper {
    int addPaperLibrary(String topic, String desc, String is_public, String owner);

    PaperLibrary selectPaperLibraryByTopic(String topic);

    PaperLibrary selectPaperLibraryById(int library_id);

    List<PaperLibrary> selectPaperLibraryBySequence(int start, int offset, String owner);

    List<PaperLibrary> selectPaperLibraryBySequenceAndTopic(int start, int offset, String topic,String owner);

    int modifyTopicById(int library_id, String topic);

    int modifyDescById(int library_id, String desc);

    int modifyIsPublicById(int library_id, String is_public);

    int deletePaperLibraryById(int library_id);

    int getLibraryNum(String owner);


}
