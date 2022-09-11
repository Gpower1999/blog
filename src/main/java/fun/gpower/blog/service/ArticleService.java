package fun.gpower.blog.service;

import fun.gpower.blog.vo.ArticleVo;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.params.PageParams;

import java.util.List;
public interface ArticleService {
    List<ArticleVo> listArticlesPage(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticles(int limit);

    Result listArchives();

    ArticleVo findArticleById(Long id);
}
