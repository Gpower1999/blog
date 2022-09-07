package fun.gpower.blog.handler;

import fun.gpower.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception exception){
        exception.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
