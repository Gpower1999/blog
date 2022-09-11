package fun.gpower.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.gpower.blog.dao.ArticleBodyMapper;
import fun.gpower.blog.dao.ArticleMapper;
import fun.gpower.blog.dao.TagMapper;
import fun.gpower.blog.dao.dos.Archives;
import fun.gpower.blog.pojo.Article;
import fun.gpower.blog.pojo.ArticleBody;
import fun.gpower.blog.service.*;
import fun.gpower.blog.vo.ArticleBodyVo;
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
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;

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
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return articleVoList;
    }

    /**
     * 首页 热帖查询
     *
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_counts");
        Page<Article> page = new Page<>(1, limit);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> articles = articlePage.getRecords();
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 首页 最新文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_date");
        Page<Article> page = new Page<>(1, limit);
        List<Article> articleList = articleMapper.selectPage(page, queryWrapper).getRecords();
        return Result.success(copyList(articleList, false, false));
    }

    /**
     * 文章归档
     *
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }
    @Autowired
    private ThreadService threadService;
    /**
     * 查询文章详情
     *
     * @param id
     * @return
     */
    @Override
    public ArticleVo findArticleById(Long id) {
        /**
         * 1.根据id查询，找到bodyid
         * 2.根据bodyid和catagoryid去做关联查询
         */
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //查看完文章，新增阅读数，但是存在问题
        //查看完文章之后，本应该直接返回数据了，但是有一个更新操作实行的加锁操作，阻塞了其他的读操作，性能会降低
        threadService.updateViewCount(article);
        return articleVo;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        if (isTag) {
            articleVo.setTags(tagService.findTagByArticleId(article.getId()));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategorys(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }


    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
