package gr.webtechnikon.repositories;

import gr.webtechnikon.exceptions.OwnerNotFoundException;
import gr.webtechnikon.exceptions.ResourceNotFoundException;
import gr.webtechnikon.model.Owner;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestScoped
public class OwnerRepository implements OwnerRepositoryInterface<Owner, Long, String> {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    public Optional<Owner> findByOwnerId(Long ownerId) {
        try {
            Owner owner = entityManager.find(Owner.class, ownerId);
            return Optional.ofNullable(owner);
        } catch (OwnerNotFoundException onfe) {
            log.debug("Could not find Owner with ID: " + ownerId);
            System.out.println(onfe.getMessage());
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Owner> findByVatNumber(Long vatNumber) {
        try {
            TypedQuery<Owner> query = entityManager.createQuery(
                    "SELECT o FROM Owner o WHERE o.VatNumber = :vatNumber AND o.deleted = false", Owner.class);
            query.setParameter("vatNumber", vatNumber);
            Owner owner = query.getSingleResult();
            return Optional.of(owner);
        } catch (Exception e) {
            log.debug("Could not find an Owner with VAT number: {}", vatNumber, e);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Owner> findByEmail(String email) {
        try {
            TypedQuery<Owner> query = entityManager.createQuery(
                    "SELECT o FROM Owner o WHERE o.Email = :email AND o.deleted = false", Owner.class);
            query.setParameter("email", email);
            Owner owner = query.getSingleResult();
            return Optional.of(owner);
        } catch (Exception e) {
            log.debug("Could not find an Owner with email: {}", email, e);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Owner> save(Owner owner) {
        try {
            entityManager.persist(owner);
            return Optional.of(owner);
        } catch (Exception e) {
            log.debug("Could not save Owner", e);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public boolean deleteById(Long ownerId) {
        Owner persistentInstance = entityManager.find(Owner.class, ownerId);
        if (persistentInstance != null) {
            try {
                entityManager.remove(persistentInstance);
                return true;
            } catch (Exception e) {
                log.debug("Could not delete Owner", e);
            }
        }
        return false;
    }

    @Override
    @Transactional
    public List<Owner> findAll() throws ResourceNotFoundException {
        try {
            TypedQuery<Owner> query = entityManager.createQuery(
                    "SELECT o FROM Owner o WHERE o.deletedOwner = false", Owner.class);
            return query.getResultList();
        } catch (Exception e) {
            log.debug("Could not fetch all Owners", e);
            throw new ResourceNotFoundException("No owners found");
        }
    }

    @Override
    @Transactional
    public boolean safeDeleteById(Long ownerId) {
        Owner persistentInstance = entityManager.find(Owner.class, ownerId);
        if (persistentInstance != null) {
            try {
                persistentInstance.setDeletedOwner(true);
                entityManager.merge(persistentInstance);
                return true;
            } catch (Exception e) {
                log.debug("Could not safely delete Owner", e);
            }
        }
        return false;
    }
    
    @Override
    @Transactional
    public Optional<Owner> update(Owner owner) {
        try {
            Owner o = entityManager.find(Owner.class, owner.getOwnerId());
            if (o != null) {
                o.setAddress(owner.getAddress());
                o.setEmail(owner.getEmail());
                o.setPassword(owner.getPassword());
                entityManager.merge(o);
                return Optional.of(o);
            } else {
                log.debug("Owner with ID: {} was not found and cannot be updated.", owner.getOwnerId());
            }
        } catch (Exception e) {
            log.debug("Could not update Owner", e);
        }
        return Optional.empty();
    }
}
