package gr.webtechnikon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.webtechnikon.enums.PropertyType;
import gr.webtechnikon.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author alexandrosaristeridis
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long propertyId;
    private long E9;
    private long propertyCode;
    private String address;
    private int yearOfConstruction;
    private PropertyType propertyType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private Owner owner;

//    @OneToMany(mappedBy = "property")
//    private List<Repair> repairList;

    @Column(name = "is_deleted")
    private boolean deletedProperty;

	public Property(long propertyCode, String address, int yearOfConstruction, PropertyType propertyType, Owner owner) {
        this.address = address;
        this.yearOfConstruction = yearOfConstruction;
        this.propertyType = propertyType;
        this.owner = owner;
        this.propertyCode = propertyCode;
        this.E9 = this.propertyCode;

    }
}
