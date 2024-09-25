package gr.technikonweb.services;

import gr.technikonweb.models.Owner;
import gr.technikonweb.models.Property;
import gr.technikonweb.models.Repair;
import java.util.List;
import java.util.Optional;

public interface OwnerServiceInterface {

    boolean acceptance(Repair repair);

    List<Property> getPropertiesByOwnerId(Long ownerId);
    
    Optional<Owner> getOwnerDetails(Long ownerId);
 
    Optional<Owner> updateOwnerDetails(Owner owner);
    
    Optional<Property> createProperty(Property property);
    
     List<Property> getProperties(Long ownerId);

    Optional<Property> updateProperty(Long propertyId, Property property);

    boolean deleteProperty(Long propertyId);

    List<Repair> getRepairs(Long ownerId);

    Optional<Repair> createRepair(Repair repair);

    Optional<Repair> updateRepair(Long repairId, Repair repair);

    boolean deleteRepair(Long repairId);
}

