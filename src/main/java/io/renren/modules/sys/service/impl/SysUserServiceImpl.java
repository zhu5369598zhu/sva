package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");
		String deptId = (String) params.get("deptId");
		Long createUserId = (Long)params.get("createUserId");
		if (deptId == null || deptId.equals("")){
		    deptId = "0";
        }
		List<Integer> deptIds = sysDeptService.queryRecursiveChildByParentId(Long.parseLong(deptId));

		Page<SysUserEntity> page = this.selectPage(
			new Query<SysUserEntity>(params).getPage(),
			new EntityWrapper<SysUserEntity>()
					.eq("is_delete",0)
				.like(StringUtils.isNotBlank(username),"username", username)
				.in("dept_id", deptIds)
		);

		for(SysUserEntity sysUserEntity : page.getRecords()){
			SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
			sysUserEntity.setSalt("");
			sysUserEntity.setDeptName(sysDeptEntity.getName());
		}
		return new PageUtils(page);
	}

	@Override
	public List<SysUserEntity> all(Map<String, Object> params) {
		String username = (String)params.get("username");
		String deptId = (String) params.get("deptId");
		Long createUserId = (Long)params.get("createUserId");
		if (deptId == null || deptId.equals("")){
			deptId = "0";
		}
		List<Integer> deptIds = sysDeptService.queryRecursiveChildByParentId(Long.parseLong(deptId));

		List<SysUserEntity> list = this.selectList(
				new EntityWrapper<SysUserEntity>()
						.eq("is_delete",0)
						.like(StringUtils.isNotBlank(username),"username", username)
						.in("dept_id", deptIds)
		);

		for(SysUserEntity sysUserEntity : list){
			SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
			sysUserEntity.setDeptName(sysDeptEntity.getName());

			if (sysUserEntity.getStatus().equals(1)) {
				sysUserEntity.setStatusName("正常");
			} else {
				sysUserEntity.setStatusName("禁用");
			}

		}

		return list;
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		this.insert(user);

		/*
		//检查角色是否越权
		checkRole(user);
		*/
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		SysUserEntity tmp = this.selectById(user.getUserId());

		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(tmp.getPassword());
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}

		user.setGuid(tmp.getGuid());
		user.setCreateTime(tmp.getCreateTime());
		user.setIsDelete(0);
		this.updateAllColumnById(user);




		/*
		//检查角色是否越权
		checkRole(user);
		*/
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userIds) {
		List<Long> userIdList = Arrays.asList(userIds);

		for(Long userId :userIdList){
			sysUserRoleService.deleteByUserId(userId);
		}
		for(Long userId:userIdList){
			SysUserEntity user = this.selectById(userId);
			if(user != null){
				user.setIsDelete(1);
				this.deleteById(user);
			}
		}
		//this.deleteBatchIds(userIdList);
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}

	@Override
	public long login(LoginForm form) {
		SysUserEntity user = queryByUserName(form.getUsername());

		//密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())){
			throw new RRException("用户名或密码错误");
		}

		//账号锁定
		if(user.getStatus() == 0){
			throw new RRException("账号已被锁定,请联系管理员");
		}

		return user.getUserId();
	}

	@Override
	public SysUserEntity selectByGuid(String guid) { return baseMapper.selectByGuid(guid); }

	@Override
	public Integer isExist(String userName, String userCode){
		return baseMapper.isExist(userName, userCode);
	}

	@Override
	public List<Map<String, Object>> findByUserTree() {


		List<Map<String,Object>> root = sysUserDao.queryListParentId(0);

		for(Map<String, Object> m : root){
			getTreeNodeData(m);
		}

		return clearNode(root);
	}

	private List<Map<String, Object>> clearNode(List<Map<String, Object>> curNode) {
		Iterator<Map<String,Object>> item = curNode.iterator();
		while (item.hasNext()){
			Map<String, Object> node = item.next();
			String type = (String)node.get("type");
			if(type.equals("dept")) {
				List<Map<String,Object>> children = (List<Map<String,Object>>)node.get("children");
				if (children.size() == 0){
					item.remove();
				} else {
					clearNode(children);
				}
			}
		}

		return curNode;
	}

	private void getTreeNodeData(Map<String, Object> node) {
		// 查看该部门下 的 用户
		List<Map<String,Object>> users = sysUserDao.findDeviceLeveList((Long)node.get("id"));

		if(node.get("type").equals("dept")){
			List<Map<String, Object>> children  = sysUserDao.querydeptListParentId((Long)node.get("id"));
			if(children != null && children.size() > 0 ){
				children.addAll(users);
				node.put("children", children);
				for(Map<String, Object> m : children){
					if(m.get("type").equals("dept")) { // 如果是部门才 继续循环，是设备等级 就循环下一个部门
						getTreeNodeData(m);
					}
				}
			} else {
				node.put("children", users);
			}
		}


	}


}
