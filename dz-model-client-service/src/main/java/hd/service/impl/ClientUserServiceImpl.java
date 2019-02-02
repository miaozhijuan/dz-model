package hd.service.impl;

import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.config.ToSearchResult;
import hd.entity.ClientUser;
import hd.mapper.ClientUserMapper;
import hd.service.ClientUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;

/**
 * 客户端用户接口实现层
 * 
 * @author mao
 *
 */
@Service
public class ClientUserServiceImpl implements ClientUserService {

	@Autowired
	private ClientUserMapper clientUserMapper;

	@Override
	public int insertSelective(ClientUser record) {
		return clientUserMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return clientUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ClientUser record) {
		return clientUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ClientUser selectByPrimaryKey(String id) {
		return clientUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public ClientUser selectByPhone(String phone) {
		return clientUserMapper.selectByPhone(phone);
	}

	@Override
	public SearchResult<ClientUser> getSearchResult(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return new ToSearchResult<ClientUser>().getSearchResult(clientUserMapper.list(scs));
	}

	@Override
	public List<ClientUser> list(SearchConditions scs) {
		if (null != scs.getPage()) {
			PageHelper.startPage(scs.getPage().getPageNum(), scs.getPage().getPageSize());
		}
		return clientUserMapper.list(scs);
	}
}