package fun.gpower.blog.service;

import fun.gpower.blog.pojo.SysUser;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.params.LoginParam;
import org.springframework.stereotype.Service;

public interface LoginService {
    Result login(LoginParam loginParam);

    Result logout(String token);

    SysUser checkToken(String token);
}
