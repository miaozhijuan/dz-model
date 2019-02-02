package hd.utils;

import hd.entity.SysPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单工具类
 * 
 * @author mao
 *
 */
public class MenuUtil {
	/**
	 * 获取根目录集合
	 * 
	 * @param list
	 * @return
	 */
	public static List<SysPermission> getSysMenuRooList(List<SysPermission> list) {
		List<SysPermission> rooList = new ArrayList<SysPermission>();
		for (SysPermission sysPermission : list) {
			if (isRoot(list, sysPermission)) {
				rooList.add(sysPermission);
			}
		}
		return rooList;
	}

	/**
	 * 判断是否是根目录
	 * 
	 * @param list
	 * @param sysPermission
	 * @return
	 */
	public static boolean isRoot(List<SysPermission> list, SysPermission sysPermission) {
		boolean falg = true;
		for (SysPermission menu : list) {
			if (sysPermission.getParentId().equals(menu.getId())) {
				falg = false;
				break;
			}
		}
		return falg;
	}

	/**
	 * 判断是否是父级目录
	 * 
	 * @param list
	 * @param sysPermission
	 * @return
	 */
	public static boolean isParent(List<SysPermission> list, SysPermission sysPermission) {
		boolean falg = false;
		for (SysPermission menu : list) {
			if (sysPermission.getId().equals(menu.getParentId())) {
				falg = true;
				break;
			}
		}
		return falg;
	}
}