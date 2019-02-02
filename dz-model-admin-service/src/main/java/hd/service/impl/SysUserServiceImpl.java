package hd.service.impl;

import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.config.ToSearchResult;
import hd.entity.SysUser;
import hd.mapper.SysUserMapper;
import hd.service.SysUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;

/**
 * 系统用户业务实现层
 * 
 * @author mao
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public int insertSelective(SysUser record) {
		return sysUserMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return sysUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysUser selectByPrimaryKey(String id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public SearchResult<SysUser> getSearchResult(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return new ToSearchResult<SysUser>().getSearchResult(sysUserMapper.list(scs));
	}

	@Override
	public List<SysUser> list(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return sysUserMapper.list(scs);
	}
}