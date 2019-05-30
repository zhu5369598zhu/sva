package io.renren.modules.setting.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.setting.entity.BaseTurnEntity;
import io.renren.modules.setting.service.BaseTurnService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:38:32
 */
@RestController
@RequestMapping("setting/baseturn")
public class BaseTurnController {
    @Autowired
    private BaseTurnService baseTurnService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:baseturn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseTurnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:baseturn:info")
    public R info(@PathVariable("id") Integer id){
			BaseTurnEntity baseTurn = baseTurnService.selectById(id);

        return R.ok().put("baseTurn", baseTurn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("setting:baseturn:save")
    public R save(@RequestBody BaseTurnEntity baseTurn){
			baseTurnService.insert(baseTurn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("setting:baseturn:update")
    public R update(@RequestBody BaseTurnEntity baseTurn){
			baseTurnService.updateById(baseTurn);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("setting:baseturn:delete")
    public R delete(@RequestBody Integer[] ids){
			baseTurnService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
