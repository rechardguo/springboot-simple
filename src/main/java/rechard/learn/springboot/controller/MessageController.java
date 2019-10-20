package rechard.learn.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rechard.learn.springboot.PSOProperties;

import java.util.Locale;


@RestController
public class MessageController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PSOProperties pr;

    @GetMapping("/{msg}")
    public String getMessage(@PathVariable("msg") String msg){
        //return pr.getMessage("link_resource.a.payment","argument.required");
        return context.getMessage(msg,null ,Locale.US);
    }
}
