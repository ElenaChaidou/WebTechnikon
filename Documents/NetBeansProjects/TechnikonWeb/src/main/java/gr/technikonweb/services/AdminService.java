package gr.technikonweb.services;

import gr.technikonweb.repositories.RepairRepository;
import gr.technikonweb.repositories.RepairRepositoryInterface;
import gr.technikonweb.enums.RepairStatus;
import gr.technikonweb.models.Owner;
import gr.technikonweb.models.Property;
import gr.technikonweb.models.Repair;
import gr.technikonweb.repositories.OwnerRepositoryInterface;
import gr.technikonweb.repositories.PropertyRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
public class AdminService implements AdminServiceInterface {
    
    @Inject
    private  OwnerRepositoryInterface ownerRepository;
    @Inject
    private  PropertyRepositoryInterface propertyRepository;
    @Inject
    private  RepairRepositoryInterface repairRepository;

    @Override
    public List<Repair> getPendingRepairs() {
        RepairRepository getRepairs = new RepairRepository();
        List<Repair> allRepairs = getRepairs.findAll();
        return allRepairs.stream().filter((Repair pendingRepair) -> RepairStatus.PENDING.equals(pendingRepair.getRepairStatus())).collect(Collectors.toList());

    }

    @Override
    public void proposeCost(Long repairId, BigDecimal proposedCost) {
        Repair rp = new Repair();
        RepairRepositoryInterface rr = new RepairRepository();
        rr.findById(repairId);
        rp.setProposedCost(proposedCost);

    }

    public List<BigDecimal> getProposedCost() {
        Repair rp = new Repair();
        RepairRepository getRepairs = new RepairRepository();
        List<Repair> allRepairs = getRepairs.findAll();
        BigDecimal BDCost;
        List<BigDecimal> Costs = new ArrayList();
        for (Repair repair : allRepairs) {
            BDCost = rp.getProposedCost();
            Costs.add(BDCost);

        }
        return Costs;

    }

    @Override
    public List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd) {
        RepairRepositoryInterface rri = new RepairRepository();
        return rri.findByRangeDates(proposedDateOfStart, proposedDateOfEnd);

    }

    public List<Repair> getAllRepairs() {
        RepairRepository getRepairs = new RepairRepository();
        List<Repair> allRepairs = getRepairs.findAll();
        return getRepairs.findAll();
    }

    @Override
    public List<Date> checkActuallDate(Long repairId) {
        RepairRepository rr = new RepairRepository();
        List<Date> rDates = new ArrayList<>();

        Optional<Repair> optionalRepair = rr.findById(repairId);
        if (optionalRepair.isPresent()) {
            Repair rp = optionalRepair.get();
            if (rp.getRepairStatus().equals(RepairStatus.COMPLETE)) {
                rDates.add(rp.getDateOfStart());
                rDates.add(rp.getDateOfEnd());
            }
        }

        return rDates;
    }

    public void proposeCostsAndDates(Long repairId, BigDecimal proposedCost, Date proposedStartDate, Date proposedEndDate) {
        RepairRepositoryInterface rri = new RepairRepository();
        Optional<Repair> optionalRepair = rri.findById(repairId);
        if (optionalRepair.isPresent()) {
            Repair repair = optionalRepair.get();
            if (RepairStatus.PENDING.equals(repair.getRepairStatus())) {
                repair.setProposedCost(proposedCost);
                repair.setProposedDateOfStart(proposedStartDate);
                repair.setProposedDateOfEnd(proposedEndDate);
            }
        }
    }
    
    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> getOwnerById(Long ownerId) {
        return ownerRepository.findByOwnerId(ownerId);
    }

    @Override
    public void createOwner(Owner ownerData) {
        ownerRepository.save(ownerData);
    }

//    @Override
//    public void updateOwner(Long ownerId, Owner ownerData) {
//        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
//        optionalOwner.ifPresent(existingOwner -> {
//            existingOwner.setName(ownerData.getName());
//            existingOwner.setContactInfo(ownerData.getContactInfo());
//            ownerRepository.save(existingOwner);
//        });
//    }

    @Override
    public void deleteOwner(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }

    // Property Management Methods
    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Optional<Property> getPropertyById(Long propertyId) {
        return propertyRepository.findById(propertyId);
    }

//    @Override
//    public void updateProperty(Long propertyId, Property propertyData) {
//        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
//        optionalProperty.ifPresent(existingProperty -> {
//            existingProperty.setName(propertyData.getName());
//            existingProperty.setAddress(propertyData.getAddress());
//            propertyRepository.save(existingProperty);
//        });
//    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }

    @Override
    public Optional<Repair> getRepairById(Long repairId) {
        return repairRepository.findById(repairId);
    }

    @Override
    public void createRepair(Repair repairData) {
        repairRepository.save(repairData);
    }

    @Override
    public void updateRepair(Long repairId, Repair repairData) {
        Optional<Repair> optionalRepair = repairRepository.findById(repairId);
        optionalRepair.ifPresent(existingRepair -> {
            existingRepair.setRepairStatus(repairData.getRepairStatus());
            existingRepair.setProposedCost(repairData.getProposedCost());
            repairRepository.save(existingRepair);
        });
    }

    @Override
    public void deleteRepair(Long repairId) {
        repairRepository.deleteById(repairId);
    }



}
