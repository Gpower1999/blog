package fun.gpower.blog.service.impl;

import fun.gpower.blog.dao.TagMapper;
import fun.gpower.blog.pojo.Tag;
import fun.gpower.blog.service.TagService;
import fun.gpower.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
