package io.renren.modules.management.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import io.renren.modules.inspection.entity.InspectionResultEntity;
import io.renren.modules.management.entity.OrderDefectiveEntity;

@Mapper
public interface OrderDefectDao extends BaseMapper<OrderDefectiveEntity>{

	List<InspectionResultEntity> selectResultList(Page<InspectionResultEntity> page,
			InspectionResultEntity resultEntity);

	OrderDefectiveEntity selectBydefectiveorderId(Integer resultId);

	
} 
