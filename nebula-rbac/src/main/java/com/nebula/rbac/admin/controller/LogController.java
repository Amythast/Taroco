package com.nebula.rbac.admin.controller;


import com.nebula.common.constants.CommonConstant;
import com.nebula.common.constants.RoleConst;
import com.nebula.common.entity.SysLog;
import com.nebula.common.exception.InvalidParamException;
import com.nebula.common.utils.PageQuery;
import com.nebula.common.web.BaseController;
import com.nebula.common.web.Response;
import com.nebula.common.web.annotation.RequireRole;
import com.nebula.rbac.admin.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author feifeixia
 * @since 2017-11-20
 */
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 分页查询日志信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/logPage")
    @RequireRole(RoleConst.ADMIN)
    public Page logPage(@RequestParam Map<String, Object> params) {
        final QueryWrapper<SysLog> query = new QueryWrapper<>();
        query.eq(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        final String typeKey = "type";
        if (params.containsKey(typeKey) && !ObjectUtils.isEmpty(params.get(typeKey))) {
            query.eq(typeKey, params.get(typeKey));
        }
        return (Page) sysLogService.page(new PageQuery<>(params), query);
    }

    /**
     * 根据ID
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    @RequireRole(RoleConst.ADMIN)
    public Response delete(@PathVariable Long id) {
        return Response.success(sysLogService.updateByLogId(id));
    }

    /**
     * 添加日志
     *
     * @param log 日志实体
     * @param result 错误信息
     */
    @PostMapping
    public void add(@Valid @RequestBody SysLog log, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidParamException(result.getAllErrors().get(0).getDefaultMessage());
        }
        sysLogService.save(log);
    }
}
