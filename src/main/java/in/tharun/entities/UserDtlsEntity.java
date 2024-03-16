package in.tharun.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="user_details")
public class UserDtlsEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int USER_ID;
	@Column(name="NAME")
	private String name;
	@Column(name="EMAIL")
	private String email;
	@Column(name="PHNO")
	private int phno;
	private String PWD;
	private String ACC_STATUS;
	@OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
	private List<StudentEnqEntity> enquiry;
}
