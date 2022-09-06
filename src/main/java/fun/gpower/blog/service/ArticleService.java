package fun.gpower.blog.service;

import fun.gpower.blog.vo.ArticleVo;
import fun.gpower.blog.vo.params.PageParams;

import java.util.List;
public interface ArticleService {
    List<ArticleVo> listArticlesPage(PageParams pageParams);
}
