package io.renren.modules.inspection.controller;

import java.io.*;
import java.util.*;

import io.renren.modules.inspection.entity.DevicePicEntity;
import io.renren.modules.inspection.entity.InspectionRandomResultMediaEntity;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.AppUpgradeEntity;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.inspection.entity.DeviceDocEntity;
import io.renren.modules.inspection.service.DeviceDocService;
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
@RequestMapping("inspection/devicedoc")
public class DeviceDocController extends AbstractController {
    @Autowired
    private DeviceDocService deviceDocService;

    @GetMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response, String uuid)throws ServletException, IOException {
        DeviceDocEntity doc = deviceDocService.selectByGuid(uuid);
        if(doc != null){
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(doc.getFilename().getBytes("utf-8"),"ISO-8859-1"));
        }

        ServletOutputStream out = response.getOutputStream();
        out.write(doc.getData());
        IOUtils.closeQuietly(out);
        response.setContentLength(doc.getData().length);

    }

    @PostMapping("/upload")
    @RequiresPermissions("inspection:devicedoc:upload")
    public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, Integer category, Integer deviceId)throws Exception{
        if(deviceId == null){
            return R.error(400,"参数错误，上传失败。");
        }
        if(file != null) {
            DeviceDocEntity doc = new DeviceDocEntity();
            doc.setCategory(category);
            doc.setFilename(file.getOriginalFilename());
            doc.setDeviceId(deviceId);
            doc.setData(file.getBytes());
            doc.setGuid(UUID.randomUUID().toString());
            doc.setCreateTime(new Date());
            doc.setUserId(getUserId());
            if(deviceDocService.insert(doc)){
                return R.ok();
            }
        }

        return R.error(500, "上传失败，服务器内部错误。");
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:devicedoc:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceDocService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:devicedoc:info")
    public R info(@PathVariable("id") Integer id){
			DeviceDocEntity deviceDoc = deviceDocService.selectById(id);

        return R.ok().put("deviceDoc", deviceDoc);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:devicedoc:save")
    public R save(@RequestBody DeviceDocEntity deviceDoc){
			deviceDocService.insert(deviceDoc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:devicedoc:update")
    public R update(@RequestBody DeviceDocEntity deviceDoc){
			deviceDocService.updateById(deviceDoc);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{uuid}")
    @RequiresPermissions("inspection:devicedoc:delete")
    public R delete(@PathVariable("uuid") String uuid){
			deviceDocService.deleteByGuid(uuid);

        return R.ok();
    }

}
