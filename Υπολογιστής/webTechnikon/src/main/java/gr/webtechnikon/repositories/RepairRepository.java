package gr.webtechnikon.repositories;

import gr.webtechnikon.enums.RepairStatus;
import gr.webtechnikon.exceptions.ResourceNotFoundException;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.utility.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@Slf4j
//@ApplicationScoped 
@RequestScoped 
@NoArgsConstructor
public class RepairRepository implements RepairRepositoryInterface<Repair, Long, Date> {
    
    @PersistenceContext(unitName = "Persistence")
    private  EntityManager entityManager;

    @Override
    public Optional<Repair> findById(Long repairId) {
        try {
            Repair repair = entityManager.find(Repair.class, repairId);
            return Optional.ofNullable(repair);
        } catch (ResourceNotFoundException rnfe) {
            //log.debug("Could not find Property with ID: " + repairId);
            entityManager.getTransaction().rollback();
            System.out.println(rnfe.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Repair> findByPropertyId(Long propertyId) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Repair> query = entityManager.createQuery(
                    "SELECT repair FROM Repair repair WHERE repair.property.propertyId = :propertyId", Repair.class);
            query.setParameter("propertyId", propertyId);
            List<Repair> repairs = query.getResultList();
            entityManager.getTransaction().commit();
            return repairs;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            log.error("Error finding repairs by Property ID: " + propertyId, e);
        }
        return List.of();
    }
    
    @Transactional
    @Override
    public List<Repair> findByOwnerId(Long ownerId) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Repair> query = entityManager.createQuery(
                    "SELECT repair FROM Repair repair WHERE repair.property.propertyId = :propertyId", Repair.class);
            query.setParameter("propertyId", ownerId);
            List<Repair> repairs = query.getResultList();
            entityManager.getTransaction().commit();
            return repairs;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            log.error("Error finding repairs by Property ID: " + ownerId, e);
        }
        return List.of();
    }

    @Override
    public List<Repair> findByDate(Date dateOfStart) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Repair> query = entityManager.createQuery("SELECT repair FROM Repair repair WHERE repair.dateOfStart = :dateOfStart", Repair.class);
            query.setParameter("dateOfStart", dateOfStart);
            List<Repair> repairs = query.getResultList();
            entityManager.getTransaction().commit();
            return repairs;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error finding repairs by date: " + dateOfStart, e);
        }
        return List.of();
    }

    @Override
    public List<Repair> findByRangeDates(Date dateOfStart, Date dateOfEnd) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Repair> query = entityManager.createQuery("SELECT repair FROM Repair repair WHERE repair.dateOfSubmission BETWEEN :dateOfStart AND :dateOfEnd", Repair.class);
            query.setParameter("dateOfStart", dateOfStart);
            query.setParameter("dateOfEnd", dateOfEnd);
            List<Repair> repairs = query.getResultList();
            entityManager.getTransaction().commit();
            return repairs;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error finding repairs by date range: " + dateOfStart + " to " + dateOfEnd, e);
        }
        return List.of();
    }

    @Override
    @Transactional
    public List<Repair> findAll() {
        try {
            TypedQuery<Repair> query = entityManager.createQuery("SELECT r FROM Repair r", Repair.class);
            return query.getResultList();
        } catch (ResourceNotFoundException rnfe) {
            log.error("Error retrieving all repairs");
            System.out.println(rnfe.getMessage());
        }
        return List.of();
    }
    
    @Override
    @Transactional
    public Optional<Repair> save(Repair repair) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(repair);
            entityManager.getTransaction().commit();
            return Optional.of(repair);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error saving repair: " + repair, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Repair> update(Repair repair) {
        try {
            entityManager.getTransaction().begin();
            repair = entityManager.merge(repair);
            entityManager.getTransaction().commit();
            return Optional.of(repair);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error updating repair: " + repair, e);
        }
        return Optional.empty();
    }

    @Override       
    @Transactional
    public boolean deleteById(Long repairId) {
        try {
            Repair repair = entityManager.find(Repair.class, repairId);
            if (repair != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(repair);
                entityManager.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error deleting repair with ID: " + repairId, e);
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean safeDeleteById(Long repairId) {
        try {
            Repair repair = entityManager.find(Repair.class, repairId);
            if (repair != null) {
                entityManager.getTransaction().begin();
                repair.setDeletedRepair(true);
                entityManager.merge(repair);
                entityManager.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error safely deleting repair with ID: " + repairId, e);
        }
        return false;
    }
    
        public List<Repair> getPendingRepairs() {
        RepairRepository getRepairs = new RepairRepository();
        List<Repair> allRepairs = getRepairs.findAll();
        return allRepairs.stream().filter((Repair pendingRepair) -> RepairStatus.PENDING.equals(pendingRepair.getRepairStatus())).collect(Collectors.toList());

    }
}
