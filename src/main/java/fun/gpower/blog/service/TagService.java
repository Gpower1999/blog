package fun.gpower.blog.service;

import fun.gpower.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagByArticleId(Long articleId);
}
