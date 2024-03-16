package in.tharun.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="student_enquries")
public class StudentEnqEntity
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int ENQUIRY_ID;
   private String STUDENT_NAME;
   private int PHNO;
   private String CLASS_MODE;
   private String COURSE_NAME;
   private String ENQUIRY_STATUS;
   @CreationTimestamp
   private LocalDate CREATED_DATE;
   @UpdateTimestamp 
   private LocalDate UPDATED_DATE;
   
   @ManyToOne
   @JoinColumn(name="user_id")
   private UserDtlsEntity user_id;
   
   
   
}
