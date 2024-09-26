package gr.webtechnikon.services;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AdminServiceInterface {

    List<Repair> getPendingRepairs();

    void proposeCost(Long repairId, BigDecimal proposedCost);

    List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd);

    List<Date> checkActuallDate(Long repairId);
    
    Optional<Owner> createOwner(Owner owner);
    Optional<Owner> updateOwner(Owner owner);
    boolean deleteOwner(Long ownerId);
    Optional<Owner> searchOwnerById(Long ownerId);
    Optional<Owner> searchOwnerByVatNumber(Long vatNumber);
    
    List<Property> getAllProperties();
    Optional<Property> createProperty(Property property);
    Optional<Property> updateProperty(Property property);
    boolean deleteProperty(Long propertyId);
    
    Optional<Repair> createRepair(Repair repair);
    Optional<Repair> updateRepair(Repair repair);
    boolean deleteRepair(Long repairId);
//    List<Repair> getRepairsByDate(String date);
    List<Repair> getRepairsByOwnerId(Long ownerId);

}
