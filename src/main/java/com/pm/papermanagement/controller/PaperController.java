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
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
//@CrossOrigin
public class PaperController {
    PaperMapper paperMapper;

    @Autowired
    public void setPaperMapper(PaperMapper paperMapper){
        this.paperMapper = paperMapper;
    }

    @PostMapping(value = "/api/paper", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue addPaper(@RequestBody PaperParam paperParam){
//        if(!SecurityUtils.getSubject().isAuthenticated()){
//            System.out.println("未登录：" + SecurityUtils.getSubject().getPrincipal());
//            return ReturnValue.generate("101","未登陆",null);
//        }
        //System.out.println(SecurityUtils.getSubject().getPrincipal().toString());
        if(ObjectUtils.isEmpty(paperParam.getTitle())){
            throw new BizException("Title不能为空");
        }
        int library_id = Integer.parseInt(paperParam.getLibrary_id());
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        paperMapper.addPaper(library_id,paperParam.getTitle(),owner);
        Paper paper = paperMapper.selectPaperByTitle(paperParam.getTitle());
        return ReturnValue.generateSuccess(new PaperParamOutput(paper));
    }

    @GetMapping("/api/paper")
    public ReturnValue requestPaper(@RequestParam(value = "library_id") String library_id, @RequestParam(value = "page_num") int page_num, @RequestParam(value = "page_size") int page_size, @RequestParam(value = "title",required = false) String title){
        page_num -= 1;
        if(page_num < 0 || page_size < 0){
            throw new BizException("参数不能为负数");
        }
        List<Paper> papers;
        if(title != null){
            papers = paperMapper.selectPaperBySequenceAndTitle(Integer.parseInt(library_id),page_num * page_size, page_size, title);
        }else{
            papers = paperMapper.selectPaperBySequence(Integer.parseInt(library_id),page_num * page_size, page_size);
        }
        int paperNum = paperMapper.getPaperNum(Integer.parseInt(library_id));
        return ReturnValue.generateSuccess(new Papers(papers,paperNum));
    }

    @RequestMapping(value = "/api/paper/{paper_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue updatePaper(@PathVariable String paper_id, @RequestBody TitleParam titleParam) {
        // todo:
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        Paper paper = paperMapper.selectPaperById(Integer.parseInt(paper_id));
        if(!owner.equals(paper.getOwner())){
            return ReturnValue.generate("800","无权限",null);
        }
        if(!ObjectUtils.isEmpty(titleParam.getTitle()))
            paperMapper.modifyTitleById(Integer.parseInt(paper_id),titleParam.getTitle());
        if(!ObjectUtils.isEmpty(titleParam.getAuthors()))
            paperMapper.modifyAuthorsById(Integer.parseInt(paper_id),titleParam.getAuthors());
        if(!ObjectUtils.isEmpty(titleParam.getPublisher()))
            paperMapper.modifyPublisherById(Integer.parseInt(paper_id),titleParam.getPublisher());
        if(titleParam.getYear() != 0)
            paperMapper.modifyYearById(Integer.parseInt(paper_id),titleParam.getYear());
        paper = paperMapper.selectPaperById(Integer.parseInt(paper_id));
        return ReturnValue.generateSuccess(new PaperParamOutput(paper));
    }

    @RequestMapping(value = "/api/paper/{paper_id}", method = RequestMethod.DELETE)
    public ReturnValue deletePaper(@PathVariable String paper_id){
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        Paper paper = paperMapper.selectPaperById(Integer.parseInt(paper_id));
        if(!owner.equals(paper.getOwner())){
            return ReturnValue.generate("800","无权限",null);
        }
        paperMapper.deletePaperById(Integer.parseInt(paper_id));
        //paper = paperMapper.selectPaperById(Integer.parseInt(paper_id));
        PaperParamOutput paperParamOutput = null;
        return ReturnValue.generateSuccess(paperParamOutput);
    }

    @PostMapping(value = "/api/paper/{paper_id}/pdf")
    public  ReturnValue uploadPaperPDF(@PathVariable String paper_id, @RequestParam("file") MultipartFile file){
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        Paper paper = paperMapper.selectPaperById(Integer.parseInt(paper_id));
        if(!owner.equals(paper.getOwner())){
            return ReturnValue.generate("800","无权限",null);
        }
        String uploadDir = "/Users/lonelyslime/paper-management/src/PDF/";
        String fileName = UUID.randomUUID().toString() + ".pdf";
        String pathname = uploadDir + fileName;
        try {
            File file1 = new File(pathname);
            System.out.println("文件路径：%s".formatted(pathname));
            file.transferTo(file1);
            //Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            paperMapper.releaseSourceByID(pathname,Integer.parseInt(paper_id));
        } catch (IOException e) {
            throw new BizException("Failed to store file");
        }
        return ReturnValue.generateSuccess(new SourceParam(pathname));
    }

    @GetMapping(value = "/api/paper/{paper_id}/pdf/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadPaper(@PathVariable String paper_id){
        String source = paperMapper.selectPaperById(Integer.parseInt(paper_id)).getSource();
        if(ObjectUtils.isEmpty(source)){
            return null;
        }
        Resource resource = new FileSystemResource(source);
        String filename = "paper.pdf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping(value = "/api/paper/{paper_id}/pdf/preview")
    public ResponseEntity<byte[]> previewPaper(@PathVariable String paper_id) throws IOException {
        String source = paperMapper.selectPaperById(Integer.parseInt(paper_id)).getSource();
        if(ObjectUtils.isEmpty(source)){
            return null;
        }
        Path path = Paths.get(source);
        byte[] fileContent = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);

    }
}
