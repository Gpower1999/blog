package fun.gpower.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginParam {
    private String account;

    private String password;
}
