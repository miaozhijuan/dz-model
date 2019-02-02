package hd.controller;

import hd.common.base.PcUserVo;
import hd.common.finals.FinalUtil;
import hd.common.tools.BodyMsg;
import hd.common.tools.IdUtil;
import hd.config.BaseController;
import hd.entity.Goods;
import hd.entity.GoodsWater;
import hd.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 用户接口
 * 
 * @author mao
 *
 */
@Api(value = "商品", tags = { "商品接口" })
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {

	@Reference
	private GoodsService goodsService;

	/**
	 * 添加商品信息
	 * 
	 * @param token
	 * @param name
	 * @param price
	 * @param amount
	 * @return
	 */
	@ApiOperation(value = "添加商品信息", notes = "添加商品信息", httpMethod = FinalUtil.HTTP_POST, response = BodyMsg.class)
	@RequestMapping(value = "/addGoods.do", method = RequestMethod.POST)
	public BodyMsg register(@ApiParam(required = true, name = "token", value = "用户标识") @RequestHeader(required = true, value = "token") String token,
			@ApiParam(required = true, name = "name", value = "商品名称") @RequestParam(required = true, value = "name") String name,
			@ApiParam(required = true, name = "price", value = "商品单价(分)") @RequestParam(required = true, value = "price") Integer price,
			@ApiParam(required = true, name = "amount", value = "商品数量") @RequestParam(required = true, value = "amount") Integer amount) {
		BodyMsg bodyMsg = new BodyMsg();
		PcUserVo pcUserVo = getPcUserByToken(token);
		// 商品信息
		Goods goods = new Goods();
		goods.setId(IdUtil.getUUID());
		goods.setUserId(pcUserVo.getId());
		goods.setName(name);
		goods.setPrice(price);
		goods.setAmount(amount);
		goods.setWithholdAmount(0);
		goods.setStatus(FinalUtil.BYTE_0);
		goods.setOnline(FinalUtil.BYTE_0);
		goods.setCreateTime(new Date());
		// 商品库存流水
		GoodsWater goodsWater = new GoodsWater();
		goodsWater.setId(IdUtil.getUUID());
		goodsWater.setGoodsId(goods.getId());
		goodsWater.setAmount(amount);
		goodsWater.setStatus(FinalUtil.BYTE_1);
		goodsWater.setCreateTime(new Date());
		goodsService.addGoods(goods, goodsWater);
		bodyMsg.isSuccess();
		return bodyMsg;
	}
}