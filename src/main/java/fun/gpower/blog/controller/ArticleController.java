package fun.gpower.blog.controller;

import fun.gpower.blog.service.ArticleService;
import fun.gpower.blog.vo.ArticleVo;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result articles(@RequestBody(required = false) PageParams pageParams){
        List<ArticleVo> articleVoList = articleService.listArticlesPage(pageParams);
        return Result.success(articleVoList);
    }
}
