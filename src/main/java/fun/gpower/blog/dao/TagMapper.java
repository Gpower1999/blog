package fun.gpower.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.gpower.blog.pojo.Tag;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.TagVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查tag标签
     * @param articleId
     * @return
     */
    List<Tag> findTagByArticleId(Long articleId);

    /**
     * 查询前limit个标签
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);
}
