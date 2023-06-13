package com.pm.papermanagement.controller;

import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.entity.PaperLibrary;
import com.pm.papermanagement.common.exception.BizException;
import com.pm.papermanagement.common.model.NullObject;
import com.pm.papermanagement.common.model.PaperLibraries;
import com.pm.papermanagement.common.model.ReturnValue;
import com.pm.papermanagement.common.model.param.*;
import com.pm.papermanagement.mapper.PaperLibraryMapper;
import com.pm.papermanagement.mapper.PaperMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
//@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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
//        if(!SecurityUtils.getSubject().isAuthenticated()){
//            return ReturnValue.generate("101","未登陆",null);
//        }
        if (ObjectUtils.isEmpty(paperLibraryParam.getTopic())) {
            throw new BizException("topic不能为空");
        }
        if (!booleanString.contains(paperLibraryParam.getIs_public())) {
            throw new BizException("传递参数格式错误");
        }
        System.out.println(paperLibraryParam);
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        paperLibraryMapper.addPaperLibrary(paperLibraryParam.getTopic(), paperLibraryParam.getDesc(), paperLibraryParam.getIs_public(), owner);
        PaperLibrary paperLibrary = paperLibraryMapper.selectPaperLibraryByTopic(paperLibraryParam.getTopic());
        return ReturnValue.generateSuccess(new PaperLibraryParamOutput(paperLibrary));
    }

    @GetMapping("/api/library")
    public ReturnValue requestPaperLibrary(@RequestParam(value = "page_num") int page_num, @RequestParam(value = "page_size") int page_size, @RequestParam(value = "topic", required = false) String topic) {
        page_num -= 1;
        if (page_num < 0 || page_size < 0) {
            throw new BizException("参数不能为负数");
        }
        List<PaperLibrary> paperLibraries;
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        if (topic != null) {
            paperLibraries = paperLibraryMapper.selectPaperLibraryBySequenceAndTopic(page_num * page_size, page_size, topic, owner);
        } else {
            paperLibraries = paperLibraryMapper.selectPaperLibraryBySequence(page_num * page_size, page_size, owner);
        }
        int libraryNum = paperLibraryMapper.getLibraryNum(owner);
        return ReturnValue.generateSuccess(new PaperLibraries(paperLibraries, libraryNum));
    }

    @RequestMapping(value = "/api/library/{library_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue updateLibrary(@PathVariable String library_id, @RequestBody TopicParam topicParam) {
        // todo:
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        PaperLibrary paperLibrary = paperLibraryMapper.selectPaperLibraryById(Integer.parseInt(library_id));
        if (!owner.equals(paperLibrary.getOwner())) {
            return ReturnValue.generate("800", "无权限", null);
        }
//        if (!ObjectUtils.isEmpty(topicParam.getIs_public()) && !booleanString.contains(topicParam.getIs_public())) {
//            throw new BizException("传递参数格式错误");
//        }
        if (!ObjectUtils.isEmpty(topicParam.getTopic()))
            paperLibraryMapper.modifyTopicById(Integer.parseInt(library_id), topicParam.getTopic());
        if (!ObjectUtils.isEmpty(topicParam.getDesc()))
            paperLibraryMapper.modifyDescById(Integer.parseInt(library_id), topicParam.getDesc());
//        if(!ObjectUtils.isEmpty(topicParam.getIs_public()))
//            paperLibraryMapper.modifyIsPublicById(Integer.parseInt(library_id),topicParam.getIs_public());
        paperLibrary = paperLibraryMapper.selectPaperLibraryById(Integer.parseInt(library_id));
        return ReturnValue.generateSuccess(new PaperLibraryParamOutput(paperLibrary));
    }

    @RequestMapping(value = "/api/library/{library_id}", method = RequestMethod.DELETE)
    public ReturnValue deleteLibrary(@PathVariable String library_id) {
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        PaperLibrary paperLibrary = paperLibraryMapper.selectPaperLibraryById(Integer.parseInt(library_id));
        if (!owner.equals(paperLibrary.getOwner())) {
            return ReturnValue.generate("800", "无权限", null);
        }
        paperLibraryMapper.deletePaperLibraryById(Integer.parseInt(library_id));
        //paperLibrary = paperLibraryMapper.selectPaperLibraryById(Integer.parseInt(library_id));
        PaperLibraryParamOutput paperLibraryParamOutput = null;
        return ReturnValue.generateSuccess(paperLibraryParamOutput);
    }


    /*@PostMapping(value = "/api/paper", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ReturnValue addPaper(@RequestBody PaperParam paperParam){
        paperMapper.addPaper(paperParam);
        Paper paper = paperMapper.selectPaperByTitle(paperParam.getTitle());
        return ReturnValue.generateSuccess(new PaperParamOutput(paper));
    }*/

}
