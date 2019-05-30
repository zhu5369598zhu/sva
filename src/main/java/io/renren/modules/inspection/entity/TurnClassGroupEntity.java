package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@TableName("tb_turn_class_group")
public class TurnClassGroupEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 轮次id
     */
    private Long turnId;
    /**
     * 获取:班组id
     */
    private Long classGroupId;
    /**
     * 设置:id
     */
    public Long getId() { return this.id; };
    /**
     * 获取:id
     */
    public void setId(Long id) { this.id = id; };
    /**
     * 设置:轮次id
     */
    public Long getTurnId() { return this.turnId; };
    /**
     * 获取:轮次id
     */
    public void setTurnId(Long turnId) { this.turnId = turnId; };
    /**
     * 设置:班组id
     */
    public Long getClassGroupId() { return this.classGroupId; };
    /**
     * 获取:班组id
     */
    public void setClassGroupId(Long classGroupId) { this.classGroupId = classGroupId; };

}
