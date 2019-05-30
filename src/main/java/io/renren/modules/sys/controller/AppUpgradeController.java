package io.renren.modules.sys.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.utils.QrCodeUtils;
import io.renren.modules.inspection.entity.InspectionRandomResultMediaEntity;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.AppUpgradeEntity;
import io.renren.modules.sys.service.AppUpgradeService;
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
 * @date 2019-04-18 12:45:52
 */
@RestController
@RequestMapping("sys/appupgrade")
public class AppUpgradeController {
    @Autowired
    private AppUpgradeService appUpgradeService;


    @GetMapping("qrcode.png")
    public void qrcode(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/png");

        AppUpgradeEntity app = appUpgradeService.selectById(1);

        ServletOutputStream out = response.getOutputStream();
        out.write(app.getAppQrcode());
        IOUtils.closeQuietly(out);
        response.setContentLength(app.getAppQrcode().length);
    }

    @GetMapping("app.apk")
    public void download(HttpServletResponse response)throws ServletException, IOException {
        AppUpgradeEntity app = appUpgradeService.selectById(1);
        if(app != null){
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(app.getAppFilename().getBytes("utf-8"),"ISO-8859-1"));
        }

        ServletOutputStream out = response.getOutputStream();
        out.write(app.getAppFile());
        IOUtils.closeQuietly(out);
        response.setContentLength(app.getAppFile().length);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:appupgrade:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appUpgradeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:appupgrade:info")
    public R info(@PathVariable("id") Integer id){
        AppUpgradeEntity appUpgrade = appUpgradeService.selectById(id);
        appUpgrade.setAppFile(null);
        appUpgrade.setAppQrcode(null);
        return R.ok().put("appUpgrade", appUpgrade);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:appupgrade:save")
    public R save(@RequestBody AppUpgradeEntity appUpgrade){
			appUpgradeService.insert(appUpgrade);

        return R.ok();
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    @RequiresPermissions("sys:appupgrade:upload")
    public R upload(HttpServletRequest request,  @RequestParam("file") MultipartFile file, Integer isDomain, String localhost, String domain)throws Exception{
        AppUpgradeEntity app = new AppUpgradeEntity();
        app.setId(1);
        app.setIsDomain(isDomain);
        app.setLocalhost(localhost);
        app.setDomain(domain);
        StringBuffer url = new StringBuffer("http://");
        if (app.getIsDomain().equals(0)){
            url.append(app.getLocalhost());
        }else{
            url.append(app.getDomain());
        }
        url.append("/sva/sys/appupgrade/app.apk");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        QrCodeUtils.encode(url.toString(), baos);
        app.setAppQrcode(baos.toByteArray());
        System.out.println(url.toString());
        if(file != null) {
            try{
                File toFile = null;
                if(file.equals("")||file.getSize()<=0){
                    file = null;
                }else {
                    InputStream ins = null;
                    ins = file.getInputStream();
                    toFile = new File(file.getOriginalFilename());
                    inputStreamToFile(ins, toFile);
                    ins.close();
                }
                ApkFile apk = new ApkFile(toFile);
                ApkMeta apkMeta = apk.getApkMeta();
                app.setAppFile(file.getBytes());
                app.setAppFilename(file.getOriginalFilename());
                app.setAppFilesize(file.getSize());
                app.setAppVersion(apkMeta.getVersionName());
                app.setAppVersionCode(apkMeta.getVersionCode());
                toFile.delete();
                baos.close();
            }catch (Exception e) {
                e.printStackTrace();

                return R.error(400,"apk文件格式错误，不能识别。");
            }
        }
        app.setCreateTime(new Date());

        appUpgradeService.updateById(app);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:appupgrade:update")
    public R update(@RequestBody AppUpgradeEntity appUpgrade) throws Exception{
        StringBuffer url = new StringBuffer("http://");
        if (appUpgrade.getIsDomain().equals(0)){
            url.append(appUpgrade.getLocalhost());
        }else{
            url.append(appUpgrade.getDomain());
        }
        url.append("/sva/sys/appupgrade/app.apk");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        QrCodeUtils.encode(url.toString(), baos);
        appUpgrade.setAppQrcode(baos.toByteArray());
        System.out.println(url.toString());
        appUpgradeService.updateById(appUpgrade);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:appupgrade:delete")
    public R delete(@RequestBody Integer[] ids){
			appUpgradeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
