package com.nebula.rbac.admin.service.impl;

import com.nebula.common.utils.PageQuery;
import com.nebula.rbac.admin.mapper.SysRoleDeptMapper;
import com.nebula.rbac.admin.mapper.SysRoleMapper;
import com.nebula.rbac.admin.model.dto.RoleDTO;
import com.nebula.rbac.admin.model.entity.SysRole;
import com.nebula.rbac.admin.model.entity.SysRoleDept;
import com.nebula.rbac.admin.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author feifeixia
 * @since 2017-10-29
 */
@SuppressWarnings("unchecked")
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    @Override
    public Boolean insertRole(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.insert(sysRole);
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(sysRole.getRoleId());
        roleDept.setDeptId(roleDto.getRoleDeptId());
        sysRoleDeptMapper.insert(roleDept);
        return true;
    }

    @Override
    public IPage<RoleDTO> selectPageVo(final PageQuery pageQuery, final String roleName) {
        return sysRoleMapper.selectPageVo(pageQuery, roleName);
    }

    /**
     * 分页查角色列表
     *
     * @param pageQuery   查询条件
     * @param wrapper wapper
     * @return page
     */
    @Override
    public Page selectwithDeptPage(PageQuery pageQuery, QueryWrapper wrapper) {
        return (Page) sysRoleMapper.selectPage(pageQuery, wrapper);
    }

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateRoleById(RoleDTO roleDto) {
        //删除原有的角色部门关系
        SysRoleDept condition = new SysRoleDept();
        condition.setRoleId(roleDto.getRoleId());
        sysRoleDeptMapper.delete(new UpdateWrapper<>(condition));

        //更新角色信息
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.updateById(sysRole);

        //维护角色部门关系
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(sysRole.getRoleId());
        roleDept.setDeptId(roleDto.getRoleDeptId());
        sysRoleDeptMapper.insert(roleDept);
        return true;
    }

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectListByDeptId(Integer deptId) {
        return sysRoleMapper.selectListByDeptId(deptId);
    }
}
