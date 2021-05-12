package cn.smbms.service.user;

import cn.smbms.mapper.user.UserDao;
import cn.smbms.pojo.User;
import cn.smbms.tools.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 *
 * @author Administrator
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean add(User user) throws Exception {
        int count = userDao.add(user);
        return count > 0 ? true : false;
    }

    @Override
    public User login(String userCode, String userPassword) throws Exception {
        User user = userDao.getLoginUser(userCode, userPassword);
        return user;
    }

    @Override
    public List<User> getUserList(String queryUserName, Integer queryUserRole,
                                  Integer currentPageNo, Integer pageSize) throws Exception {

        //1、查询总记录数，计算总页数
        int count = userDao.getUserCount(queryUserName, queryUserRole);
        //对页码和每页大小做兼容性处理
        PageSupport support = new PageSupport(currentPageNo, pageSize, count);
        //2、查询当前页的数据
        int pageNo = support.getCurrentPageNo();
        int size = support.getPageSize();
        //3、把用户信息，页码信息放入Page对象
        int startIndex = (pageNo - 1) * size;
        List<User> userList = userDao.getUserList(queryUserName, queryUserRole, startIndex, size);
        return userList;
    }

    @Override
    public int getUserCount(String queryUserName, Integer queryUserRole) throws Exception {
        int count = userDao.getUserCount(queryUserName, queryUserRole);
        return count;
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        return null;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        return false;
    }

    @Override
    public User getUserById(Integer id) throws Exception {

        return userDao.getUserById(id);
    }

    @Override
    public boolean modify(User user) {
        return false;
    }

    @Override
    public boolean updatePwd(int id, String pwd) {
        return false;
    }

    @Override
    public User getUserByUserCode(String userCode) {
        return userDao.getByUserCode(userCode);
    }
}
