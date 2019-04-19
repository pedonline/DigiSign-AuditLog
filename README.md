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
		} catch (InvalidKeyException | UnrecoverableKeyException | KeyStoreException
				| NoSuchAlgorithmException
				| CertificateException | NoSuchPaddingException 
				| IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		}
		return "person_v";
	}
}
```
XHTML : Add & Edit Page
```xml
<p:commandButton id="save" value="save" actionListener="#{digiSignDialog.DialogchooseOpen}" immediate="true"  >
	<f:ajax execute="@form" render="@form" />
	<p:ajax event="dialogReturn" listener="#{personEdit.onSignAction}" update=":form" />
</p:commandButton>
```
XHTML : View Page
```xml
<h:panelGrid columns="2">
	<h:graphicImage id="img_correct" widgetVar="img_correct" value ="/resources/image_new/img_correct_3.png" width="50" rendered="#{personEdit.CBIPerson.isEntityValid}"/>
	<h:graphicImage id="img_wrong" widgetVar="img_wrong" value ="/resources/image_new/img_wrong.png" width="50" rendered="#{!personEdit.CBIPerson.isEntityValid}"/>
	<h:panelGroup >
	<p:fieldset legend="Audit Log" rendered="#{not empty personEdit.CBIPerson.digiSignVerifyInfomationList}">	
	<p:dataTable id="digisigntablelist" value="#{personEdit.CBIPerson.digiSignVerifyInfomationList}" var="digisigndatalist" 
		paginator="true" rowIndexVar="rowIndex" rowKey="#{digisigndatalist.auditLogId}" 
		currentPageReportTemplate="Total {totalRecords} ">
		<p:column headerText="Order">
			<h:outputText value="#{rowIndex+1}" />
		</p:column>
		<p:column headerText="Audit Log Status">
			<h:graphicImage id="img2_correct" widgetVar="img2_correct" value ="/resources/image_new/img_correct_3.png" width="20" rendered="#{digisigndatalist.verified}"/>
			<h:graphicImage id="img2_wrong" widgetVar="img2_wrong" value ="/resources/image_new/img_wrong.png" width="20" rendered="#{!digisigndatalist.verified}"/>
		</p:column>
		<p:column headerText="Name">
			<h:outputText value="#{digisigndatalist.signName}" />
		</p:column>
		<p:column headerText="Sign Date">
			<h:outputText value="#{digisigndatalist.signDate}" />
		</p:column>
		<p:column headerText="Subject">
			<h:outputText value="#{digisigndatalist.certificateDetail.subject}" />
		</p:column>
	</p:dataTable>				        				
	</p:fieldset>	
	</h:panelGroup>
</h:panelGrid>
```
