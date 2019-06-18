package io.renren.modules.management.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.management.entity.OrderDefectiveEntity;

public interface OrderDefectService extends IService<OrderDefectiveEntity>{

	PageUtils queryPage(Map<String, Object> params);

	OrderDefectiveEntity selectBydefectiveorderId(Integer resultId);

	
	
}
