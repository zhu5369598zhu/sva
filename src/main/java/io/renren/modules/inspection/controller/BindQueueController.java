package io.renren.modules.inspection.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.BindQueueEntity;
import io.renren.modules.inspection.service.BindQueueService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-03-15 11:43:31
 */
@RestController
@RequestMapping("inspection/bindqueue")
public class BindQueueController {
    @Autowired
    private BindQueueService bindQueueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:bindqueue:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bindQueueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:bindqueue:info")
    public R info(@PathVariable("id") Integer id){
			BindQueueEntity bindQueue = bindQueueService.selectById(id);

        return R.ok().put("bindQueue", bindQueue);
    }

    /**
     * pda绑定
     */
    @RequestMapping("/bindpda")
    @RequiresPermissions("inspection:pda:info")
    public R bindpda(){
        BindQueueEntity pda = bindQueueService.selectOne(
                new EntityWrapper<BindQueueEntity>()
                .eq("type", "pda")
                .orderBy("create_time", false)

        );
        if (pda != null){
            bindQueueService.deleteById(pda.getId());
        }

        return R.ok().put("bind", pda);
    }

    /**
     * rfid绑定
     */
    @RequestMapping("/bindrfid")
    @RequiresPermissions("inspection:zone:info")
    public R bindrfid(){
        BindQueueEntity rfid = bindQueueService.selectOne(
                new EntityWrapper<BindQueueEntity>()
                        .eq("type", "rfid")
                        .orderBy("create_time", false)

        );
        if (rfid != null){
            bindQueueService.deleteById(rfid.getId());
        }

        return R.ok().put("bind", rfid);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:bindqueue:save")
    public R save(@RequestBody BindQueueEntity bindQueue){
			bindQueueService.insert(bindQueue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:bindqueue:update")
    public R update(@RequestBody BindQueueEntity bindQueue){
			bindQueueService.updateById(bindQueue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:bindqueue:delete")
    public R delete(@RequestBody Integer[] ids){
			bindQueueService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
