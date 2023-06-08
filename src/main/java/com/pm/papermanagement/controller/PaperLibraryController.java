package com.pm.papermanagement.controller;

import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.entity.PaperLibrary;
import com.pm.papermanagement.common.exception.BizException;
import com.pm.papermanagement.common.model.NullObject;
import com.pm.papermanagement.common.model.PaperLibraries;
import com.pm.papermanagement.common.model.ReturnValue;
import com.pm.papermanagement.common.model.param.PaperLibraryParam;
import com.pm.papermanagement.common.model.param.PaperParam;
import com.pm.papermanagement.common.model.param.PaperParamOutput;
import com.pm.papermanagement.common.model.param.TopicParam;
import com.pm.papermanagement.mapper.PaperLibraryMapper;
import com.pm.papermanagement.mapper.PaperMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class PaperLibraryController {
    PaperLibraryMapper paperLibraryMapper;
    //PaperMapper paperMapper;
    final static List<String> booleanString = new ArrayList<>(Arrays.asList("true", "false"));

    @Autowired
    public void setPaperLibraryMapper(PaperLibraryMapper paperLibraryMapper) {
        this.paperLibraryMapper = paperLibraryMapper;
    }

    /*@Autowired
    public void setPaperMapper(PaperMapper paperMapper){
        this.paperMapper = paperMapper;
    }*/

    @PostMapping(value = "/api/library", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue addPaperLibrary(@RequestBody PaperLibraryParam paperLibraryParam) {
        if (ObjectUtils.isEmpty(paperLibraryParam.getTopic())) {
            throw new BizException("topic不能为空");
        }
        if (!booleanString.contains(paperLibraryParam.getIs_public())) {
            throw new BizException("传递参数格式错误");
        }
        System.out.println(paperLibraryParam);
        paperLibraryMapper.addPaperLibrary(paperLibraryParam);
        PaperLibrary paperLibrary = paperLibraryMapper.selectPaperLibraryByTopic(paperLibraryParam.getTopic());
        return ReturnValue.generateSuccess(paperLibrary);
    }

    @GetMapping("/api/library")
    public ReturnValue requestPaperLibrary(@RequestParam(value = "page_num") int page_num, @RequestParam(value = "page_size") int page_size, @RequestParam(value = "topic",required = false) String topic){
        if(page_num < 0 || page_size < 0){
            throw new BizException("参数不能为负数");
        }
        List<PaperLibrary> paperLibraries;
        if(topic != null){
            paperLibraries = paperLibraryMapper.selectPaperLibraryBySequenceAndTopic(page_num * page_size, page_size, topic);
        }else{
            paperLibraries = paperLibraryMapper.selectPaperLibraryBySequence(page_num * page_size, page_size);
        }
        return ReturnValue.generateSuccess(new PaperLibraries(paperLibraries));
    }

    @RequestMapping(value = "/api/library/{library_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue updateLibrary(@PathVariable String library_id, @RequestBody TopicParam topicParam) {
        // todo:
        paperLibraryMapper.modifyTopicById(Integer.parseInt(library_id),topicParam.getTopic());
        PaperLibrary paperLibrary = paperLibraryMapper.selectPaperLibraryById(Integer.parseInt(library_id));
        return ReturnValue.generateSuccess(paperLibrary);
    }

    @RequestMapping(value = "/api/library/{library_id}", method = RequestMethod.DELETE)
    public ReturnValue deleteLibrary(@PathVariable String library_id){
        paperLibraryMapper.deletePaperLibraryById(Integer.parseInt(library_id));
        PaperLibrary paperLibrary = paperLibraryMapper.selectPaperLibraryById(Integer.parseInt(library_id));
        return ReturnValue.generateSuccess(paperLibrary);
    }


    /*@PostMapping(value = "/api/paper", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ReturnValue addPaper(@RequestBody PaperParam paperParam){
        paperMapper.addPaper(paperParam);
        Paper paper = paperMapper.selectPaperByTitle(paperParam.getTitle());
        return ReturnValue.generateSuccess(new PaperParamOutput(paper));
    }*/

}
