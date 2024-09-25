package gr.technikonweb.services;

import gr.technikonweb.models.Owner;
import gr.technikonweb.repositories.PropertyRepositoryInterface;
import gr.technikonweb.models.Property;
import gr.technikonweb.models.Repair;
import gr.technikonweb.repositories.OwnerRepositoryInterface;
import gr.technikonweb.repositories.RepairRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.Builder;
import lombok.Builder.Default;

@Slf4j
@RequestScoped

public class OwnerService implements OwnerServiceInterface {

    @Inject
    private  OwnerRepositoryInterface ownerRepository;
    @Inject
    private  PropertyRepositoryInterface propertyRepository;
    @Inject
    private  RepairRepositoryInterface repairRepository;

    @Override
    public boolean acceptance(Repair repair) {
        System.out.println(repair.toString());
        System.out.println("Do you accept the proposed repair ?");
        Scanner scanner = new Scanner(System.in);
        boolean acceptance = scanner.nextBoolean();
        repair.setAcceptance(acceptance);
        return acceptance;

    }

    public OwnerService(PropertyRepositoryInterface propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public List<Property> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
    
        @Override
    public Optional<Owner> getOwnerDetails(Long ownerId) {
        return ownerRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Owner> updateOwnerDetails(Owner owner) {
        return ownerRepository.update(owner);
    }

    @Override
    public List<Property> getProperties(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
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

    @Override
    public List<Repair> getRepairs(Long ownerId) {
        return repairRepository.findByOwnerId(ownerId); 
    }

    @Override
    public Optional<Repair> createRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    @Override
    public Optional<Repair> updateRepair(Long repairId, Repair repair) {
        return repairRepository.update(repair);
    }

    @Override
    public boolean deleteRepair(Long repairId) {
        return repairRepository.safeDeleteById(repairId);
    }
}
