package io.renren.modules.sys.controller;

import io.renren.common.utils.R;
import io.renren.modules.sys.entity.UserPicEntity;
import io.renren.modules.sys.service.UserPicService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-10-17 16:00:30
 */
@RestController
@RequestMapping("sys/userpic")
public class UserPicController {
    @Autowired
    private UserPicService userPicService;

    @GetMapping("pic.png")
    public void qrcode(HttpServletResponse response, String uuid)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/png");

        UserPicEntity pic = userPicService.selectByGuid(uuid);

        ServletOutputStream out = response.getOutputStream();
        out.write(pic.getData());
        IOUtils.closeQuietly(out);
        response.setContentLength(pic.getData().length);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:userpic:list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = userPicService.queryPage(params);
        //return R.ok().put("page", page);
        String userId = params.get("userId").toString();
        List<Map<String, Object>> picEntities = userPicService.selectByUserId(Integer.valueOf(userId));
        return R.ok().put("piclist", picEntities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:userpic:info")
    public R info(@PathVariable("id") Integer id){
			UserPicEntity userPic = userPicService.selectById(id);

        return R.ok().put("userPic", userPic);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:userpic:save")
    public R save(@RequestBody UserPicEntity userPic){
			userPicService.insert(userPic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:userpic:update")
    public R update(@RequestBody UserPicEntity userPic){
			userPicService.updateById(userPic);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{guid}")
    @RequiresPermissions("sys:userpic:delete")
    public R delete(@PathVariable("guid") String guid){
			//userPicService.deleteBatchIds(Arrays.asList(ids));
             userPicService.deleteByGuid(guid);
        return R.ok();
    }

    @PostMapping("/upload")
    @RequiresPermissions("sys:userpic:upload")
    public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, Integer userId)throws Exception{
        if(userId == null){
            return R.error(400,"参数错误，上传失败。");
        }
        if(file != null) {
            UserPicEntity pic = new UserPicEntity();
            pic.setUserId(userId);
            pic.setData(file.getBytes());
            pic.setGuid(UUID.randomUUID().toString());
            pic.setCreateTime(new Date());
            if(userPicService.insert(pic)){
                return R.ok();
            }
        }

        return R.error(500, "上传失败，服务器内部错误。");
    }



}
