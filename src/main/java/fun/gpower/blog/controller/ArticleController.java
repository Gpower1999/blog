package fun.gpower.blog.controller;

import fun.gpower.blog.service.ArticleService;
import fun.gpower.blog.vo.ArticleVo;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页的文章
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result articles(@RequestBody(required = false) PageParams pageParams) {
        List<ArticleVo> articleVoList = articleService.listArticlesPage(pageParams);
        return Result.success(articleVoList);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("/hot")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("/new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){

        ArticleVo articleVo = articleService.findArticleById(id);
        return Result.success(articleVo);
    }
}
