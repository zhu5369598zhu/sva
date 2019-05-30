package io.renren.modules.app.controller;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.inspection.entity.BindQueueEntity;
import io.renren.modules.inspection.service.BindQueueService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 巡检相关接口
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-11-17
 */
@RestController
@RequestMapping("/app/bind/")
@Api("APP绑定相关接口")
public class AppBindController {

    @Autowired
    private BindQueueService bindService;

    @Login
    @GetMapping("bindpda")
    @ApiOperation("绑定pda设备mac地址到机构")
    public R bindpda(@LoginUser SysUserEntity user, @RequestParam("pdamac") String pdaMac) {
        if (pdaMac.equals("")){
            return R.error(400,"绑定失败，pdaMac不能为空。");
        }
        BindQueueEntity pda = new BindQueueEntity();
        pda.setCreateTime(new Date());
        pda.setType("pda");
        pda.setContent(pdaMac);
        boolean isOk = bindService.insert(pda);

        if (isOk) {
            return R.ok();
        } else {
            return R.error(500,"绑定失败，服务器未知错误。");
        }
    }

    @Login
    @GetMapping("bindrfid")
    @ApiOperation("绑定rfid卡到巡区")
    public R bindrfid(@LoginUser SysUserEntity user, @RequestParam("rfid") String rfid) {
        if (rfid.equals("")){
            return R.error(400,"绑定失败，rfid不能为空。");
        }
        BindQueueEntity card = new BindQueueEntity();
        card.setCreateTime(new Date());
        card.setType("rfid");
        card.setContent(rfid);
        boolean isOk = bindService.insert(card);

        if (isOk) {
            return R.ok();
        } else {
            return R.error(500,"绑定失败，服务器未知错误。");
        }
    }
}
