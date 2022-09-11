package fun.gpower.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fun.gpower.blog.pojo.SysUser;
import fun.gpower.blog.service.LoginService;
import fun.gpower.blog.service.SysUserService;
import fun.gpower.blog.utils.JWTUtils;
import fun.gpower.blog.vo.ErrorCode;
import fun.gpower.blog.vo.Result;
import fun.gpower.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String salt = "gpower#@#@";

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去user中查询 是否存在
         * 3.如果不存在 登录失败
         * 4.如果存在，使用jwt 生成token 返回给前端
         * 5.将token放入redis中， redis    token：user信息 设置过期时间
         * （登录认证的时候 先认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

        return Result.success(token);
    }

    /**
     * 登出
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete(token);
        return Result.success(null);
    }

    /**
     * 根据token 找到SysUser
     * @param token
     * @return
     */
    @Override
    public SysUser checkToken(String token) {
        if (!StringUtils.hasLength(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return null;
        }
        String userJSON = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (!StringUtils.hasLength(userJSON)){
            return null;
        }
        SysUser sysUser = JSONObject.parseObject(userJSON, SysUser.class);
        if (sysUser == null){
            return null;
        }
        return sysUser;
    }
}
