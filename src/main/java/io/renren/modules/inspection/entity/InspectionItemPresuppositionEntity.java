package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("tb_inspection_item_presupposition")
public class InspectionItemPresuppositionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 巡检项id
     */
    private Integer itemId;
    /**
     * guid
     */
    private String guid;
    /**
     * 名称
     */
    private String name;
    /**
     * 异常等级id
     */
    private Integer exceptionId;
    /**
     * 异常等级
     */
    @TableField(exist = false)
    private String exceptionName;

    /**
     * 获取：id
     */
    public Integer getId() { return id; }
    /**
     * 设置：巡检项id
     */
    public void setId(Integer id) { this.id = id; }
    /**
     * 获取：巡检项id
     */
    public Integer getItemId() { return itemId; }
    /**
     * 设置：巡检项id
     */
    public void setItemId(Integer itemId) { this.itemId = itemId; }
    /**
     * 获取：名称
     */
    public String getName() { return name; }
    /**
     * 设置：名称
     */
    public void setName(String name) { this.name = name; }
    /**
     * 设置：guid
     */
    public String getGuid() { return guid; }
    /**
     * 设置：guid
     */
    public void setGuid(String guid) { this.guid = guid; }
    /**
     * 获取：异常id
     */
    public Integer getExceptionId() { return exceptionId; }
    /**
     * 设置：异常id
     */
    public void setExceptionId(Integer exceptionId) { this.exceptionId = exceptionId; }

    /**
     * 获取：异常
     */
    public String getExceptionName() { return exceptionName; }
    /**
     * 设置：异常
     */
    public void setExceptionName(String exceptionName) { this.exceptionName = exceptionName; }

}
