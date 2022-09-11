package fun.gpower.blog.service;

import fun.gpower.blog.pojo.SysUser;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.UserVo;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    UserVo findUserVoById(Long id);
}
