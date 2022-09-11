package fun.gpower.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.gpower.blog.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
