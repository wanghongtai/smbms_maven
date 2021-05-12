package cn.smbms.mapper.role;

import cn.smbms.pojo.Role;

import java.util.List;

public interface RoleDao {
	
	public List<Role> getRoleList()throws Exception;

}
