package gr.webtechnikon.services;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.repositories.PropertyRepositoryInterface;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.repositories.OwnerRepository;
import gr.webtechnikon.repositories.OwnerRepositoryInterface;
import gr.webtechnikon.repositories.PropertyRepository;
import gr.webtechnikon.repositories.RepairRepository;
import gr.webtechnikon.repositories.RepairRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import jakarta.inject.Inject;

//@ApplicationScoped
@RequestScoped
@Slf4j
public class OwnerService implements OwnerServiceInterface {

    @Inject
    private OwnerRepository ownerRepository;
    @Inject
    private PropertyRepository propertyRepository;
    @Inject
    private RepairRepository repairRepository;

    @Override
    public boolean acceptance(Repair repair) {
        System.out.println(repair.toString());
        System.out.println("Do you accept the proposed repair ?");
        Scanner scanner = new Scanner(System.in);
        boolean acceptance = scanner.nextBoolean();
        repair.setAcceptance(acceptance);
        return acceptance;

    }

    @Override
    public Optional<Owner> findOwnerById(Long ownerId) {
        return ownerRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
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
    public boolean safeDeleteOwnerById(Long ownerId) {
        return ownerRepository.safeDeleteById(ownerId);
    }

    @Override
    public Optional<Property> getPropertyDetails(Long propertyId) {
        return propertyRepository.findById(propertyId);
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
    public List<Property> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Repair> getRepairsForOwner(Long ownerId) {
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
