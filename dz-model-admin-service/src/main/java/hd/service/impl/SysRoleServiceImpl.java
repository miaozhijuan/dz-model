package hd.service.impl;

import hd.common.base.ZtreeNode;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.config.ToSearchResult;
import hd.entity.SysRole;
import hd.entity.SysUserRoleKey;
import hd.mapper.SysRoleMapper;
import hd.mapper.SysUserRoleMapper;
import hd.service.SysRoleService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;

/**
 * 系统角色业务实现层
 * 
 * @author mao
 *
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public int insertSelective(SysRole record) {
		return sysRoleMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return sysRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRole record) {
		return sysRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysRole selectByPrimaryKey(String id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public SearchResult<SysRole> getSearchResult(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return new ToSearchResult<SysRole>().getSearchResult(sysRoleMapper.list(scs));
	}

	@Override
	public List<SysRole> list(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return sysRoleMapper.list(scs);
	}

	@Override
	public List<ZtreeNode> allotRole(String userId) {
		return sysRoleMapper.allotRole(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void saveRole(String userId, String roleIds) {
		SysUserRoleKey sysUserRoleKey = new SysUserRoleKey();
		sysUserRoleKey.setUserId(userId);
		sysUserRoleMapper.deleteByPrimaryKey(sysUserRoleKey);
		JSONArray jsonArray = JSONArray.parseArray(roleIds);
		if (null != jsonArray && !jsonArray.isEmpty()) {
			List<SysUserRoleKey> list = new ArrayList<SysUserRoleKey>();
			for (Object roleId : jsonArray) {
				sysUserRoleKey = new SysUserRoleKey();
				sysUserRoleKey.setRoleId(roleId.toString());
				sysUserRoleKey.setUserId(userId);
				list.add(sysUserRoleKey);
			}
			sysUserRoleMapper.insertList(list);
		}
	}
}