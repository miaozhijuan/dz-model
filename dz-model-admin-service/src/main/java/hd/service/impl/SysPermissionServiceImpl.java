package hd.service.impl;

import hd.common.base.ZtreeNode;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.config.ToSearchResult;
import hd.entity.SysPermission;
import hd.entity.SysRolePermissionKey;
import hd.mapper.SysPermissionMapper;
import hd.mapper.SysRolePermissionMapper;
import hd.service.SysPermissionService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;

/**
 * 系统权限业务实现层
 * 
 * @author mao
 *
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;

	@Override
	public int insertSelective(SysPermission record) {
		return sysPermissionMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return sysPermissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysPermission record) {
		return sysPermissionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysPermission selectByPrimaryKey(String id) {
		return sysPermissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public SearchResult<SysPermission> getSearchResult(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return new ToSearchResult<SysPermission>().getSearchResult(sysPermissionMapper.list(scs));
	}

	@Override
	public List<SysPermission> list(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return sysPermissionMapper.list(scs);
	}

	@Override
	public List<SysPermission> menuList(SearchConditions scs) {
		return sysPermissionMapper.menuList(scs);
	}

	@Override
	public List<ZtreeNode> allotPermission(String roleId) {
		return sysPermissionMapper.allotPermission(roleId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void savePermission(String roleId, String permissionIds) {
		SysRolePermissionKey sysRolePermissionKey = new SysRolePermissionKey();
		sysRolePermissionKey.setRoleId(roleId);
		sysRolePermissionMapper.deleteByPrimaryKey(sysRolePermissionKey);
		JSONArray jsonArray = JSONArray.parseArray(permissionIds);
		if (null != jsonArray && !jsonArray.isEmpty()) {
			List<SysRolePermissionKey> list = new ArrayList<SysRolePermissionKey>();
			for (Object permissionId : jsonArray) {
				sysRolePermissionKey = new SysRolePermissionKey();
				sysRolePermissionKey.setPermissionId(permissionId.toString());
				sysRolePermissionKey.setRoleId(roleId);
				list.add(sysRolePermissionKey);
			}
			sysRolePermissionMapper.insertList(list);
		}
	}

	@Override
	public List<ZtreeNode> zTreeNodeList() {
		return sysPermissionMapper.zTreeNodeList();
	}
}