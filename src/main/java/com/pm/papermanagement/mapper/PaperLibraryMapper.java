package com.pm.papermanagement.mapper;

import com.pm.papermanagement.common.entity.PaperLibrary;
import com.pm.papermanagement.common.model.param.PaperLibraryParam;
import com.pm.papermanagement.common.model.param.TopicParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperLibraryMapper {
    int addPaperLibrary(PaperLibraryParam paperLibrary);

    PaperLibrary selectPaperLibraryByTopic(String topic);

    PaperLibrary selectPaperLibraryById(int library_id);

    List<PaperLibrary> selectPaperLibraryBySequence(int start, int offset);

    List<PaperLibrary> selectPaperLibraryBySequenceAndTopic(int start, int offset, String topic);

    int modifyTopicById(int library_id, String topic);

    int deletePaperLibraryById(int library_id);


}
