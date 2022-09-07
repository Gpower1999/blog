package fun.gpower.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.gpower.blog.dao.ArticleMapper;
import fun.gpower.blog.dao.TagMapper;
import fun.gpower.blog.dao.dos.Archives;
import fun.gpower.blog.pojo.Article;
import fun.gpower.blog.service.ArticleService;
import fun.gpower.blog.service.SysUserService;
import fun.gpower.blog.service.TagService;
import fun.gpower.blog.vo.ArticleVo;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    /**
     * 分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {

        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_date", "weight");
        Page<Article> page = new Page(pageParams.getPage(), pageParams.getPageSize());

        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //得到的是Article,想拿到ArticleVo对象
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return articleVoList;
    }

    /**
     * 首页 热帖查询
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_counts");
        Page<Article> page = new Page<>(1,limit);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> articles = articlePage.getRecords();
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 首页 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_date");
        Page<Article> page = new Page<>(1,limit);
        List<Article> articleList = articleMapper.selectPage(page, queryWrapper).getRecords();
        return Result.success(copyList(articleList,false,false));
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        if (isTag) {
            articleVo.setTags(tagService.findTagByArticleId(article.getId()));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        return articleVo;
    }

}
