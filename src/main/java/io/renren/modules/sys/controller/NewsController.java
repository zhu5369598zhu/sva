package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.NewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 消息表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-10 08:42:48
 */
@RestController
@RequestMapping("sys/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:news:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = newsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{newsId}")
    @RequiresPermissions("sys:news:info")
    public R info(@PathVariable("newsId") Integer newsId){
			NewsEntity news = newsService.selectById(newsId);

        return R.ok().put("news", news);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:news:save")
    public R save(@RequestBody NewsEntity news){
			newsService.insert(news);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:news:update")
    public R update(@RequestBody NewsEntity news){
			newsService.updateById(news);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:news:delete")
    public R delete(@RequestBody Integer[] newsIds){
			newsService.deleteBatchIds(Arrays.asList(newsIds));

        return R.ok();
    }

}
