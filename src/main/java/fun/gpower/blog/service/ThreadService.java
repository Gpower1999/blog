package fun.gpower.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import fun.gpower.blog.dao.ArticleMapper;
import fun.gpower.blog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    @Autowired
    private ArticleMapper articleMapper;

    @Async("taskExecutor")
    public void updateViewCount(Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        UpdateWrapper wrapper = new UpdateWrapper();;
        wrapper.eq("id",article.getId());
        wrapper.eq("view_counts",viewCounts);
        articleMapper.update(articleUpdate,wrapper);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
