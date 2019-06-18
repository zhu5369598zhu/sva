package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.NewsDao;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.NewsService;


@Service("newsService")
public class NewsServiceImpl extends ServiceImpl<NewsDao, NewsEntity> implements NewsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String user_id = params.get("user_id").toString(); 
        Page<NewsEntity> page = this.selectPage(
                new Query<NewsEntity>(params).getPage(),
                new EntityWrapper<NewsEntity>()
                .eq(StringUtils.isNotBlank(user_id),"user_id", user_id)
                .notLike("news_type", "0")
        );
        for(NewsEntity newsEntity: page.getRecords()) {
        	if(newsEntity.getNewsType() ==1) { 
        		newsEntity.setNewsTypeName("待确认班长日志");
        	}else if(newsEntity.getNewsType()==2) {
        		newsEntity.setNewsTypeName("驳回的班长日志"); 
        	}else if(newsEntity.getNewsType()==3) {
        		newsEntity.setNewsTypeName("已下发待受理工单日志");
        	}else if(newsEntity.getNewsType() ==4) {
        		newsEntity.setNewsTypeName("已下发待受理的工单日志被驳回");
        	}else if(newsEntity.getNewsType() ==5) {
        		newsEntity.setNewsTypeName("已受理待上报工单日志"); 
        	}else if(newsEntity.getNewsType() ==6) {
        		newsEntity.setNewsTypeName("已上报待确认工单日志"); 
        	}else if(newsEntity.getNewsType() ==7) {
        		newsEntity.setNewsTypeName("已确认待完结工单日志"); 
        	}else if(newsEntity.getNewsType() ==8) {
        		newsEntity.setNewsTypeName("已确认待完结工单日志被打回"); 
        	}
        }

        return new PageUtils(page);
    }

}
