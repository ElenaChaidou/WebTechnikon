package gr.technikonweb.services;

import gr.technikonweb.models.Owner;
import gr.technikonweb.models.Property;
import gr.technikonweb.models.Repair;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AdminServiceInterface {

    List<Repair> getPendingRepairs();

    void proposeCost(Long repairId, BigDecimal proposedCost);

    List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd);

    List<Date> checkActuallDate(Long repairId);
    
    List<Repair> getAllRepairs(); 
    
    Optional<Repair> getRepairById(Long repairId);
    
    void createRepair(Repair repairData); 
    
    void updateRepair(Long repairId, Repair repairData); 

    void deleteRepair(Long repairId); 
    
    List<Owner> getAllOwners(); 

    Optional<Owner> getOwnerById(Long ownerId); 

    void createOwner(Owner ownerData); 

//    void updateOwner(Long ownerId, Owner ownerData); 

    void deleteOwner(Long ownerId); 

    List<Property> getAllProperties(); 

    Optional<Property> getPropertyById(Long propertyId); 

//    void updateProperty(Long propertyId, Property propertyData); 

    void deleteProperty(Long propertyId); 
    
    

}
