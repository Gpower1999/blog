package fun.gpower.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.gpower.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
