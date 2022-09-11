package fun.gpower.blog.service;

import fun.gpower.blog.vo.CategoryVo;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
