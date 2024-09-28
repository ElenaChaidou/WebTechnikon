package gr.webtechnikon.services;

import gr.webtechnikon.repositories.RepairRepository;
import gr.webtechnikon.repositories.RepairRepositoryInterface;
import gr.webtechnikon.enums.RepairStatus;
import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.repositories.OwnerRepository;
import gr.webtechnikon.repositories.OwnerRepositoryInterface;
import gr.webtechnikon.repositories.PropertyRepository;
import gr.webtechnikon.repositories.PropertyRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Slf4j
public class AdminService implements AdminServiceInterface {

    @Inject
    private OwnerRepository ownerRepository;
    @Inject
    private PropertyRepository propertyRepository;
    @Inject
    private RepairRepository repairRepository;

//    @Override
//    public List<Repair> getPendingRepairs() {
//        RepairRepository getRepairs = new RepairRepository();
//        List<Repair> allRepairs = getRepairs.findAll();
//        return allRepairs.stream().filter((Repair pendingRepair) -> RepairStatus.PENDING.equals(pendingRepair.getRepairStatus())).collect(Collectors.toList());
//
//    }
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

//    public List<Repair> getAllRepairs() {
//        RepairRepository getRepairs = new RepairRepository();
//        List<Repair> allRepairs = getRepairs.findAll();
//        return getRepairs.findAll();
//    }
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
    public Optional<Owner> findOwnerById(Long ownerId) {
        return ownerRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Owner> createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Optional<Owner> updateOwner(Owner owner) {
        return ownerRepository.update(owner);
    }

    @Override
    public boolean deleteOwner(Long ownerId) {
        return ownerRepository.safeDeleteById(ownerId);
    }

    @Override
    public Optional<Owner> searchOwnerById(Long ownerId) {
        return ownerRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Owner> searchOwnerByVatNumber(Long vatNumber) {
        return ownerRepository.findByVatNumber(vatNumber);
    }

    @Override
    public Optional<Owner> searchOwnerByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Optional<Property> createProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Optional<Property> updateProperty(Long propertyId, Property property) {
        return propertyRepository.update(property);
    }

    @Override
    public boolean deleteProperty(Long propertyId) {
        return propertyRepository.safeDeleteById(propertyId);
    }
    
//    @Override
//    public List<Property> searchPropertyByVatNumber(Long vatNumber) {
//        return propertyRepository.findByVatNumber(vatNumber);
//    }
    
        @Override
    public List<Property> searchPropertyOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
    
    @Override
    public Optional<Property> searchPropertyById(Long propertyId) {
        return propertyRepository.findById(propertyId);
    }

    @Override
    public List<Repair> getPendingRepairs() {
        return repairRepository.getPendingRepairs();
    }

    @Override
    public List<Repair> getAllRepairs() {
        return repairRepository.findAll();
    }

    @Override
    public Optional<Repair> createRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    @Override
    public Optional<Repair> updateRepair(Repair repair) {
        return repairRepository.update(repair);
    }

    @Override
    public boolean deleteRepair(Long repairId) {
        return repairRepository.safeDeleteById(repairId);
    }

    @Override
    public List<Repair> getRepairsByDate(Date date) {
        return repairRepository.findByDate(date);
    }
    
    @Override
    public List<Repair> getRepairsByOwnerId(Long ownerId) {
        return repairRepository.findByOwnerId(ownerId);
    }

}
