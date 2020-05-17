package com.soa.api.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soa.api.controller.request.BrandRequest;
import com.soa.api.controller.request.ProductRequest;
import com.soa.api.controller.request.TypeRequest;
import com.soa.api.controller.response.ApiResponse;
import com.soa.api.entity.Brand;
import com.soa.api.entity.Color;
import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Image;
import com.soa.api.entity.OrderDetail;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;
import com.soa.api.entity.ShoppingCart;
import com.soa.api.entity.User;
import com.soa.api.repository.BrandRepository;
import com.soa.api.repository.ColorRepository;
import com.soa.api.repository.FurnitureTypeRepository;
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

	@Autowired
	private FurnitureTypeRepository furnitureTypeRepository;

	@Autowired
	private NonScurityService nonScurityService;

	public ResponseEntity<?> insertProduct(ProductRequest productRequest) {

		Product existedProduct = productRepository.findByProductName(productRequest.getProductName());

		if (existedProduct != null) {

			ApiResponse apiResponse = new ApiResponse();

			apiResponse.setSuccess(false);

			apiResponse.setMessage("Product name has already taken");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);

		}

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

		if (result != null) {

			ApiResponse apiResponse = new ApiResponse();

			apiResponse.setSuccess(false);

			apiResponse.setMessage("Create product failed");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);

		}

		List<Image> images = this.getByteListImage(productRequest.getImageFiles(), result);

		imageRepository.saveAll(images);

		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setSuccess(true);

		apiResponse.setMessage("Create product successfully");

		apiResponse.setObject(productRepository.findById(result.getId()).get());

		return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);

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

			List<ProductType> productTypes = nonScurityService.findAllProductType();
			List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

			productRequest.setListFurnitureTypes(furnitureTypes);
			productRequest.setListProductTypes(productTypes);

			List<Integer> colors = new ArrayList<Integer>();

			for (int index = 0; index < product.getColors().size(); index++) {
				colors.add(product.getColors().get(index).getId());

			}

			productRequest.setColor(colors);

			return productRequest;
		}
		return null;
	}

	public ResponseEntity<?> updateProduct(ProductRequest productRequest) {
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

			ApiResponse apiResponse = new ApiResponse();

			apiResponse.setSuccess(true);

			apiResponse.setMessage("Create product successfully");

			apiResponse.setObject(productRepository.findById(result.getId()).get());

			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		}
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setSuccess(false);

		apiResponse.setMessage("Product doesn't exist");

		apiResponse.setObject(null);

		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> deleteProductById(int id) {
		Optional<Product> pOptional = productRepository.findById(id);
		Product product = pOptional.isPresent() ? pOptional.get() : null;
		if (product != null) {
			List<ShoppingCart> spcbooks = shoppingCartRepository.findByShoppingCartKeyProductId(product.getId());
			List<OrderDetail> oddbooks = orderDetailRepository.findByProduct(product);

			if (spcbooks.isEmpty() && oddbooks.isEmpty()) {
				List<Image> images = product.getImages();

				if (images.isEmpty()) {
					imageRepository.deleteAll(images);
				}

				productRepository.delete(product);
			}
			product.setStatus(false);
			productRepository.save(product);

			ApiResponse apiResponse = new ApiResponse();

			apiResponse.setSuccess(true);

			apiResponse.setMessage("Delete product successfully");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		}
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setSuccess(false);

		apiResponse.setMessage("Delete product failed");

		apiResponse.setObject(null);

		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
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

	public ResponseEntity<?> insertType(TypeRequest typeRequest, Boolean type) {
		Object result = null;
		ApiResponse apiResponse = new ApiResponse();
		if (!type) {
			Optional<FurnitureType> furnitureType = furnitureTypeRepository.findById(typeRequest.getFurnitureTypeId());
			
			if (!furnitureType.isPresent()) {
				
				apiResponse.setSuccess(false);

				apiResponse.setMessage("FurnitureType doesn't exist");

				apiResponse.setObject(null);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			}
			
			ProductType existProductType = productTypeRepository.findByNameAndFurnitureType(typeRequest.getName(), furnitureType.get());
			
			if (existProductType != null) {
				
				apiResponse.setSuccess(false);

				apiResponse.setMessage("Product Type has already taken");

				apiResponse.setObject(null);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			}
			
			ProductType productType = new ProductType();

			productType.setAcronym(typeRequest.getAcronym());
			productType.setName(typeRequest.getName());

			productType.setFurnitureType(furnitureType.get());

			result = productTypeRepository.save(productType);

		} else {
			
			FurnitureType existFurnitureType = furnitureTypeRepository.findByFurnitureName(typeRequest.getName());
			
			if (existFurnitureType != null) {
				
				apiResponse.setSuccess(false);

				apiResponse.setMessage("Furniture Type has already taken");

				apiResponse.setObject(null);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			}
			
			FurnitureType furnitureType = new FurnitureType();

			furnitureType.setAcronym(typeRequest.getAcronym());
			furnitureType.setFurnitureName(typeRequest.getName());

			result = furnitureTypeRepository.save(furnitureType);
		}
		if (result != null) {

			apiResponse.setSuccess(true);

			apiResponse.setMessage("Create successfully");

			apiResponse.setObject(typeRequest);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);

		} else {

			apiResponse.setSuccess(false);

			apiResponse.setMessage("Create failed");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);

		}
	}

	public ResponseEntity<?> findTypeById(int id, Boolean type) {
		TypeRequest typeRequest = null;

		if (!type) {
			Optional<ProductType> productTypeOpt = productTypeRepository.findById(id);
			ProductType productType = productTypeOpt.isPresent() ? productTypeOpt.get() : null;
			if (productType != null) {
				typeRequest = new TypeRequest();
				typeRequest.setId(productType.getId());
				typeRequest.setAcronym(productType.getAcronym());
				typeRequest.setName(productType.getName());

				Optional<FurnitureType> furnitureType = furnitureTypeRepository
						.findById(typeRequest.getFurnitureTypeId());

				if (furnitureType.isPresent()) {
					productType.setFurnitureType(furnitureType.get());
				}

			}

		} else {
			Optional<FurnitureType> furnitureTypeOpt = furnitureTypeRepository.findById(id);
			FurnitureType furnitureType = furnitureTypeOpt.isPresent() ? furnitureTypeOpt.get() : null;
			if (furnitureType != null) {
				typeRequest = new TypeRequest();
				typeRequest.setId(furnitureType.getId());
				typeRequest.setAcronym(furnitureType.getAcronym());
				typeRequest.setName(furnitureType.getFurnitureName());
				;
			}

		}

		ApiResponse apiResponse = new ApiResponse();
		if (typeRequest != null) {

			apiResponse.setSuccess(true);

			apiResponse.setMessage("Successfully");

			apiResponse.setObject(typeRequest);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);

		} else {

			apiResponse.setSuccess(false);

			apiResponse.setMessage("Failed");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);

		}

	}

	public ResponseEntity<?> updateType(TypeRequest typeRequest, Boolean type) {
		Object result = null;
		ApiResponse apiResponse = new ApiResponse();
		if (!type) {
			
			Optional<FurnitureType> furnitureType = furnitureTypeRepository.findById(typeRequest.getFurnitureTypeId());
			
			if (!furnitureType.isPresent()) {
				
				apiResponse.setSuccess(false);

				apiResponse.setMessage("FurnitureType doesn't exist");

				apiResponse.setObject(null);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			}
			
			ProductType existProductType = productTypeRepository.findByNameAndFurnitureType(typeRequest.getName(), furnitureType.get());
			
			ProductType existProductType2 = productTypeRepository.findByNameAndId(typeRequest.getId(), typeRequest.getName());
			
			if (existProductType != null || existProductType2 != null) {
				
				apiResponse.setSuccess(false);

				apiResponse.setMessage("Product Type has already taken");

				apiResponse.setObject(null);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			}
				
			Optional<ProductType> productTypeOpt = productTypeRepository.findById(typeRequest.getId());
			ProductType productType = productTypeOpt.isPresent() ? productTypeOpt.get() : null;
			if (productType != null) {
				productType.setAcronym(typeRequest.getAcronym());
				productType.setName(typeRequest.getName());

				if (furnitureType.isPresent()) {
					productType.setFurnitureType(furnitureType.get());
				}

				result = productTypeRepository.save(productType);
			}
		} else {
			FurnitureType existFurnitureType = furnitureTypeRepository.findByFurnitureName(typeRequest.getName());
			
			if (existFurnitureType != null) {
				
				apiResponse.setSuccess(false);

				apiResponse.setMessage("Furniture Type has already taken");

				apiResponse.setObject(null);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			}
			
			Optional<FurnitureType> furnitureTypeOpt = furnitureTypeRepository.findById(typeRequest.getId());
			FurnitureType furnitureType = furnitureTypeOpt.isPresent() ? furnitureTypeOpt.get() : null;
			if (furnitureType != null) {
				furnitureType.setAcronym(typeRequest.getAcronym());
				furnitureType.setFurnitureName(typeRequest.getName());

				result = furnitureTypeRepository.save(furnitureType);
			}
		}

		
		if (result != null) {

			apiResponse.setSuccess(true);

			apiResponse.setMessage("Update Successfully");

			apiResponse.setObject(typeRequest);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);

		} else {

			apiResponse.setSuccess(false);

			apiResponse.setMessage("Update Failed");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);

		}
	}

	public ResponseEntity<?> deleteTypeById(int id, Boolean type) {
		ApiResponse apiResponse = new ApiResponse();
		if (!type) {
			Optional<ProductType> productTypeOpt = productTypeRepository.findById(id);
			ProductType productType = productTypeOpt.isPresent() ? productTypeOpt.get() : null;
			if (productType != null) {
				List<Product> products = productRepository.findByProductType(productType);

				if (products.isEmpty()) {
					productTypeRepository.delete(productType);
					apiResponse.setSuccess(true);

					apiResponse.setMessage("Update Successfully");

					apiResponse.setObject(null);

					return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				}
			}
		} else {
			Optional<FurnitureType> furnitureTypeOpt = furnitureTypeRepository.findById(id);
			FurnitureType furnitureType = furnitureTypeOpt.isPresent() ? furnitureTypeOpt.get() : null;
			if (furnitureType != null) {
				List<ProductType> productTypes = productTypeRepository.findByFurnitureType(furnitureType);

				if (productTypes.isEmpty()) {
					furnitureTypeRepository.delete(furnitureType);
					apiResponse.setSuccess(true);

					apiResponse.setMessage("Update Successfully");

					apiResponse.setObject(null);

					return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				}
			}
		}

		apiResponse.setSuccess(false);

		apiResponse.setMessage("Failed");

		apiResponse.setObject(null);

		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);

	}

	public ResponseEntity<?> insertBrandProperties(BrandRequest brandRequest) {

		ApiResponse apiResponse = new ApiResponse();
		Brand existBrand = brandRepository.findByBrandName(brandRequest.getName());

		if (existBrand != null) {
			apiResponse.setSuccess(false);

			apiResponse.setMessage("Brand has already taken");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}

		Brand brand = new Brand();
		brand.setCountry(brandRequest.getCountry());
		brand.setBrandName(brandRequest.getName());
		Brand result = brandRepository.save(brand);
		if (result != null) {
			apiResponse.setSuccess(true);

			apiResponse.setMessage("Create brand Successfully");

			apiResponse.setObject(result);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		}

		apiResponse.setSuccess(false);

		apiResponse.setMessage("Created brand Failed");

		apiResponse.setObject(null);

		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> findBrandById(int id) {
		ApiResponse apiResponse = new ApiResponse();
		Optional<Brand> brandOpt = brandRepository.findById(id);
		Brand brand = brandOpt.isPresent() ? brandOpt.get() : null;
		if (brand != null) {
			BrandRequest brandRequest = new BrandRequest();
			brandRequest.setId(brand.getId());
			brandRequest.setCountry(brand.getCountry());
			brandRequest.setName(brand.getBrandName());

			apiResponse.setSuccess(true);

			apiResponse.setMessage("Successfully");

			apiResponse.setObject(brandRequest);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		}

		apiResponse.setSuccess(false);

		apiResponse.setMessage("Failed");

		apiResponse.setObject(null);

		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> updateBrand(@Valid BrandRequest brandRequest) {
		ApiResponse apiResponse = new ApiResponse();
		Brand existBrand = brandRepository.findByIdAndBrandName(brandRequest.getId(), brandRequest.getName());

		if (existBrand != null) {
			apiResponse.setSuccess(false);

			apiResponse.setMessage("Brand has already taken");

			apiResponse.setObject(null);

			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}

		Optional<Brand> brandOpt = brandRepository.findById(brandRequest.getId());
		Brand brand = brandOpt.isPresent() ? brandOpt.get() : null;
		if (brand != null) {
			brand.setCountry(brandRequest.getCountry());
			brand.setBrandName(brandRequest.getName());

			Brand result = brandRepository.save(brand);
			if (result != null) {
				apiResponse.setSuccess(true);

				apiResponse.setMessage("Successfully");

				apiResponse.setObject(result);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}

		apiResponse.setSuccess(false);

		apiResponse.setMessage("Failed");

		apiResponse.setObject(null);

		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> deleteBrandById(int id) {
		ApiResponse apiResponse = new ApiResponse();
		
		Optional<Brand> brandOpt = brandRepository.findById(id);
		Brand brand = brandOpt.isPresent() ? brandOpt.get() : null;
		if (brand != null) {
			List<Product> products = productRepository.findByBrand(brand);
			if (products.isEmpty()) {
				brandRepository.delete(brand);
				
				apiResponse.setSuccess(true);

				apiResponse.setMessage("Delete Brand Successfully");

				apiResponse.setObject(null);

				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}

		apiResponse.setSuccess(false);

		apiResponse.setMessage("Delete Brand Failed");

		apiResponse.setObject(null);

		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}

}
