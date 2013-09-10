package org.spinach.cart.rest.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spinach.cart.dto.ProductStoreDTO;
import org.spinach.cart.exception.CartServiceException;
import org.spinach.cart.service.ProductStoreService;

@Named
@Path("/be/api/product-store")
public class ProductStoreResource {

	Logger logger = LoggerFactory.getLogger(ProductStoreResource.class);

	@Inject
	ProductStoreService productStoreService;

	/**
	 * Handle a request to create the Product Store.
	 * 
	 * @param productStoreDTO
	 * @return
	 * @throws CartResourceException
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createProductStore(ProductStoreDTO productStoreDTO) {
		ProductStoreDTO persistedDTO = null;
		try {

			logger.debug("Product Store DTO : " + productStoreDTO.toString());

			persistedDTO = productStoreService
					.createProductStore(productStoreDTO);

		} catch (CartServiceException | NullPointerException exception) {

			logger.error("failed to create the Product Resource. Reason - ",
					exception.getMessage(), exception);

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("NOT_CREATED").build();

		}
		//ToDo
		return Response.status(Response.Status.CREATED).entity(persistedDTO)
				.build();
	}
}
