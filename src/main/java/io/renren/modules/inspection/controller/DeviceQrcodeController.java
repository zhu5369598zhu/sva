package io.renren.modules.inspection.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.inspection.entity.DeviceQrcodeEntity;
import io.renren.modules.inspection.service.DeviceQrcodeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

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
@RequestMapping("inspection/deviceqrcode")
public class DeviceQrcodeController {
    @Autowired
    private DeviceQrcodeService deviceQrcodeService;

    @GetMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response, Integer deviceId)throws ServletException, IOException {
        DeviceQrcodeEntity qrcode = deviceQrcodeService.selectByDeviceId(deviceId);
        if(qrcode != null){
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + deviceId.toString() + "qrcode.png");
            ServletOutputStream out = response.getOutputStream();
            out.write(qrcode.getData());
            IOUtils.closeQuietly(out);
            response.setContentLength(qrcode.getData().length);
        }else{
            response.setContentLength(0);
        }



    }
    @GetMapping("qrcode.png")
    public void qrcode(HttpServletResponse response, Integer deviceId)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/png");

        DeviceQrcodeEntity qrcode = deviceQrcodeService.selectByDeviceId(deviceId);
        if(qrcode != null){
            ServletOutputStream out = response.getOutputStream();
            out.write(qrcode.getData());
            IOUtils.closeQuietly(out);
            response.setContentLength(qrcode.getData().length);
        }else{
            response.setContentLength(0);
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:deviceqrcode:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceQrcodeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:deviceqrcode:info")
    public R info(@PathVariable("id") Integer id){
			DeviceQrcodeEntity deviceQrcode = deviceQrcodeService.selectById(id);

        return R.ok().put("deviceQrcode", deviceQrcode);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:deviceqrcode:save")
    public R save(@RequestBody DeviceQrcodeEntity deviceQrcode){
			deviceQrcodeService.insert(deviceQrcode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:deviceqrcode:update")
    public R update(@RequestBody DeviceQrcodeEntity deviceQrcode){
			deviceQrcodeService.updateById(deviceQrcode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:deviceqrcode:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceQrcodeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
