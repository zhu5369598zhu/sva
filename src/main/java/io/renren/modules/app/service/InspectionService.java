package io.renren.modules.app.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface InspectionService extends IService<InspectionLineEntity> {

    public HashMap<String, Object> download(SysUserEntity userEntity, String padMac);

}
