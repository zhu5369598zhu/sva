package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.*;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.inspection.entity.ClassGroupEntity;
import io.renren.modules.inspection.entity.ClassWorkerEntity;
import io.renren.modules.inspection.entity.DeviceEntity;
import io.renren.modules.inspection.service.ClassGroupService;
import io.renren.modules.inspection.service.ClassWorkerService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.form.PasswordForm;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService deptService;
	@Autowired
	private ClassGroupService classGroupService;
	@Autowired
	private ClassWorkerService workerService;


	@PostMapping("/upload")
	@RequiresPermissions("sys:user:import")
	public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
		String[] mustHeader = {"用户名<username>", "工号<user_code>", "机构名称<dept_name>", "邮箱<email>", "手机号<mobile>", "微信<wechat>", "状态<status_name>", "备注<remark>", "密码<password>", "密码盐<salt>"};
		List<String[]> rows = PoiUtils.readExcel(file);
		if (!Arrays.equals(rows.get(0), mustHeader)) {
			return R.error(400, "导入数据格式错误，导入失败。");
		}
		if(rows.size() < 2){
			return R.error(400, "导入数据格式错误，导入失败。");
		}
		//去掉表头
		rows.remove(0);
		List<SysUserEntity> users = new ArrayList<>(rows.size());
		for(String[] row: rows){
			SysUserEntity user = new SysUserEntity();
			user.setUsername(row[0]);
			user.setUserCode(row[1]);
			user.setDeptName(row[2]);
			user.setEmail(row[3]);
			user.setMobile(row[4]);
			user.setWechat(row[5]);
			user.setStatusName(row[6]);
			user.setRemark(row[7]);
			user.setPassword(row[8]);
			user.setSalt(row[9]);
			users.add(user);
		}
		return R.ok().put("list", users);
	}

	@RequestMapping("/import")
	@RequiresPermissions("sys:user:import")
	@Transactional
	public R importExcel(@RequestBody SysUserEntity[] users) throws Exception {
		StringBuilder result = new StringBuilder();
		Integer success = 0;
		for (Integer i = 0; i < users.length; i++) {
			SysUserEntity user = users[i];
			result.append("第")
					.append(i + 1)
					.append("行[")
					.append(user.getUsername())
					.append("]");
			if (sysUserService.isExist(user.getUsername(), user.getUserCode()) > 0) {
				result.append("重复\r\n");
				continue;
			}
			//设置部门
			SysDeptEntity dept = deptService.selectByName(user.getDeptName());
			if (dept != null){
				user.setDeptId(dept.getDeptId());
			}else{
				result.append("所属机构未找到\r\n");
				continue;
			}
			//设置状态
			if(user.getStatusName().equals("正常")){
				user.setStatus(1);
			}else if(user.getStatusName().equals("禁用")){
				user.setStatus(0);
			}else{
				result.append("状态数据错误\r\n");
				continue;
			}

			user.setGuid(UUID.randomUUID().toString());
			user.setCreateUserId(this.getUserId());
			user.setCreateTime(new Date());
			try{
				sysUserService.insert(user);
			}catch(Exception e) {
				result.append(e.getMessage());
				result.append("\r\n");
			}
			success = success + 1;
			result.append("导入成功\r\n");
		}
		result.append("------------------------------------------------------\r\n\r\n");
		result.append("成功导入");
		result.append(success);
		result.append("条数据\r\n");
		return R.ok().put("result", result);
	}

	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		PageUtils page = sysUserService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 所有用户列表
	 */
	@GetMapping("/export")
	@RequiresPermissions("sys:user:export")
	public R export(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		List<SysUserEntity> list= sysUserService.all(params);

		return R.ok().put("list", list);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}

	/**
	 * 获取所有用户列表
	 */
	@RequestMapping("/selectByDept")
	@RequiresPermissions("sys:user:list")
	public R selectByDept(){
		List<SysUserEntity> sysUserEntityList = sysUserService.selectList(
				new EntityWrapper<SysUserEntity>()
		);

		return R.ok().put("users", sysUserEntityList);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public R password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		
		//sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		user.setGuid(UUID.randomUUID().toString());
		user.setCreateUserId(getUserId());
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		for(Long userId:userIds){
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			List<ClassWorkerEntity> classWorkerEntities = workerService.selectByMap(params);
			for (ClassWorkerEntity classWorkerEntity:classWorkerEntities){
				ClassGroupEntity classGroupEntity = classGroupService.selectById(classWorkerEntity.getClassGroupId());
				if(classGroupEntity != null){
					return R.error(400,"该用户已添加到班组[" + classGroupEntity.getName() + "]中，不能删除");
				}
			}
		}

		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
}
