package com.nebula.rbac.admin.service.impl;

import com.nebula.common.constants.CommonConstant;
import com.nebula.common.vo.MenuVO;
import com.nebula.rbac.admin.mapper.SysMenuMapper;
import com.nebula.rbac.admin.model.entity.SysMenu;
import com.nebula.rbac.admin.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author feifeixia
 * @since 2017-10-29
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuVO> findMenuByRoleName(String role) {
        return sysMenuMapper.findMenuByRoleName(role);
    }

    @Override
    public Boolean deleteMenu(Integer id) {
        // 删除当前节点
        SysMenu condition1 = new SysMenu();
        condition1.setId(id);
        condition1.setDelFlag(CommonConstant.STATUS_DEL);
        this.updateById(condition1);

        // 删除父节点为当前节点的节点
        SysMenu conditon2 = new SysMenu();
        conditon2.setParentId(id);
        SysMenu sysMenu = new SysMenu();
        sysMenu.setDelFlag(CommonConstant.STATUS_DEL);
        return this.update(sysMenu, new UpdateWrapper<>(conditon2));
    }

    @Override
    public Boolean updateMenuById(SysMenu sysMenu) {
        return this.updateById(sysMenu);
    }
}
