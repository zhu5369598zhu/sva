package io.renren.modules.inspection.controller;

import java.io.*;
import java.util.*;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.inspection.entity.DevicePicEntity;
import io.renren.modules.inspection.service.DevicePicService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-26 12:20:16
 */
@RestController
@RequestMapping("inspection/devicepic")
public class DevicePicController {
    @Autowired
    private DevicePicService devicePicService;

    @GetMapping("pic.png")
    public void qrcode(HttpServletResponse response, String uuid)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/png");

        DevicePicEntity pic = devicePicService.selectByGuid(uuid);

        ServletOutputStream out = response.getOutputStream();
        out.write(pic.getData());
        IOUtils.closeQuietly(out);
        response.setContentLength(pic.getData().length);
    }

    @PostMapping("/upload")
    @RequiresPermissions("sys:appupgrade:upload")
    public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, Integer deviceId)throws Exception{
        if(deviceId == null){
            return R.error(400,"参数错误，上传失败。");
        }
        if(file != null) {
            DevicePicEntity pic = new DevicePicEntity();
            pic.setDeviceId(deviceId);
            pic.setData(file.getBytes());
            pic.setGuid(UUID.randomUUID().toString());
            pic.setCreateTime(new Date());
            if(devicePicService.insert(pic)){
                return R.ok();
            }
        }

        return R.error(500, "上传失败，服务器内部错误。");
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:devicepic:list")
    public R list(@RequestParam Map<String, Object> params){
        String deviceId = (String)params.get("deviceId");
        List<Map<String, Object>> picEntities = devicePicService.selectByDeviceId(Integer.valueOf(deviceId));

        return R.ok().put("piclist", picEntities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:devicepic:info")
    public R info(@PathVariable("id") Integer id){
			DevicePicEntity devicePic = devicePicService.selectById(id);

        return R.ok().put("devicePic", devicePic);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:devicepic:save")
    public R save(@RequestBody DevicePicEntity devicePic){
			devicePicService.insert(devicePic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:devicepic:update")
    public R update(@RequestBody DevicePicEntity devicePic){
			devicePicService.updateById(devicePic);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{guid}")
    @RequiresPermissions("inspection:devicepic:delete")
    public R delete(@PathVariable("guid") String guid){
			devicePicService.deleteByGuid(guid);

        return R.ok();
    }

}
