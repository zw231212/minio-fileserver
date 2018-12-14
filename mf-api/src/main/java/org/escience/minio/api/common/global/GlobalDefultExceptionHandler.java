package org.escience.minio.api.common.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefultExceptionHandler.class);

    //声明要捕获的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object defultExcepitonHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return "{\"msg\":\"error:"+e.getMessage()+"\",\"code\":500}";
    }

}
