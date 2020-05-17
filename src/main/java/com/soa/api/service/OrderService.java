package com.soa.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soa.api.controller.request.OrderRequest;
import com.soa.api.entity.Account;
import com.soa.api.entity.Order;
import com.soa.api.entity.OrderDetail;
import com.soa.api.entity.OrderDetailKey;
import com.soa.api.entity.Product;
import com.soa.api.entity.ShoppingCart;
import com.soa.api.entity.ShoppingCartKey;
import com.soa.api.entity.Status;
import com.soa.api.repository.AccountRepository;
import com.soa.api.repository.OrderDetailRepository;
import com.soa.api.repository.OrderRepository;
import com.soa.api.repository.ProductRepository;
import com.soa.api.repository.ShoppingCartRepository;

@Service
public class OrderService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	public List<ShoppingCart> findShoppingcartByUsername(String userName) {
		Account account = accountRepository.findByUserName(userName);

		List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByShoppingCartKeyAccountId(account.getId());

		shoppingCarts = validationListShoppingCart(shoppingCarts, account);

		return shoppingCarts;
	}

	public boolean addProdcutIntoShoppingCart(int id, String userName, int number) {
		Optional<Product> optProduct = productRepository.findById(id);

		Product product = optProduct.isPresent() ? optProduct.get() : null;
		Account account = accountRepository.findByUserName(userName);

		if (product != null) {
			if (product.getStatus() && product.getAmount() > 0) {
				ShoppingCart existProductIntoShoppingCart = shoppingCartRepository
						.findByShoppingCartKeyAccountIdAndShoppingCartKeyProductId(account.getId(), product.getId());
				ShoppingCart shoppingCart = null;
				if (existProductIntoShoppingCart == null) {
					ShoppingCartKey shoppingCartKey = new ShoppingCartKey(account.getId(), product.getId());
					int amount = number;

					if (product.getAmount() > 0 && (product.getAmount() < amount)) {
						amount = product.getAmount();
					} else if (product.getAmount() <= 0) {
						return false;
					}

					double unitPrice = product.getPrice();
					double discount = (double) product.getDiscount() / 100;
					double totalPrice = (unitPrice - unitPrice * discount) * (double) amount;
					shoppingCart = new ShoppingCart(shoppingCartKey, amount, unitPrice, totalPrice,
							product.getDiscount());
				} else {

					int amount = existProductIntoShoppingCart.getAmount() + number;

					if (product.getAmount() > 0 && (product.getAmount() < amount)) {
						amount = product.getAmount();
					} else if (product.getAmount() <= 0) {
						shoppingCartRepository.delete(existProductIntoShoppingCart);
						return false;
					}

					double unitPrice = product.getPrice();
					double discount = (double) product.getDiscount() / 100;
					double totalPrice = (unitPrice - unitPrice * discount) * (double) amount;

					existProductIntoShoppingCart.setAmount(amount);
					existProductIntoShoppingCart.setUnitPrice(unitPrice);
					existProductIntoShoppingCart.setTotalPrice(totalPrice);
					existProductIntoShoppingCart.setDiscount(product.getDiscount());

					shoppingCart = existProductIntoShoppingCart;
				}

				if (shoppingCart != null) {
					shoppingCartRepository.save(shoppingCart);
					return true;
				}
			}
		}
		return false;
	}

	public boolean updateAmountProdcutIntoShoppingCart(int id, String userName, int number) {
		Optional<Product> optProduct = productRepository.findById(id);

		Product product = optProduct.isPresent() ? optProduct.get() : null;
		Account account = accountRepository.findByUserName(userName);

		if (product != null) {
			if (product.getStatus() && product.getAmount() > 0) {
				ShoppingCart existProductIntoShoppingCart = shoppingCartRepository
						.findByShoppingCartKeyAccountIdAndShoppingCartKeyProductId(account.getId(), product.getId());
				ShoppingCart shoppingCart = null;
				if (existProductIntoShoppingCart != null) {

					int amount = number;

					if (product.getAmount() > 0 && (product.getAmount() < amount)) {
						amount = product.getAmount();
					} else if (product.getAmount() <= 0) {
						shoppingCartRepository.delete(existProductIntoShoppingCart);
						return false;
					}

					double unitPrice = product.getPrice();
					double discount = (double) product.getDiscount() / 100;
					double totalPrice = (unitPrice - unitPrice * discount) * (double) amount;

					existProductIntoShoppingCart.setAmount(amount);
					existProductIntoShoppingCart.setUnitPrice(unitPrice);
					existProductIntoShoppingCart.setTotalPrice(totalPrice);
					existProductIntoShoppingCart.setDiscount(product.getDiscount());

					shoppingCart = existProductIntoShoppingCart;
				}

				if (shoppingCart != null) {
					shoppingCartRepository.save(shoppingCart);
					return true;
				}
			}
		}
		return false;
	}

	public boolean updateProdcutIntoShoppingCart(int id, String userName, int amount) {
		Optional<Product> optProduct = productRepository.findById(id);

		Product product = optProduct.isPresent() ? optProduct.get() : null;
		Account account = accountRepository.findByUserName(userName);
		boolean result = false;
		if (product != null) {
			if (amount > 0) {
				result = addNumberProduct(product, account, amount);
			} else {
				result = minusNumberProduct(product, account, amount);
			}
		}
		return result;
	}

	private boolean addNumberProduct(Product product, Account account, int amount) {
		if (product.getStatus() && product.getAmount() > 0) {
			ShoppingCart existProductIntoShoppingCart = shoppingCartRepository
					.findByShoppingCartKeyAccountIdAndShoppingCartKeyProductId(account.getId(), product.getId());
			ShoppingCart shoppingCart = null;
			if (existProductIntoShoppingCart == null) {
				return false;
			} else {
				amount = existProductIntoShoppingCart.getAmount() + amount;

				if (product.getAmount() > 0 && (product.getAmount() < amount)) {
					amount = product.getAmount();
				} else if (product.getAmount() <= 0) {
					shoppingCartRepository.delete(existProductIntoShoppingCart);
					return false;
				}

				double unitPrice = product.getPrice();
				double discount = (double) product.getDiscount() / 100;
				double totalPrice = (unitPrice - unitPrice * discount) * (double) amount;

				existProductIntoShoppingCart.setAmount(amount);
				existProductIntoShoppingCart.setUnitPrice(unitPrice);
				existProductIntoShoppingCart.setTotalPrice(totalPrice);

				shoppingCart = existProductIntoShoppingCart;

			}

			if (shoppingCart != null) {
				shoppingCartRepository.save(shoppingCart);
				return true;
			}
		}
		return false;
	}

	private boolean minusNumberProduct(Product product, Account account, int amount) {
		if (product.getStatus() && product.getAmount() > 0) {
			ShoppingCart existProductIntoShoppingCart = shoppingCartRepository
					.findByShoppingCartKeyAccountIdAndShoppingCartKeyProductId(account.getId(), product.getId());
			ShoppingCart shoppingCart = null;
			if (existProductIntoShoppingCart == null) {
				return false;
			} else {
				amount = existProductIntoShoppingCart.getAmount() + amount;

				if (product.getAmount() > 0 && (product.getAmount() < amount)) {
					amount = product.getAmount();
				} else if (product.getAmount() <= 0) {
					shoppingCartRepository.delete(existProductIntoShoppingCart);
					return false;
				}

				if (amount <= 0) {
					shoppingCartRepository.delete(existProductIntoShoppingCart);
					return false;
				}

				double unitPrice = product.getPrice();
				double discount = (double) product.getDiscount() / 100;
				double totalPrice = (unitPrice - unitPrice * discount) * (double) amount;

				existProductIntoShoppingCart.setAmount(amount);
				existProductIntoShoppingCart.setUnitPrice(unitPrice);
				existProductIntoShoppingCart.setTotalPrice(totalPrice);

				shoppingCart = existProductIntoShoppingCart;
			}

			if (shoppingCart != null) {
				ShoppingCart result = shoppingCartRepository.save(shoppingCart);
				return true;
			}
		}
		return false;
	}

	public boolean deleteProductIntoShoppingCart(int id, String userName) {
		Optional<Product> optProduct = productRepository.findById(id);

		Product product = optProduct.isPresent() ? optProduct.get() : null;
		Account account = accountRepository.findByUserName(userName);

		ShoppingCart existProductIntoShoppingCart = shoppingCartRepository
				.findByShoppingCartKeyAccountIdAndShoppingCartKeyProductId(account.getId(), product.getId());
		if (existProductIntoShoppingCart != null) {
			shoppingCartRepository.delete(existProductIntoShoppingCart);
			return true;
		}
		return false;
	}

	public Order ordering(String userName, OrderRequest orderRequest) {
		Account account = accountRepository.findByUserName(userName);

		List<ShoppingCart> shoppingCarts = findShoppingcartByUsername(userName);

		shoppingCarts = validationListShoppingCart(shoppingCarts, account);

		double totalPrice = 0.0;

		for (ShoppingCart shopping : shoppingCarts) {
			totalPrice = totalPrice + shopping.getTotalPrice();
		}

		Order newOrder = new Order(orderRequest.getPaymentMethod(), new Date(), totalPrice, Status.CXN);

		newOrder.setAddress(orderRequest.getStress());

		Date date = new Date();

		newOrder.setAccount(account);

		Order order = orderRepository.save(newOrder);

		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		for (ShoppingCart shopping : shoppingCarts) {
			OrderDetailKey orderDetailKey = new OrderDetailKey(order.getId(),
					shopping.getShoppingCartKey().getProductId());
			OrderDetail orderDetail = new OrderDetail(orderDetailKey, shopping.getAmount(), shopping.getUnitPrice(),
					shopping.getTotalPrice());
			orderDetail.setDiscount(shopping.getDiscount());
			orderDetails.add(orderDetail);
		}
		List<OrderDetail> results = orderDetailRepository.saveAll(orderDetails);

		if (results.isEmpty()) {
			orderRepository.delete(order);

			return null;
		} else {
			shoppingCartRepository.deleteAll(shoppingCarts);

			return order;
		}
	}

	private List<ShoppingCart> validationListShoppingCart(List<ShoppingCart> shoppingCarts, Account account) {

		boolean delete = false;
		for (ShoppingCart shoppingCart : shoppingCarts) {
			Product product = shoppingCart.getProduct();
			if (product.getAmount() > 0 && (product.getAmount() < shoppingCart.getAmount())) {
				int amount = product.getAmount();

				double unitPrice = product.getPrice();
				double discount = (double) product.getDiscount() / 100;
				double totalPrice = (unitPrice - unitPrice * discount) * (double) amount;

				shoppingCart.setAmount(amount);
				shoppingCart.setUnitPrice(unitPrice);
				shoppingCart.setTotalPrice(totalPrice);

				shoppingCart = shoppingCartRepository.save(shoppingCart);
			} else if (product.getAmount() <= 0) {
				shoppingCartRepository.delete(shoppingCart);
				delete = true;
			}
		}

		if (delete) {
			shoppingCarts = shoppingCartRepository.findByShoppingCartKeyAccountId(account.getId());
		}

		return shoppingCarts;
	}

	public List<OrderDetail> findOrderDetailDByOrder(Order order) {
		return orderDetailRepository.findByOrder(order);
	}

}
