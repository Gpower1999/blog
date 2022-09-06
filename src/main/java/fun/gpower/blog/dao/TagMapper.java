package fun.gpower.blog.dao;

import fun.gpower.blog.pojo.Tag;
import fun.gpower.blog.vo.TagVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    List<Tag> findTagByArticleId(Long articleId);
}
