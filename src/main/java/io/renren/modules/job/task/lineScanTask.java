package io.renren.modules.job.task;

import io.renren.modules.inspection.service.InspectionLinePublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("lineScanTask")
public class lineScanTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private InspectionLinePublishService linePublishService;

    public void test(String params) {

    }

}
