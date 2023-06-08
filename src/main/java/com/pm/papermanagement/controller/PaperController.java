package com.pm.papermanagement.controller;

import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.entity.PaperLibrary;
import com.pm.papermanagement.common.exception.BizException;
import com.pm.papermanagement.common.model.PaperLibraries;
import com.pm.papermanagement.common.model.Papers;
import com.pm.papermanagement.common.model.ReturnValue;
import com.pm.papermanagement.common.model.param.*;
import com.pm.papermanagement.mapper.PaperMapper;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class PaperController {
    PaperMapper paperMapper;

    @Autowired
    public void setPaperMapper(PaperMapper paperMapper){
        this.paperMapper = paperMapper;
    }

    @PostMapping(value = "/api/paper", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue addPaper(@RequestBody PaperParam paperParam){
        if(ObjectUtils.isEmpty(paperParam.getTitle())){
            throw new BizException("Title不能为空");
        }
        Integer.parseInt(paperParam.getLibrary_id());
        paperMapper.addPaper(paperParam);
        Paper paper = paperMapper.selectPaperByTitle(paperParam.getTitle());
        return ReturnValue.generateSuccess(new PaperParamOutput(paper));
    }

    @GetMapping("/api/paper")
    public ReturnValue requestPaper(@RequestParam(value = "library_id") String library_id, @RequestParam(value = "page_num") int page_num, @RequestParam(value = "page_size") int page_size, @RequestParam(value = "title",required = false) String title){
        if(page_num < 0 || page_size < 0){
            throw new BizException("参数不能为负数");
        }
        List<Paper> papers;
        if(title != null){
            papers = paperMapper.selectPaperBySequenceAndTitle(Integer.parseInt(library_id),page_num * page_size, page_size, title);
        }else{
            papers = paperMapper.selectPaperBySequence(Integer.parseInt(library_id),page_num * page_size, page_size);
        }
        return ReturnValue.generateSuccess(new Papers(papers));
    }

    @RequestMapping(value = "/api/paper/{paper_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue updatePaper(@PathVariable String paper_id, @RequestBody TitleParam titleParam) {
        // todo:
        paperMapper.modifyTitleById(Integer.parseInt(paper_id),titleParam.getTitle());
        Paper paper = paperMapper.selectPaperById(Integer.parseInt(paper_id));
        return ReturnValue.generateSuccess(new PaperParamOutput(paper));
    }

    @RequestMapping(value = "/api/paper/{paper_id}", method = RequestMethod.DELETE)
    public ReturnValue deletePaper(@PathVariable String paper_id){
        paperMapper.deletePaperById(Integer.parseInt(paper_id));
        Paper paper = paperMapper.selectPaperById(Integer.parseInt(paper_id));
        PaperParamOutput paperParamOutput = null;
        return ReturnValue.generateSuccess(paperParamOutput);
    }

    @PostMapping(value = "/api/paper/{paper_id}/pdf")
    public  ReturnValue uploadPaperPDF(@PathVariable String paper_id, @RequestBody FileParam fileParam){
        String uploadDir = "~/paper-management/src/PDF";
        String fileName = UUID.randomUUID().toString() + ".pdf";
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(fileParam.getFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            paperMapper.releaseSourceByID(uploadDir + fileName,Integer.parseInt(paper_id));
        } catch (IOException e) {
            throw new BizException("Failed to store file");
        }
        return ReturnValue.generateSuccess(new SourceParam(uploadDir + fileName));
    }
}
