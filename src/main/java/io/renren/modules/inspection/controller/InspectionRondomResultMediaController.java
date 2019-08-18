package io.renren.modules.inspection.controller;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

import io.renren.common.utils.FftUtils;
import io.renren.modules.inspection.entity.FftChartEntity;
import io.renren.modules.inspection.entity.InspectionRandomResultEntity;
import io.renren.modules.inspection.entity.InspectionResultMediaEntity;
import io.renren.modules.inspection.service.InspectionRandomResultService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.inspection.entity.InspectionRandomResultMediaEntity;
import io.renren.modules.inspection.service.InspectionRandomResultMediaService;
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
 * @date 2019-03-15 11:43:31
 */
@RestController
@RequestMapping("inspection/inspectionrondomresultmedia")
public class InspectionRondomResultMediaController {
    @Autowired
    private InspectionRandomResultMediaService mediaService;

    @GetMapping("media.jpg")
    public void media(HttpServletResponse response, String uuid)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpg");

        InspectionRandomResultMediaEntity media = mediaService.selectByGuid(uuid);

        ServletOutputStream out = response.getOutputStream();
        out.write(media.getContent());
        IOUtils.closeQuietly(out);
        response.setContentLength(media.getContent().length);
    }

    @GetMapping("mp3")
    public void mp3(HttpServletRequest request, HttpServletResponse response, String uuid)throws ServletException, IOException {
        InspectionRandomResultMediaEntity media = mediaService.selectByGuid(uuid);
        try{

            long p = 0L;
            long toLength = 0L;
            long contentLength = 0L;
            int rangeSwitch = 0; //
            BufferedInputStream bis = null;
            long fileLength = media.getContent().length;

            // get file content
            InputStream ins = new ByteArrayInputStream(media.getContent());
            bis = new BufferedInputStream(ins);

            // tell the client to allow accept-ranges
            response.reset();
            response.setHeader("Accept-Ranges", "bytes");

            String rangBytes = "";
            String range = request.getHeader("Range");
            if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
                rangBytes = range.replaceAll("bytes=", "");
                if (rangBytes.endsWith("-")) {
                    rangeSwitch = 1;
                    p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                    contentLength = fileLength - p;
                } else {
                    rangeSwitch = 2;
                    String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                    String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                    p = Long.parseLong(temp1);
                    toLength = Long.parseLong(temp2);
                    contentLength = toLength - p + 1;
                }
            } else {
                contentLength = fileLength;
            }

            response.setHeader("Content-Length", new Long(contentLength).toString());

            // 断点开始
            // 响应的格式是:
            // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
            if (rangeSwitch == 1) {
                String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
                        .append(new Long(fileLength - 1).toString()).append("/")
                        .append(new Long(fileLength).toString()).toString();
                response.setHeader("Content-Range", contentRange);
                bis.skip(p);
            } else if (rangeSwitch == 2) {
                String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
                response.setHeader("Content-Range", contentRange);
                bis.skip(p);
            } else {
                String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
                        .append(fileLength).toString();
                response.setHeader("Content-Range", contentRange);
            }

            response.setHeader("Ohc-File-Size:", String.format("%d",media.getContent().length));

            OutputStream out = response.getOutputStream();
            int n = 0;
            long readLength = 0;
            int bsize = 1024;
            byte[] bytes = new byte[bsize];
            if (rangeSwitch == 2) {
                // 针对 bytes=27000-39000 的请求，从27000开始写数据
                while (readLength <= contentLength - bsize) {
                    n = bis.read(bytes);
                    readLength += n;
                    out.write(bytes, 0, n);
                }
                if (readLength <= contentLength) {
                    n = bis.read(bytes, 0, (int) (contentLength - readLength));
                    out.write(bytes, 0, n);
                }
            } else {

                while ((n = bis.read(bytes)) != -1) {
                    out.write(bytes, 0, n);
                }


            }
            out.flush();
            out.close();
            bis.close();
        } catch (IOException ie) {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("mp4")
    public void mp4(HttpServletRequest request, HttpServletResponse response, String uuid)throws ServletException, IOException {
        InspectionRandomResultMediaEntity media = mediaService.selectByGuid(uuid);
        try{

            long p = 0L;
            long toLength = 0L;
            long contentLength = 0L;
            int rangeSwitch = 0; //
            BufferedInputStream bis = null;
            long fileLength = media.getContent().length;

            // get file content
            InputStream ins = new ByteArrayInputStream(media.getContent());
            bis = new BufferedInputStream(ins);

            // tell the client to allow accept-ranges
            response.reset();
            response.setHeader("Accept-Ranges", "bytes");

            String rangBytes = "";
            String range = request.getHeader("Range");
            if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
                rangBytes = range.replaceAll("bytes=", "");
                if (rangBytes.endsWith("-")) {
                    rangeSwitch = 1;
                    p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                    contentLength = fileLength - p;
                } else {
                    rangeSwitch = 2;
                    String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                    String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                    p = Long.parseLong(temp1);
                    toLength = Long.parseLong(temp2);
                    contentLength = toLength - p + 1;
                }
            } else {
                contentLength = fileLength;
            }

            response.setHeader("Content-Length", new Long(contentLength).toString());

            // 断点开始
            // 响应的格式是:
            // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
            if (rangeSwitch == 1) {
                String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
                        .append(new Long(fileLength - 1).toString()).append("/")
                        .append(new Long(fileLength).toString()).toString();
                response.setHeader("Content-Range", contentRange);
                bis.skip(p);
            } else if (rangeSwitch == 2) {
                String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
                response.setHeader("Content-Range", contentRange);
                bis.skip(p);
            } else {
                String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
                        .append(fileLength).toString();
                response.setHeader("Content-Range", contentRange);
            }

            response.setHeader("Ohc-File-Size:", String.format("%d",media.getContent().length));

            OutputStream out = response.getOutputStream();
            int n = 0;
            long readLength = 0;
            int bsize = 1024;
            byte[] bytes = new byte[bsize];
            if (rangeSwitch == 2) {
                // 针对 bytes=27000-39000 的请求，从27000开始写数据
                while (readLength <= contentLength - bsize) {
                    n = bis.read(bytes);
                    readLength += n;
                    out.write(bytes, 0, n);
                }
                if (readLength <= contentLength) {
                    n = bis.read(bytes, 0, (int) (contentLength - readLength));
                    out.write(bytes, 0, n);
                }
            } else {

                while ((n = bis.read(bytes)) != -1) {
                    out.write(bytes, 0, n);
                }


            }
            out.flush();
            out.close();
            bis.close();
        } catch (IOException ie) {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mediaService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 加速度
     */
    @RequestMapping("/acc/{uuid}")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:info")
    public R acc(@PathVariable("uuid") String uuid){
        FftChartEntity fftChartEntity = null;
        InspectionRandomResultMediaEntity Media = mediaService.selectByGuid(uuid);
        if(Media != null && Media.getType().equals("data") && Media.getContent() != null){
            try{
                fftChartEntity = FftUtils.accFromByte(Media.getContent());
            }catch (IOException e){
                e.printStackTrace();
                return R.error(500,"上传数据格式错误，不能识别");
            }
        }

        return R.ok().put("chart", fftChartEntity);
    }

    /**
     * 速度
     */
    @RequestMapping("/speed/{uuid}")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:info")
    public R speed(@PathVariable("uuid") String uuid){
        FftChartEntity fftChartEntity = null;
        InspectionRandomResultMediaEntity Media = mediaService.selectByGuid(uuid);
        if(Media != null && Media.getType().equals("data") && Media.getContent() != null){
            try{
                fftChartEntity = FftUtils.speedFromByte(Media.getContent());
            }catch (IOException e){
                e.printStackTrace();
                return R.error(500,"上传数据格式错误，不能识别");
            }
        }

        return R.ok().put("chart", fftChartEntity);
    }

    /**
     * 位移
     */
    @RequestMapping("/distance/{uuid}")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:info")
    public R distance(@PathVariable("uuid") String uuid){
        FftChartEntity fftChartEntity = null;
        InspectionRandomResultMediaEntity Media = mediaService.selectByGuid(uuid);
        if(Media != null && Media.getType().equals("data") && Media.getContent() != null){
            try{
                fftChartEntity = FftUtils.distanceFromByte(Media.getContent());
            }catch (IOException e){
                e.printStackTrace();
                return R.error(500,"上传数据格式错误，不能识别");
            }
        }

        return R.ok().put("chart", fftChartEntity);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:info")
    public R info(@PathVariable("id") Integer id){
			InspectionRandomResultMediaEntity inspectionRandomResultMedia = mediaService.selectById(id);

        return R.ok().put("inspectionRondomResultMedia", inspectionRandomResultMedia);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:save")
    public R save(@RequestBody InspectionRandomResultMediaEntity inspectionRandomResultMedia){
        mediaService.insert(inspectionRandomResultMedia);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:update")
    public R update(@RequestBody InspectionRandomResultMediaEntity inspectionRandomResultMedia){
        mediaService.updateById(inspectionRandomResultMedia);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectionrondomresultmedia:delete")
    public R delete(@RequestBody Integer[] ids){
        mediaService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
