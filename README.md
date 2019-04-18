# DigiSign-AuditLog

DigiSign-AuditLog is a web application framework for Digital Signature on Audit Log in Database, It support on JSF Framework and Hibernate Framework.

## Usage

DigiSign-AuditLog is an open source project.

+ [API] disc
+ [Client Agent](https://github.com/pedonline/DigiSign-AuditLog/tree/DigiSign-AuditLog-Client)
+ [Example](https://github.com/pedonline/DigiSign-AuditLog/tree/DigiSign-AuditLog-jsf)

Hibernate Model
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
JSF ManagedBean 
```java
@ManagedBean(name = "personEdit")
@ViewScoped
public class PersonEdit extends DigiSignManageBeanTemplate implements Serializable {
	private String className;
	private String Name;

	public PersonEdit() {
		// Constructor
	}
	
	@Override
	public String saveAction() {
		//saveAction
		String methodName = "saveAction";
		try {
			
			//Open DigiSign AuditLog Session
			DigiSignInformation lo_DigiSignInformation  = this.getDigiSignInformation();		
			AuditLogInterceptor interceptor = new AuditLogInterceptor();			
			interceptor.setAlias(lo_DigiSignInformation.getDigisignAlias());
			interceptor.setOTKSign(lo_DigiSignInformation.getDigisignOTK());		
			interceptor.setOTK(lo_DigiSignInformation.getOTK());		
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(Base64.decodeBase64(
					lo_DigiSignInformation.getDigisignCertificate()));
			X509Certificate digisigncert = (X509Certificate)certFactory.generateCertificate(in);
			interceptor.setCertificate(digisigncert);
			Session MyHSs = DSPOHrmModelUtil.getSession(interceptor);		
			//Save Person
			Transaction MyHTs = MyHSs.beginTransaction();							
			try {
				Person lo_Person = new Person();
				lo_Person.setName(this.Name);			
				PersonDAO.Add(MyHSs, lo_Person, LoginUser);
				MyHTs.commit();
			} catch (Exception ex) {
				MyHTs.rollback();
			}finally {
				MyHSs.close();
			}
			
			
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[END]");
			
		} catch (InvalidKeyException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException
				| CertificateException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "person_v";
	}
}
```
