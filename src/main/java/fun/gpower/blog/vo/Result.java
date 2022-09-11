package fun.gpower.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    /**
     * 统一返回值
     */
    private Integer code;

    private boolean success;

    private String msg;

    private Object data;

    public static Result success(Object data) {
        return new Result(200, true, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(code, false, msg, null);
    }
}
