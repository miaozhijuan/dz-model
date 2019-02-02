package hd.controller;

import hd.common.base.PcUserVo;
import hd.common.finals.FinalUtil;
import hd.common.tools.BodyMsg;
import hd.common.tools.IdUtil;
import hd.common.tools.IpUtil;
import hd.config.BaseController;
import hd.entity.Goods;
import hd.entity.Order;
import hd.entity.OrderDetails;
import hd.service.GoodsLockService;
import hd.service.GoodsService;
import hd.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 订单接口
 * 
 * @author mao
 *
 */
@Api(value = "订单", tags = { "订单接口" })
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Reference
	private GoodsService goodsService;

	@Reference
	private OrderService orderService;

	@Reference
	private GoodsLockService goodsLockSercice;

	/**
	 * 添加商品信息
	 * 
	 * @param token
	 * @param name
	 * @param price
	 * @param amount
	 * @return
	 */
	@ApiOperation(value = "添加订单信息", notes = "添加订单信息", httpMethod = FinalUtil.HTTP_POST, response = BodyMsg.class)
	@RequestMapping(value = "/addOrder.do", method = RequestMethod.POST)
	public BodyMsg register(HttpServletRequest request, @ApiParam(required = true, name = "token", value = "用户标识") @RequestHeader(required = true, value = "token") String token,
			@ApiParam(required = true, name = "goodsId", value = "商品ID") @RequestParam(required = true, value = "goodsId") String goodsId,
			@ApiParam(required = true, name = "price", value = "商品单价(分)") @RequestParam(required = true, value = "price") Integer price,
			@ApiParam(required = true, name = "amount", value = "商品数量") @RequestParam(required = true, value = "amount") Integer amount,
			@ApiParam(required = true, name = "discountAmount", value = "优惠金额") @RequestParam(required = true, value = "discountAmount") Integer discountAmount) {
		BodyMsg bodyMsg = new BodyMsg();
		PcUserVo pcUserVo = getPcUserByToken(token);
		goodsLockSercice.openLock();
		Goods goods = goodsService.selectByPrimaryKey(goodsId);
		if (null == goods || 1 != goods.getOnline() || 1 != goods.getStatus()) {
			goodsLockSercice.releaseLock();
			bodyMsg.isFail("商品信息异常");
			return bodyMsg;
		}
		if (goods.getAmount() < amount) {
			goodsLockSercice.releaseLock();
			bodyMsg.isFail("商品库存不够");
			return bodyMsg;
		}
		// 订单信息
		Order order = new Order();
		order.setId(IdUtil.getUUID());
		order.setIpAddress(IpUtil.getIpAddress(request));
		order.setUserId(pcUserVo.getId());
		order.setSellerId(goods.getUserId());
		order.setDiscountAmount(discountAmount);
		order.setTotalAmount(price * amount);
		order.setStatus(FinalUtil.BYTE_0);
		order.setCreateTime(new Date());
		// 订单详情信息
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setId(IdUtil.getUUID());
		orderDetails.setOrderId(order.getId());
		orderDetails.setGoodsId(goodsId);
		orderDetails.setPrice(price);
		orderDetails.setAmount(amount);
		orderService.addOrder(order, orderDetails);
		goodsLockSercice.releaseLock();
		bodyMsg.isSuccess();
		return bodyMsg;
	}
}