# DigiSign-AuditLog

DigiSign-AuditLog is a web application framework for Digital Signature on Audit Log in Database, It support on JSF Framework and Hibernate Framework.

## Usage

DigiSign-AuditLog is an open source project.

+ [API] disc
+ [Client Agent](https://github.com/pedonline/DigiSign-AuditLog/tree/DigiSign-AuditLog-Client)
+ [Example](https://github.com/pedonline/DigiSign-AuditLog/tree/DigiSign-AuditLog-jsf)

```java
@Entity
@Table(name = "Person")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "personID")
public class Person implements IAuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PersonID", unique = true, nullable = false)
	@JsonManagedReference
	private Integer PersonID;

	@Column(name = "Name")
	private String Name;

	@Transient
	@Override
	public Long getId() {
		return this.PersonID.longValue();
	}

	@Transient
	@Override
	public byte[] getLogDeatil() {
		return IAuditLog.getJsonByteLogDeatil(this);
	}

	@Transient
	@Override
	public String getEmbeddedId() {
		return this.PersonID.toString();
	}
}
```
