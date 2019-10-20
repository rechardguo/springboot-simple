package rechard.learn.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rechard.learn.springboot.entity.JsonResult;
import rechard.learn.springboot.entity.User;
import springfox.documentation.annotations.ApiIgnore;

import javax.websocket.server.PathParam;
import java.util.*;


@RestController
public class UserController {


    @RequestMapping("/rest/call/{id}")
    public String restfulCall(@PathVariable int id){
        System.out.println("restful call id->"+id);
        return id+"";
    }
    @RequestMapping("/nonrest/call")
    public String nonRestfulCall(@PathParam("id") int id){
        System.out.println("non restfull call id->"+id);
        return id+"";
    }

}
