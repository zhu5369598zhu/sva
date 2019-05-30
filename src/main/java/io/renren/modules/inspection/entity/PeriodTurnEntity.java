package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 *
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@TableName("tb_period_turn")
public class PeriodTurnEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 周期id
     */
    private Long periodId;
    /**
     * 轮次id
     */
    private Long turnId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 获取:周期id
     */
    public Long getId() { return this.id; };
    /**
     * 设置:周期id
     */
    public void setId(Long id) { this.id = id; };
    /**
     * 获取:轮次id
     */
    public Long getTurnId() { return this.turnId; };
    /**
     * 设置:轮次id
     */
    public void setTurnId(Long turnId) { this.turnId = turnId; };
    /**
     * 获取:周期id
     */
    public Long getPeriodId() { return this.periodId; };
    /**
     * 设置:周期id
     */
    public void setPeriodId(Long periodId) { this.periodId = periodId; };
    /**
     * 获取:创建时间
     */
    public Date getCreateTime() { return this.createTime; };
    /**
     * 设置:创建时间
     */
    public void setCreateTime(Date createTime) { this.createTime = createTime; };
}
