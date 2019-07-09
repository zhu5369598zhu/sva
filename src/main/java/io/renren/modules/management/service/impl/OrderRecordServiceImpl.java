package io.renren.modules.management.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.management.dao.OrderRecordDao;
import io.renren.modules.management.entity.OrderRecordEntity;
import io.renren.modules.management.service.OrderRecordService;


@Service("orderRecordService")
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordDao, OrderRecordEntity> implements OrderRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String orderNumber = (String)params.get("orderNumber");
        Page<OrderRecordEntity> page = this.selectPage(
                new Query<OrderRecordEntity>(params).getPage(),
                new EntityWrapper<OrderRecordEntity>()
                .eq(StringUtils.isNotBlank(orderNumber),"order_number", orderNumber)
        );
        
        for(OrderRecordEntity orderRecord: page.getRecords()) {
        	if(orderRecord.getOrderPeopleId() ==1) {
        		orderRecord.setOrderPeopleName("工单操作人");
        	}else if(orderRecord.getOrderPeopleId() ==2) {
        		orderRecord.setOrderPeopleName("工单受理人");
        	}else if(orderRecord.getOrderPeopleId() ==3) {
        		orderRecord.setOrderPeopleName("工单审核人");
        	}
        }

        return new PageUtils(page);
    }

}
