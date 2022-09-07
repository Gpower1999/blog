package fun.gpower.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.gpower.blog.dao.dos.Archives;
import fun.gpower.blog.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}
