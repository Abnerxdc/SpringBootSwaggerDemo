package com.xdc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/26.
 */
@Api("用户服务")
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",defaultValue="user123",required=true,dataType="String",name="username",value="用户名"),
            @ApiImplicitParam(paramType="query",defaultValue="password123",required=true,dataType="String",name="pwd",value="密码")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不正确"),
            @ApiResponse(code=401,message="请求未被授权"),
            @ApiResponse(code=403,message="请求被阻止"),
            @ApiResponse(code=404,message="请求页面不存在")
    })
    @RequestMapping(value= "/register", method= RequestMethod.GET)
    public Map<String,String> register(String username,String pwd){
        Map<String, String> resultMap= new HashMap();
        resultMap.put("msg", "200");
        resultMap.put("username", username);
        resultMap.put("pwd", pwd);
        return resultMap;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录,需要输入用户名和密码")
    @ApiParam(defaultValue="{\"username\":\"test\",\"pwd\":\"123456\"}",required=true,name="user",value="用户")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不正确"),
            @ApiResponse(code=401,message="请求未被授权"),
            @ApiResponse(code=403,message="请求被阻止"),
            @ApiResponse(code=404,message="请求页面不存在")
    })
    @RequestMapping(value= "/login", method=RequestMethod.POST)
    public User login(@RequestBody User user){
        return user;
    }


   // @ApiIgnore //使用该注解忽略这个API
    @ApiOperation(value = "获取想要的值得格式", notes = "返回想要的值...")
   @ApiResponses({
           @ApiResponse(code=400,message="请求参数不正确"),
           @ApiResponse(code=401,message="请求未被授权"),
           @ApiResponse(code=403,message="请求被阻止"),
           @ApiResponse(code=404,message="请求页面不存在")
   })
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "object1", defaultValue = "{\"username1\":\"test\",\"username2\":\"123456\"}", value = "object",required = true, dataType = "{\"username1\":\"test\",\"username2\":\"123456\"}"),
//            @ApiImplicitParam(name = "object2", defaultValue = "{\"password1\":\"qwe\",\"password2\":\"asd\"}",  value = "object",required = true, dataType = "{\"password1\":\"qwe\",\"password2\":\"asd\"}")
//    })
//   @ApiModelProperty(value = "用户名： ", example = "maxTse",position = 1)
   @RequestMapping(value= "/object", method=RequestMethod.POST)
   public Object getForm(@RequestParam @ApiParam(name="object1",value="参数a",required=true) Object object1,
                         @RequestParam @ApiParam(name="object2",value="参数b",required=true) Object object2
                         ){
        JSONObject obj1 = JSON.parseObject(object1.toString());
        JSONObject obj2 = JSON.parseObject(object2.toString());
        String username1 = obj1.getString("username1");
        String username2 = obj1.getString("username2");
        String password1 = obj2.getString("password1");
        String password2 = obj2.getString("password2");
        JSONObject obj = new JSONObject();
        obj.put("用户名1：",username1);
        obj.put("用户名2：",username2);
        obj.put("密码1：",password1);
        obj.put("密码2： ",password2);
//        String result = obj.toJSONString();
       return obj;
   }


    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public Object putUser(@PathVariable String id, @RequestBody User user) {
       logger.info("-------------id: "+id);
        String uid = id;
        String username = user.getUsername();
        String password = user.getPassword();
        JSONObject obj = new JSONObject();
        obj.put("id",uid);
        obj.put("username",username);
        obj.put("password",password);
        return obj;
    }

    @ApiOperation(value="计算结果", notes = "根据参数a、b和c计算a+b+c的结果")
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public Integer demo(@RequestParam @ApiParam(name="a",value="参数a",required=true)Integer a,
                        @RequestParam  @ApiParam(name="b",value="参数b",required=true)Integer b,
                        @RequestParam @ApiParam(name="c",value="参数c",required=true) Integer c) {
       Integer d = a+b+c;
       return d;
    }

    @ApiOperation(value="计算结果", notes = "根据参数a、b")
    @RequestMapping(value = "/uuUuu", method = RequestMethod.GET)
    //注意： 在后台采用对象接收参数时，Swagger自带的工具采用的是JSON传参，
    //测试时需要在参数上加入@RequestBody,正常运行采用form或URL提交时候请删除。
    public Object UserDemo(@RequestParam @ApiParam(name="a",value="参数a",required=true)Integer a,
                        @RequestBody  @ApiParam(name="b",value="参数b",required=true)String b) {
       logger.debug("--------------b:"+b);
       JSONObject bj = JSON.parseObject(b);
       JSONObject obj = new JSONObject();
       obj.put("b",bj.getString("111"));
       obj.put("a",a);
        return obj;
    }


    class User{
        private String username;

        private String password;

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

}
