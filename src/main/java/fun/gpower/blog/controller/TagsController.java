package fun.gpower.blog.controller;

import fun.gpower.blog.service.TagService;
import fun.gpower.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private TagService tagService;
    @GetMapping("/hot")
    public Result hot(){
        int limit = 6;
        return tagService.hotTags(limit);
    }
}
