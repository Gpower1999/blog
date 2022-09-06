package fun.gpower.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.gpower.blog.dao.SysUserMapper;
import fun.gpower.blog.pojo.SysUser;
import fun.gpower.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("Gpower");
        }
        return sysUser;
    }
}
