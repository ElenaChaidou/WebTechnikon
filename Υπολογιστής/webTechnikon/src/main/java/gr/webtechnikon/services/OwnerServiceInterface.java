package gr.webtechnikon.services;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import java.util.List;
import java.util.Optional;

public interface OwnerServiceInterface {

    Optional<Owner> findOwnerById(Long ownerId);
    List<Owner> findAllOwners();
    Optional<Owner> createOwner(Owner owner);
    Optional<Owner> updateOwner(Owner owner);
    boolean safeDeleteOwnerById(Long ownerId);
    
    Optional<Property> getPropertyDetails(Long propertyId);
    List<Property> getPropertiesByOwnerId(Long ownerId);
    Optional<Property> createProperty(Property property);
    Optional<Property> updateProperty(Long propertyId, Property property);
    boolean deleteProperty(Long propertyId);
   
    List<Repair> getRepairsForOwner(Long ownerId);
    Optional<Repair> createRepair(Repair repair);
    Optional<Repair> updateRepair(Long repairId, Repair repair);
    boolean deleteRepair(Long repairId);

    boolean acceptance(Repair repair);
}


