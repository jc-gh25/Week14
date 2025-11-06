package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}

	// Helper methods

	// Full replace – copies every field, even if null.
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	// Returns an existing PetStore if an ID is supplied, otherwise creates a new instance.
	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			// Create a brand new store (POST)
			return new PetStore();
		} else {
			return findByPetStoreId(petStoreId);
		}
	}

	// Update an existing store (PUT). Must exist, otherwise 404.
	private PetStore findByPetStoreId(Long petStoreId) {
		return petStoreDao.findByPetStoreId(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID " + petStoreId + " was not found."));
	}

	// READ – used by a GET (optional for the assignment)
	public PetStoreData getPetStore(Long id) {
		PetStore entity = petStoreDao.findByPetStoreId(id)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID " + id + " was not found."));
		return new PetStoreData(entity);
	}

} // end of class
