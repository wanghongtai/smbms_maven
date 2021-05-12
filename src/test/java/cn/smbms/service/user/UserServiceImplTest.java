package cn.smbms.service.user;

import cn.smbms.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Hunter on 2021-05-07.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceImplTest {

    @Autowired
    UserService userService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void login() throws Exception {
//        ApplicationContext context
//                = new ClassPathXmlApplicationContext("applicationContext.xml");
//        UserService userService = context.getBean(UserService.class);
        User user = userService.login("admin", "1234567");
        System.out.println(user);
    }

    @Test
    public void test2() throws Exception {
        List<User> userList
                = userService.getUserList("", null, -1, 5);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void test4(){
        User admin = userService.getUserByUserCode("admin2");
        System.out.println(admin);
    }


    @Test
    public void test3() throws Exception {
        User user = new User();
        user.setCreatedBy(1);
        user.setCreationDate(new Date());
        user.setUserRole(2);
        user.setAddress("test 地址");
        user.setPhone("15815678945");
        user.setBirthday(sdf.parse("2000-01-02"));
        user.setUserName("季飞宇");
        user.setGender(1);
        user.setUserCode("jifeiyu");
        user.setUserPassword("1234567");
        boolean result = userService.add(user);
        System.out.println(result?"添加成功":"添加失败");
    }


}