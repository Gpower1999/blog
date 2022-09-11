package fun.gpower.blog.service;

import fun.gpower.blog.vo.CommentParam;
import fun.gpower.blog.vo.Result;

public interface CommentService {
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
