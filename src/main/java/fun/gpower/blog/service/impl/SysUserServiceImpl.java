package fun.gpower.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.gpower.blog.dao.SysUserMapper;
import fun.gpower.blog.pojo.SysUser;
import fun.gpower.blog.service.LoginService;
import fun.gpower.blog.service.SysUserService;
import fun.gpower.blog.utils.JWTUtils;
import fun.gpower.blog.vo.ErrorCode;
import fun.gpower.blog.vo.LoginUserVo;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("Gpower");
        }
        return sysUser;
    }

    /**
     * 根据账号密码查询用户
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public SysUser findUser(String account, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", password);
        queryWrapper.select("id", "account", "avatar", "nickname");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;

    }

    /**
     * 根据token查用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性校验
         *     是否为空
         *     解析是否成功
         *     redis是否存在
         * 2.如果校验失败，返回错误
         * 3.如果成功返回成功的结果 LoginUserVo
         */
        if (!StringUtils.hasLength(token)) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        String userJSON = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (!StringUtils.hasLength(userJSON)){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        SysUser sysUser = JSONObject.parseObject(userJSON, SysUser.class);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        // BeanUtils.copyProperties(sysUser,loginUserVo);
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }
    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("gpower");
        }
        UserVo userVo = new UserVo();
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());
        userVo.setId(sysUser.getId());
        return userVo;
    }
}
