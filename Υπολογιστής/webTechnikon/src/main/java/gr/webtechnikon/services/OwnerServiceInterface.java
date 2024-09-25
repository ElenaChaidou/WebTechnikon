package gr.webtechnikon.services;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import java.util.List;
import java.util.Optional;

public interface OwnerServiceInterface {

    boolean acceptance(Repair repair);

    List<Property> getPropertiesByOwnerId(Long ownerId);
    
    Optional<Owner> findOwnerById(Long ownerId);
    List<Owner> findAllOwners();
    Optional<Owner> createOwner(Owner owner);
    Optional<Owner> updateOwner(Owner owner);
    boolean safeDeleteOwnerById(Long ownerId);
}
