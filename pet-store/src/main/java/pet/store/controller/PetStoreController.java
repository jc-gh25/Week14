package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService petStoreService;

	// Create (POST)
	@PostMapping // no extra "/pet_store" here - use the class-level path
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}

	// Update (PUT)
	// The request body must contain all fields of the store. Omitted fields will be
	// overwritten with null.
	// For partial updates use the PATCH endpoint (not implemented in this assignment).
	@PutMapping("/{storeId}")
	@ResponseStatus(HttpStatus.OK)
	public PetStoreData updatePetStore(@PathVariable("storeId") Long storeId, @RequestBody PetStoreData petStoreData) {
		log.info("Updating Pet Store {} with data {}", storeId, petStoreData);
		petStoreData.setPetStoreId(storeId);
		return petStoreService.savePetStore(petStoreData);
	}

	// Read (GET)
	@GetMapping("/{id}")
	public PetStoreData read(@PathVariable("id") Long id) {
		log.info("GET /pet_store/{}", id);
		return petStoreService.getPetStore(id);
	}

	// For checking connectivity
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

} // end of class
