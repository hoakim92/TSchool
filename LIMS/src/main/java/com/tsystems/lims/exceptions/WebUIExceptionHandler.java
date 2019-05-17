package com.tsystems.lims.exceptions;

import lombok.extern.log4j.Log4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j
@ControllerAdvice
public class WebUIExceptionHandler {

    @ExceptionHandler
    public String handleException(Exception exception, Model model){
        log.info("ERROR " + exception.getMessage());
        for (StackTraceElement e :exception.getStackTrace())
            log.info(e.toString());
        model.addAttribute("error", exception.getMessage());
        return "exception";
    }
}


