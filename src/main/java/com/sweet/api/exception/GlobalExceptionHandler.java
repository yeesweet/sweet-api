package com.sweet.api.exception;

import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.constants.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 通用 Api Controller 全局异常处理
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseMessage handleException(HttpServletRequest request , HttpServletResponse response, Throwable e){
        logger.error("",e);
        ResponseMessage responseMessage = new ResponseMessage(MessageConst.CODE_INNER_ERROR,MessageConst.MSG_INNER_ERROR);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return responseMessage;
    }

    @ExceptionHandler(InputException.class)
    @ResponseBody
    ResponseMessage handleInputException(HttpServletRequest request , HttpServletResponse response, InputException exception) {
        logger.error("Input exception",exception);
        ResponseMessage responseMessage = new ResponseMessage(MessageConst.CODE_INPUT_ERROR,exception.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return responseMessage;
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseBody
    ResponseMessage handlePermissionDeniedException(HttpServletRequest request , HttpServletResponse response, PermissionDeniedException exception) {
        logger.warn("PermissionDeniedException,无应用权限，msg:{}",exception.getMessage());
        ResponseMessage responseMessage = new ResponseMessage(MessageConst.CODE_PERMISSION_DENIED_ERROR,exception.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return responseMessage;
    }

    @ExceptionHandler(ShowNotAllowedException.class)
    @ResponseBody
    ResponseMessage handleShowNotAllowedException(HttpServletRequest request , HttpServletResponse response, PermissionDeniedException exception) {
        logger.warn("ShowNotAllowedException,无权接口访问权限，msg:{}",exception.getMessage());
        ResponseMessage responseMessage = new ResponseMessage(MessageConst.CODE_NOT_ALLOWED_ERROR,exception.getMessage());
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return responseMessage;
    }
}
