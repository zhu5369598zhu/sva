package io.renren.modules.inspection.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
import io.renren.modules.inspection.entity.PdaEntity;
import io.renren.modules.inspection.service.InspectionLinePublishService;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.inspection.service.PdaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:49
 */
@RestController
@RequestMapping("inspection/pda")
public class PdaController {
    @Autowired
    private PdaService pdaService;
    @Autowired
    private InspectionLinePublishService publishService;
    @Autowired
    private InspectionLineService lineService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:pda:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pdaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{pdaId}")
    @RequiresPermissions("inspection:pda:info")
    public R info(@PathVariable("pdaId") Integer pdaId){
			PdaEntity pda = pdaService.selectById(pdaId);

        return R.ok().put("pda", pda);
    }

    /**
     * 选择未绑定到巡区的所有设备
     */
    @RequestMapping("/selectunbind")
    @RequiresPermissions("inspection:device:list")
    public R selectunbind(@RequestParam Map<String, Object> params){
        String filterField = "";
        String filter = (String)params.get("filter");
        String key = (String)params.get("key");
        if (filter.equals("pdaName")) {
            filterField = "pda_name";
        }
        if (filter.equals("pdaMac")) {
            filterField = "pda_mac";
        }
        Long lineId = Long.valueOf((String)params.get("lineId"));
        List<PdaEntity> pdaEntityList = pdaService.findPdaUnBind(filterField, key, lineId);

        return R.ok().put("pdaList", pdaEntityList);
    }

    /**
     * 保存
     */
    @SysLog("保存PDA")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:pda:save")
    public R save(@RequestBody PdaEntity pda){
            pda.setCreateTime(new Date());
            pda.setGuid(UUID.randomUUID().toString());
            PdaEntity tmp = pdaService.selectByMac(pda.getPdaMac());
            if(tmp == null ){
                pdaService.insert(pda);
            }else{
                return R.error(400, "Mac已绑定过其它设备，不能再次绑定。");
            }

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改PDA")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:pda:update")
    public R update(@RequestBody PdaEntity pda){
        PdaEntity tmp = pdaService.selectByMac(pda.getPdaMac());
        if(tmp != null){
            if(tmp.getPdaId() == pda.getPdaId()){
                pdaService.updateById(pda);
            } else {
                return R.error(400, "Mac已绑定过其它设备，不能再次绑定。");
            }
        }else{
            pdaService.updateById(pda);
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除PDA")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:pda:delete")
    public R delete(@RequestBody Integer[] pdaIds){
        boolean isOk = false;
        for(Integer pdaId:pdaIds){
            PdaEntity pdaEntity = pdaService.selectById(pdaId);
            if(pdaEntity != null){
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("pda_id", pdaEntity.getPdaId());
                List<InspectionLinePublishEntity> publishEntities = publishService.selectByMap(params);
                if(publishEntities.size() > 0 ){
                    for(InspectionLinePublishEntity publish:publishEntities){
                        InspectionLineEntity line = lineService.selectById(publish.getLineId());
                        if(line != null && line.getIsDelete()==0 ){ // 绑定巡线并且巡线被没有删除
                            return R.error(400,"该设备已绑定到巡检线路" + line.getName() + "中，不能删除。");
                        }
                    }
                }
                pdaEntity.setIsDelete(1);
                isOk = pdaService.updateById(pdaEntity);
                if(!isOk){
                    break;
                }
            }
        }
        if(isOk){
            return R.ok();
        }else{
            return R.error(500,"服务器错误");
        }
    }

}
