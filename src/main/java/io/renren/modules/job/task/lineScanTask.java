package io.renren.modules.job.task;

import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.InspectionPeriodEntity;
import io.renren.modules.inspection.service.InspectionLinePublishService;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.inspection.service.InspectionPeriodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component("lineScanTask")
public class lineScanTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    InspectionLineService lineService;
    @Autowired
    InspectionPeriodService periodService;

    public void test() {
        HashMap lineParams = new HashMap();
        lineParams.put("is_publish", 1);
        List<InspectionLineEntity> lines = lineService.selectByMap(lineParams);
        for (InspectionLineEntity line:lines) {
            HashMap periodParams = new HashMap();
            periodParams.put("line_id", line.getId());
            List<InspectionPeriodEntity> periods = periodService.selectByMap(periodParams);
            for (InspectionPeriodEntity period:periods) {
                if(period.getFrequency() == 1){
                    
                }
                if(period.getFrequency() == 7){

                }
                if(period.getFrequency() == 30){

                }
            }
        }
    }

}
