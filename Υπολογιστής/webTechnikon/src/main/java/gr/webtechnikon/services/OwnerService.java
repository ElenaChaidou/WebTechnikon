package gr.webtechnikon.services;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.repositories.PropertyRepositoryInterface;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.repositories.OwnerRepositoryInterface;
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

//    private final PropertyRepositoryInterface propertyRepository;
    //private final OwnerRepositoryInterface ownerRepository;
    @Inject
    private OwnerRepositoryInterface <Owner, Long, String> ownerRepository;
    @Inject
    private PropertyRepositoryInterface <Property,Long> propertyRepository;

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
}
