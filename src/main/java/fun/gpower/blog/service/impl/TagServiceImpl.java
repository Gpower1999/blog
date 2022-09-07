package fun.gpower.blog.service.impl;

import fun.gpower.blog.dao.TagMapper;
import fun.gpower.blog.pojo.Tag;
import fun.gpower.blog.service.TagService;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<TagVo> findTagByArticleId(Long articleId) {
        List<Tag> tagList = tagMapper.findTagByArticleId(articleId);
        return copyList(tagList);
    }

    @Override
    public Result hotTags(int limit) {
        /**
         * 1.标签下最多的文章、
         * 2.查询 根据tag_id分组技术，从大到小排列取前limit
         */
        List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(hotsTagIds)){
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList = new ArrayList<>();
        for (Long hotsTagId : hotsTagIds) {
            Tag tag = tagMapper.selectById(hotsTagId);
            tagList.add(tag);
        }
        List<TagVo> tagVoList = copyList(tagList);
        return Result.success(tagVoList);
    }

    private TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    private List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
}
