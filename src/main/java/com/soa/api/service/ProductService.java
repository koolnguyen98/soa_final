package com.soa.api.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soa.api.controller.request.BrandRequest;
import com.soa.api.controller.request.ProductRequest;
import com.soa.api.controller.request.ProductTypeRequest;
import com.soa.api.entity.Brand;
import com.soa.api.entity.Color;
import com.soa.api.entity.Image;
import com.soa.api.entity.OrderDetail;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;
import com.soa.api.entity.ShoppingCart;
import com.soa.api.repository.BrandRepository;
import com.soa.api.repository.ColorRepository;
import com.soa.api.repository.ImageRepository;
import com.soa.api.repository.OrderDetailRepository;
import com.soa.api.repository.ProductRepository;
import com.soa.api.repository.ProductTypeRepository;
import com.soa.api.repository.ShoppingCartRepository;

@Service
public class ProductService {
	@Autowired
	private ProductTypeRepository productTypeRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ColorRepository colorRepository;

	public Product insertProduct(ProductRequest productRequest) {
		Product product = new Product();

		product.setAmount(productRequest.getAmount());
		product.setDateCreate(new Timestamp(System.currentTimeMillis()));
		product.setDescription(productRequest.getDescription());
		product.setDiscount(productRequest.getDiscount());
		product.setPrice(productRequest.getPrice());
		product.setProductName(productRequest.getProductName());
		product.setProductType(productTypeRepository.findById(productRequest.getProductType()).get());
		product.setSize(productRequest.getSize());
		product.setStatus(productRequest.getStatus());
		
		List<Color> colors = new ArrayList<Color>();
		
		for (int index = 0; index < productRequest.getColor().size(); index++) {
			Optional<Color> color = colorRepository.findById(productRequest.getColor().get(index));
			if (color.isPresent()) {
				colors.add(color.get());
			}
		}
		
		product.setColors(colors);
		product.setBrand(brandRepository.findById(productRequest.getBrand()).get());
		
		Product result = productRepository.save(product);
		
		List<Image> images = this.getByteListImage(productRequest.getImageFiles(), result);
		
		imageRepository.saveAll(images);
		
		return productRepository.findById(result.getId()).get();
	}

	public ProductRequest findProductById(int id) {
		Optional<Product> pOptional = productRepository.findById(id);
		Product product = pOptional.isPresent() ? pOptional.get() : null;
		if (product != null) {
			ProductRequest productRequest = new ProductRequest();
			productRequest.setId(product.getId());
			productRequest.setAmount(product.getAmount());
			productRequest.setDescription(product.getDescription());
			productRequest.setDiscount(product.getDiscount());
			productRequest.setPrice(product.getPrice());
			productRequest.setProductName(product.getProductName());
			productRequest.setProductType(product.getProductType().getId());
			productRequest.setSize(product.getSize());
			productRequest.setStatus(product.getStatus());
			productRequest.setBase64Images(this.CoverBase64(product.getImages()));
			productRequest.setBrand(product.getBrand().getId());
			
			List<Integer> colors = new ArrayList<Integer>();
			
			for (int index = 0; index < product.getColors().size(); index++) {
				colors.add(product.getColors().get(index).getId());

			}
			
			productRequest.setColor(colors);
			
			return productRequest;
		}
		return null;
	}

	public Product updateProduct(ProductRequest productRequest) {
		Optional<Product> pOptional = productRepository.findById(productRequest.getId());
		Product product = pOptional.isPresent() ? pOptional.get() : null;
		if (product != null) {
			product.setAmount(productRequest.getAmount());
			product.setDateCreate(new Timestamp(System.currentTimeMillis()));
			product.setDescription(productRequest.getDescription());
			product.setDiscount(productRequest.getDiscount());
			product.setPrice(productRequest.getPrice());
			product.setProductName(productRequest.getProductName());
			product.setProductType(productTypeRepository.findById(productRequest.getProductType()).get());
			product.setSize(productRequest.getSize());
			product.setStatus(productRequest.getStatus());
			
			List<Color> colors = new ArrayList<Color>();
			
			for (int index = 0; index < productRequest.getColor().size(); index++) {
				Optional<Color> color = colorRepository.findById(productRequest.getColor().get(index));
				if (color.isPresent()) {
					colors.add(color.get());
				}
			}
			
			product.setColors(colors);
			product.setBrand(brandRepository.findById(productRequest.getBrand()).get());
			
			Product result = productRepository.save(product);
			
			List<Image> images = this.getByteListImage(productRequest.getImageFiles(), result);
			
			imageRepository.saveAll(images);
			
			return productRepository.findById(result.getId()).get();
		}
		return null;
	}

	public Boolean deleteProductById(int id) {
		Optional<Product> pOptional = productRepository.findById(id);
		Product product = pOptional.isPresent() ? pOptional.get() : null;
		if (product != null) {
			List<ShoppingCart> spcbooks = shoppingCartRepository.findByShoppingCartKeyProductId(product.getId());
			List<OrderDetail> oddbooks = orderDetailRepository.findByProduct(product);
			
			if(spcbooks.isEmpty() && oddbooks.isEmpty()) {
				List<Image> images = product.getImages();
				
				if (images.isEmpty()) {
					imageRepository.deleteAll(images);
				}
				
				productRepository.delete(product);
			}
			product.setStatus(false);
			productRepository.save(product);
			return true;
		}
		return false;
	}
	
	private List<Image> getByteListImage(MultipartFile[] imageFiles, Product result) {
		List<Image> images = new ArrayList<Image>();
		
		if (imageFiles != null && imageFiles.length > 0) {
			for (MultipartFile imageFile : imageFiles) {
				Image image = new Image();
				try {
					image.setImage(imageFile.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				image.setProduct(result);
				images.add(image);
			}
			return images;
		}
		
		return null;
	}
	
	private List<String> CoverBase64(List<Image> images) {
		List<String> base64Images = new ArrayList<String>();
		for (Image image : images) {
			String base64Encoded = Base64.getEncoder().encodeToString(image.getImage());
			base64Images.add(base64Encoded);
		}
		return base64Images;
	}

	public ProductType insertProductType(ProductTypeRequest productTypeRequest) {
		ProductType productType = new ProductType();

		productType.setAcronym(productTypeRequest.getAcronym());
		productType.setName(productTypeRequest.getName());

		return productTypeRepository.save(productType);
	}
	
	public ProductTypeRequest findProductTypeById(int id) {
		Optional<ProductType> productTypeOpt = productTypeRepository.findById(id);
		ProductType productType = productTypeOpt.isPresent() ? productTypeOpt.get() : null;
		if (productType != null) {
			ProductTypeRequest productTypeRequest = new ProductTypeRequest();
			productTypeRequest.setId(productType.getId());
			productTypeRequest.setAcronym(productType.getAcronym());
			productTypeRequest.setName(productType.getName());

			return productTypeRequest;
		}
		return null;
	}

	public ProductType updateProductType(ProductTypeRequest productTypeRequest) {
		Optional<ProductType> productTypeOpt = productTypeRepository.findById(productTypeRequest.getId());
		ProductType productType = productTypeOpt.isPresent() ? productTypeOpt.get() : null;
		if (productType != null) {
			productType.setAcronym(productTypeRequest.getAcronym());
			productType.setName(productTypeRequest.getName());

			return productTypeRepository.save(productType);
		}
		return null;
	}

	public Boolean deleteProductTypeById(int id) {
		Optional<ProductType> productTypeOpt = productTypeRepository.findById(id);
		ProductType productType = productTypeOpt.isPresent() ? productTypeOpt.get() : null;
		if (productType != null) {
			List<Product> products = productRepository.findByProductType(productType);
			
			if(products.isEmpty()) {
				productTypeRepository.delete(productType);
				return true;
			}
		}
		return false;
	}

	public Boolean insertBrandProperties(BrandRequest brandRequest) {
			Brand brand = new Brand();

		brand.setCountry(brandRequest.getCountry());
		brand.setBrandName(brandRequest.getName());
		if (brandRepository.save(brand) != null) {
			return true;
		}

		return false;
	}

	public BrandRequest findBrandById(int id) {

		Optional<Brand> brandOpt = brandRepository.findById(id);
		Brand brand = brandOpt.isPresent() ? brandOpt.get() : null;
		if (brand != null) {
			BrandRequest brandRequest = new BrandRequest();
			brandRequest.setId(brand.getId());
			brandRequest.setCountry(brand.getCountry());
			brandRequest.setName(brand.getBrandName());

			return brandRequest;
		}

		return null;
	}

	public Boolean updateBrand(@Valid BrandRequest brandRequest) {

		Optional<Brand> brandOpt = brandRepository.findById(brandRequest.getId());
		Brand brand = brandOpt.isPresent() ? brandOpt.get() : null;
		if (brand != null) {
			brand.setCountry(brandRequest.getCountry());
			brand.setBrandName(brandRequest.getName());
			if (brandRepository.save(brand) != null) {
				return true;
			}
		}

		return false;
	}

	public Boolean deleteBrandById(int id) {

			Optional<Brand> brandOpt = brandRepository.findById(id);
			Brand brand = brandOpt.isPresent() ? brandOpt.get() : null;
			if (brand != null) {
				List<Product> products = productRepository.findByBrand(brand);
				if(products.isEmpty()) {
					brandRepository.delete(brand);
					return true;
				}
			}
		
		return false;
	}
	
}
